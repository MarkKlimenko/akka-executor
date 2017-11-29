package akka.executor.service

import akka.actor.ActorRef
import akka.actor.ActorSystem

class Service {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");
        final ActorRef executorActor = system.actorOf(Executor.props(), "printerActor")

        executorActor.tell('test actor Message', ActorRef.noSender())
    }
}
