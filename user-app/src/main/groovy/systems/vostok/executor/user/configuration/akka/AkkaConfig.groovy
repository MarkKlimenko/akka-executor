package systems.vostok.executor.user.configuration.akka

import akka.actor.ActorSystem
import akka.actor.Extension
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AkkaConfig implements Extension {
    @Value('${akka.remote.netty.tcp.port}')
    String akkaPort

    @Bean
    Config systemConfig() {
        ConfigFactory.parseString("akka.remote.netty.tcp.port=$akkaPort")
                .withFallback(ConfigFactory.load())
    }

    @Bean
    ActorSystem actorSystem(Config systemConfig) {
        ActorSystem.create('UserApp', systemConfig)
    }
}
