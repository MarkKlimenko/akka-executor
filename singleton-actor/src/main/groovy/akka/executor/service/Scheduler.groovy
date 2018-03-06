package akka.executor.service

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Cancellable
import akka.actor.Props
import akka.executor.service.tasks.ScheduledTask
import groovy.util.logging.Slf4j
import scala.concurrent.duration.Duration

import java.util.concurrent.TimeUnit

@Slf4j
class Scheduler extends AbstractActor {

    Scheduler() {
        executeScheduledTask()
    }

    static Props props() {
        return Props.create(Scheduler.class, { new Scheduler() })
    }

    @Override
    AbstractActor.Receive createReceive() {
        return receiveBuilder().match(Object.class, { msg ->

        }).build()
    }

    void executeScheduledTask() {
        log.info('in Scheduler')
        ActorSystem system = getContext().system()
        ActorRef scheduledTaskActor = system.actorOf(ScheduledTask.props(), 'scheduledTask')
        Cancellable cancellable = system.scheduler().schedule(Duration.Zero(),
                Duration.create(5, TimeUnit.SECONDS), scheduledTaskActor, 'message',
                system.dispatcher(), null)
        log.info('finish in Scheduler')
    }
}
