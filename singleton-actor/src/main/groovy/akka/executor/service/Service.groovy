package akka.executor.service

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import akka.cluster.singleton.ClusterSingletonManager
import akka.cluster.singleton.ClusterSingletonManagerSettings
import akka.cluster.singleton.ClusterSingletonProxy
import akka.cluster.singleton.ClusterSingletonProxySettings
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovy.util.logging.Slf4j

@Slf4j
class Service {
    static void main(String[] args) {
        Config config = ConfigFactory
                .parseString("akka.remote.netty.tcp.port=" + args[0])
                .withFallback(ConfigFactory.load())

        ActorSystem system = ActorSystem.create('TestedSystem', config)

        // create singleton manager only for cluster nodes with role worker
        final ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(system).withRole('worker')
        // create actor
        system.actorOf(ClusterSingletonManager.props(Scheduler.props(), PoisonPill.getInstance(), settings), 'consumer')

        // ACHTUNG Не удалять, очень нужно для образца !!!
        // use actor per proxy -- not use tell, need to execute on singleton creation
        /*ClusterSingletonProxySettings proxySettings = ClusterSingletonProxySettings.create(system).withRole('worker')
        ActorRef proxy = system.actorOf(ClusterSingletonProxy.props('/user/consumer', proxySettings), 'consumerProxy')
        proxy.tell(args[0], ActorRef.noSender())*/
    }
}

