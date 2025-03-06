package boot;

import akka.actor.ActorRef;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class AkkaService {

/*
    @Autowired
*/
    private final List<ActorRef> actors;

    public AkkaService(List<ActorRef> actors) {
        this.actors = actors;
    }

    @PostConstruct
    public void startMessaging() {
        // Start sending messages between actors
        for (ActorRef actor : actors) {
            actor.tell("start", ActorRef.noSender());
        }
    }
}
