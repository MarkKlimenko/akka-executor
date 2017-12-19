package akka.executor.service.producer

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.dispatch.Mapper
import akka.dispatch.OnSuccess
import akka.executor.service.SystemProvider
import akka.pattern.Patterns

// import akka.actor.AbstractActor.Receive - need to be imported
import akka.actor.AbstractActor.Receive

import akka.routing.FromConfig
import akka.util.Timeout
import groovy.util.logging.Slf4j
import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration.Duration

import java.util.concurrent.Callable

import static akka.dispatch.Futures.future
import static akka.dispatch.Futures.sequence

@Slf4j
class ProducerActor extends AbstractActor {
    ProducerActor() { }

    static Props props() {
        return Props.create(ProducerActor.class, {new ProducerActor()})
    }

    ActorRef consumerRouter = context.actorOf(FromConfig.getInstance().props(), 'consumerRouter')

    @Override
    Receive createReceive() {
        return receiveBuilder().match(Object.class, { msg ->
            ActorSystem system = SystemProvider.instance()

            log.info("START in root")

            final ExecutionContext ec = system.dispatcher()

            Future<String> res =  ['a', 'b', 'c'].collect {
                new SubTask(it, msg as String)
            }.collect {
                future(it,ec)
            }. with {
                sequence(it, ec)
            }.map(
                    new Mapper<Iterable<String>, String>() {
                        String apply(Iterable<String> ints) {
                            String sum = ""
                            for (String i : ints)
                                sum += i
                            return (msg + sum)
                        }
                    }, ec)

            log.info('INNER BEFORE SUCCESS')
            res.onSuccess(new PrintResult<Long>(), system.dispatcher())
            log.info('INNER AFTER SUCCESS')

        }).build()
    }

    static class SubTask implements Callable<String> {
        String param
        String number

        SubTask(String param, String number) {
            this.param = param
            this.number = number
        }

        @Override
        String call() throws Exception {

            if(number == '1') {
                sleep(5000)
            }
            if(number == '2') {
                sleep(2000)
            }

            log.info('IN CALL ' + number + param)
            return param
        }
    }

    final static class PrintResult<T> extends OnSuccess<T> {
        @Override public final void onSuccess(T t) {
            log.info(t as String)
        }
    }
}
