package akka.executor.service.tasks

import akka.actor.AbstractActor
import akka.actor.Props
import groovy.util.logging.Slf4j

@Slf4j
class ScheduledTask extends AbstractActor {
    ScheduledTask(){

    }

    static Props props() {
        return Props.create(ScheduledTask.class, {new ScheduledTask()})
    }

    @Override
    AbstractActor.Receive createReceive() {
        return receiveBuilder().match(Object.class,{ m ->

            log.info("Execute scheduled task for ${m}")

        }).build()
    }

}
