package akka.executor.service

import akka.actor.AbstractActor
import akka.actor.Props
import akka.actor.AbstractActor.Receive

class Executor extends AbstractActor {

    static public Props props() {
        return Props.create(Executor.class, {new Executor()})
    }

    Executor() {

    }

    @Override
    Receive createReceive() {
        return receiveBuilder()
                .match(String.class, { msg ->

            println('test Actor')
            println('test Actor ' + msg)

        }).build()
    }
}
