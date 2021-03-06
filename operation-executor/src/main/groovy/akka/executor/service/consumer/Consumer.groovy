package akka.executor.service.consumer

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovy.util.logging.Slf4j

@Slf4j
class Consumer {

    static String clusterRole = 'consumer'

    static void main(String[] args) {
        Config config = ConfigFactory
                .parseString("""akka.cluster.roles = [ "${clusterRole}" ] \n akka.remote.netty.tcp.port=${args[0]} """)
                .withFallback(ConfigFactory.load())

        ActorSystem system = ActorSystem.create('ClusterSystem', config)

        system.actorOf(ResponseConsumerActor.props(), 'consumer')
        //system.actorOf(ResponseConsumerActor.props(), 'responseConsumer')
    }
}
