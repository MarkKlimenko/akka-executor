package akka.executor.service

import akka.actor.AbstractActor
import akka.actor.Props
// import akka.actor.AbstractActor.Receive - need to be imported
import akka.actor.AbstractActor.Receive
import groovy.util.logging.Slf4j

@Slf4j
class Executor extends AbstractActor {

    static Props props() {
        return Props.create(Executor.class, {new Executor()})
    }

    Executor() {

    }

    @Override
    Receive createReceive() {
        return receiveBuilder().match(Object.class, { msg ->

            log.info("START in test actor of node ${msg}")
            sleep(7000)
            log.info("STOP in test actor of node ${msg}")

        }).build()
    }
}
