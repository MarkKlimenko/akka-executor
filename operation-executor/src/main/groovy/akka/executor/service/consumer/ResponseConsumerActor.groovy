package akka.executor.service.consumer

import akka.actor.AbstractActor
import akka.actor.Props

// import akka.actor.AbstractActor.Receive - need to be imported
import groovy.util.logging.Slf4j

import java.util.concurrent.Callable

@Slf4j
class ResponseConsumerActor extends AbstractActor {
    ResponseConsumerActor() { }

    static Props props() {
        return Props.create(ResponseConsumerActor.class, {new ResponseConsumerActor()})
    }

    @Override
    Receive createReceive() {
        return receiveBuilder().match(Object.class, { msg ->

            /*log.info("START in RESPONSE CONSUMER actor of node ${msg}")
            sleep(2000)
            log.info("STOP in RESPONSE CONSUMER actor of node ${msg}")
            getSender().tell('response result', getSender())*/


        }).build()
    }

    class Executed implements Callable<String> {
        String text

        Executed(String text) {
            this.text = text
        }

        @Override
        String call() throws Exception {
            text
        }
    }

}
