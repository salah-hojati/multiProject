import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.List;

// ChatActor class that sends and receives messages
class ChatActor extends AbstractActor {
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
                    System.out.println("Message from [" + message.sender + "] to [" + message.receiver + "]");
                })
                .matchEquals("start", msg -> {
                    // Send messages to all other actors
                    for (ActorRef actor : actors) {
                        if (!actor.equals(getSelf())) {
                            actor.tell(new Message(name, actor.path().name()), getSelf());
                        }
                    }
                })
                .build();
    }
}
