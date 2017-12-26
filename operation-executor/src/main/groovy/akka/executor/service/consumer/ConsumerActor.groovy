package akka.executor.service.consumer

import akka.actor.AbstractActor
import akka.actor.Props

// import akka.actor.AbstractActor.Receive - need to be imported
import groovy.util.logging.Slf4j

@Slf4j
class ConsumerActor extends AbstractActor {
    ConsumerActor() { }

    static Props props() {
        return Props.create(ConsumerActor.class, {new ConsumerActor()})
    }

    @Override
    Receive createReceive() {
        return receiveBuilder().match(Object.class, { msg ->

            log.info("START in CONSUMER actor of node ${msg}")
            sleep(2000)
            log.info("STOP in CONSUMER actor of node ${msg}")

        }).build()
    }
}
