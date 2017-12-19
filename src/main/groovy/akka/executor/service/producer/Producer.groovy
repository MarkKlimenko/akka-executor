package akka.executor.service.producer

import akka.NotUsed
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.dispatch.Mapper
import akka.dispatch.OnSuccess
import akka.executor.service.SystemProvider
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.ServerBinding
import akka.http.javadsl.model.HttpRequest
import akka.http.javadsl.model.HttpResponse
import akka.http.javadsl.server.AllDirectives
import akka.http.javadsl.server.Route
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import akka.stream.javadsl.Flow
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovy.util.logging.Slf4j
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import java.util.concurrent.Callable
import java.util.concurrent.CompletionStage


import static akka.dispatch.Futures.*

@Slf4j
class Producer extends  AllDirectives  {
    static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args[0]).withFallback(ConfigFactory.load())
        SystemProvider.createInstance(config)
        ActorSystem system = SystemProvider.instance()

        final Http http = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system)
        Producer app = new Producer();

        ActorRef executorActor = system.actorOf(ProducerActor.props(), 'producer')

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute(executorActor, system).flow(system, materializer)
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer)
    }

    private Route createRoute(ActorRef executorActor, ActorSystem system) {
        return route(
                path("hello", {
                    get({
                        log.info('START')
                        executorActor.tell('1', ActorRef.noSender())
                        executorActor.tell('2', ActorRef.noSender())
                        executorActor.tell('3', ActorRef.noSender())
                        executorActor.tell('4', ActorRef.noSender())
                        log.info('STOP')
                        complete("complete")
                    })
                })
        )
    }
}
