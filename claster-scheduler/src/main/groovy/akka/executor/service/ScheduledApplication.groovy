package akka.executor.service

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Cancellable
import akka.actor.Props
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import groovy.util.logging.Slf4j
import scala.concurrent.duration.Duration
// import akka.actor.AbstractActor.Receive - need to be imported
import akka.actor.AbstractActor.Receive

import java.util.concurrent.TimeUnit

@Slf4j
class ScheduledApplication {
    static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args[0]).withFallback(ConfigFactory.load())
        ActorSystem system = ActorSystem.create('ClusterSystem', config)

        ActorRef tickActor = system.actorOf(Ticker.props(), 'consumer')

        Cancellable cancellable = system.scheduler().schedule(Duration.Zero(),
                Duration.create(5, TimeUnit.SECONDS), tickActor, "Tick",
                system.dispatcher(), null);
    }
}

@Slf4j
class Ticker extends AbstractActor {
    Ticker(){

    }

    static Props props() {
        return Props.create(Ticker.class, {new Ticker()})
    }

    @Override
    Receive createReceive() {
        return receiveBuilder()
                .matchEquals("Tick",{ m ->

            log.info('in scheduler')
        }).build()
    }
}
