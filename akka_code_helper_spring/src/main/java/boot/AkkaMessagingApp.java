package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AkkaMessagingApp {
    public static void main(String[] args) {
        SpringApplication.run(AkkaMessagingApp.class, args);
    }
}
