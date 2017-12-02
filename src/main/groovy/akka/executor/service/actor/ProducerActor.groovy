package akka.executor.service.actor

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props

// import akka.actor.AbstractActor.Receive - need to be imported
import akka.actor.AbstractActor.Receive
import akka.routing.FromConfig
import groovy.util.logging.Slf4j

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

            while(true) {
                sleep(2000)
                log.info("START in test actor of node ${msg}")
                consumerRouter.tell('produce', ActorRef.noSender())
            }

        }).build()
    }
}
