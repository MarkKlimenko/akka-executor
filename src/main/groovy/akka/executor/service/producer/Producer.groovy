package akka.executor.service.producer

import akka.NotUsed
import akka.actor.ActorRef
import akka.actor.ActorSystem
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

import java.util.concurrent.CompletionStage

@Slf4j
class Producer extends  AllDirectives  {
    static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args[0]).withFallback(ConfigFactory.load())
        ActorSystem system = ActorSystem.create('ClusterSystem', config)

        final Http http = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system)
        Producer app = new Producer();

        ActorRef executorActor = system.actorOf(ProducerActor.props(), 'producer')

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute(executorActor).flow(system, materializer)
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer)
    }

    private Route createRoute(ActorRef executorActor) {
        return route(
                path("hello", {
                    get({
                        executorActor.tell('produce', ActorRef.noSender())
                        complete("<h1>Say hello to akka-http</h1>")
                    })
                })
        )
    }
}
