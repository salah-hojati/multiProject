package boot;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;


import java.util.List;

public class ChatActor extends AbstractActor {
    private final String name;
    private final List<ActorRef> actors;

    public ChatActor(String name, List<ActorRef> actors) {
        this.name = name;
        this.actors = actors;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, message -> {
                    System.out.println("boot.Message from [" + message.sender + "] to [" + message.receiver + "]");
                })
                .matchEquals("start", msg -> {
                    for (ActorRef actor : actors) {
                        if (!actor.equals(getSelf())) {
                            actor.tell(new Message(name, actor.path().name()), getSelf());
                        }
                    }
                })
                .build();
    }
}
