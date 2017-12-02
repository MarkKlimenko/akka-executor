package akka.executor.service

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.executor.service.actor.ProducerActor
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovy.util.logging.Slf4j

@Slf4j
class Producer {
    static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args[0]).withFallback(ConfigFactory.load())
        ActorSystem system = ActorSystem.create('ClusterSystem', config)

        ActorRef executorActor = system.actorOf(ProducerActor.props(), 'producer')
        executorActor.tell('produce', ActorRef.noSender())
    }
}
