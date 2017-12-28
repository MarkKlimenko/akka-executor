package akka.executor.service

import akka.actor.AbstractActor
import akka.actor.Props

import groovy.util.logging.Slf4j

@Slf4j
class Executor extends AbstractActor {

    Executor() { }

    static Props props() {
        return Props.create(Executor.class, {new Executor()})
    }

    @Override
    AbstractActor.Receive createReceive() {
        return receiveBuilder().match(Object.class, { msg ->

            log.info("START in Executor actor of node: ${msg}")

            // there should be placed scheduler

        }).build()
    }
}
