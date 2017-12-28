package akka.executor.service

import akka.actor.ActorRef
import akka.actor.ActorSystem
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


        ActorRef executorActor = system.actorOf(Executor.props(), 'executorActor')
        executorActor.tell("execute ${args[0]}", ActorRef.noSender())
    }
}

