package akka.executor.service

import akka.actor.*
import akka.cluster.singleton.ClusterSingletonManager
import akka.cluster.singleton.ClusterSingletonManagerSettings
import akka.cluster.singleton.ClusterSingletonProxy
import akka.cluster.singleton.ClusterSingletonProxySettings
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


        final ClusterSingletonManagerSettings settings =
                ClusterSingletonManagerSettings.create(system).withRole("worker")
        ActorRef tickActor = system.actorOf(
                ClusterSingletonManager.props(
                        Ticker.props(),
                        PoisonPill.getInstance(),
                        settings),
                "consumer");


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
