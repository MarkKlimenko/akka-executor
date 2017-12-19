package akka.executor.service

import akka.actor.ActorSystem
import com.typesafe.config.Config

class SystemProvider {

    private static ActorSystem system

    static void createInstance(Config config) {
        system = ActorSystem.create('ClusterSystem', config)
    }

    static ActorSystem instance() {
        system
    }
}
