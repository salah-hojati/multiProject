package boot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringAkkaConfig {

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("SpringAkkaSystem");
    }


   @Bean
    public List<ActorRef> actorList(ActorSystem actorSystem) {
        List<ActorRef> actors = new ArrayList<>();
        actors.add(actorSystem.actorOf(Props.create(ChatActor.class, () -> new ChatActor("Actor1", actors)), "Actor1"));
        actors.add(actorSystem.actorOf(Props.create(ChatActor.class, () -> new ChatActor("Actor2", actors)), "Actor2"));
        actors.add(actorSystem.actorOf(Props.create(ChatActor.class, () -> new ChatActor("Actor3", actors)), "Actor3"));
        return actors;
    }
}
