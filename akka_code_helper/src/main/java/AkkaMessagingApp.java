import akka.actor.*;

import java.util.ArrayList;
import java.util.List;

// Main Application
public class AkkaMessagingApp {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("MessagingSystem");

        // Create a list to store actor references
        List<ActorRef> actors = new ArrayList<>();
        actors.add(system.actorOf(Props.create(ChatActor.class, () -> new ChatActor("Actor1", actors)), "Actor1"));
        actors.add(system.actorOf(Props.create(ChatActor.class, () -> new ChatActor("Actor2", actors)), "Actor2"));
        actors.add(system.actorOf(Props.create(ChatActor.class, () -> new ChatActor("Actor3", actors)), "Actor3"));

        // Start the message exchange
        for (ActorRef actor : actors) {
            actor.tell("start", ActorRef.noSender());
        }

        // Give some time for messages to be processed
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.terminate();
    }
}
