package akka.executor.service.producer

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import akka.pattern.Patterns

// import akka.actor.AbstractActor.Receive - need to be imported
import akka.actor.AbstractActor.Receive

import akka.routing.FromConfig
import akka.util.Timeout
import groovy.util.logging.Slf4j
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration

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
            log.info("START in root")

//            sleep(2000)
//            consumerRouter.tell('produce', ActorRef.noSender())
//            log.info("STOP in root")


            Timeout timeout = new Timeout(Duration.create(5, "seconds"))
            Future<Object> future = Patterns.ask(consumerRouter, 'produce', timeout)
            String result = (String) Await.result(future, timeout.duration())
            log.info("STOP in root with result ${result}")

        }).build()
    }
}
