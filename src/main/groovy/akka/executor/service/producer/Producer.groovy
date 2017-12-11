package akka.executor.service.producer

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.ServerBinding
import akka.http.javadsl.model.ContentTypes
import akka.http.javadsl.model.HttpResponse
import akka.pattern.Patterns
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import akka.util.ByteString
import akka.util.Timeout
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovy.util.logging.Slf4j

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.util.ByteString;


import java.util.concurrent.CompletionStage


@Slf4j
class Producer {
    static void main(String[] args) {
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args[0]).withFallback(ConfigFactory.load())
        ActorSystem system = ActorSystem.create('ClusterSystem', config)
        Materializer materializer = ActorMaterializer.create(system)
        ActorRef executorActor = system.actorOf(ProducerActor.props(), 'producer')


        CompletionStage<ServerBinding> serverBindingFuture =
                Http.get(system).bindAndHandleSync(
                        { request ->
                            if (request.getUri().path().equals("/")) {


                                executorActor.tell('produce', ActorRef.noSender())

                                return HttpResponse
                                        .create()
                                        .withEntity(ContentTypes.TEXT_HTML_UTF8, ByteString.fromString("OK"))
                            }
                        }, ConnectHttp.toHost("localhost", 8084), materializer)
    }
}
