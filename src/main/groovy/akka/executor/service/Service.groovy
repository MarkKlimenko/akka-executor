package akka.executor.service

import akka.actor.ActorRef
import akka.actor.ActorSystem
import groovy.util.logging.Slf4j

@Slf4j
class Service {
    static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");
        final ActorRef executorActor = system.actorOf(Executor.props(), "printerActor")

        if(args[1] == 'main') {
            5.times{
                sleep(2000)
                 log.info("Main ${args[0]} call actor")
                executorActor.tell("${args[0]} : ${it}", ActorRef.noSender())
            }
        }
    }
}
