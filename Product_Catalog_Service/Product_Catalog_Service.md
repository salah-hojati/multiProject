# Product_Catalog_Service
## model

    private Long id;
    private String name;
    private String description;
    private Double price;


- ***tasks***
  - simple spring boot project rest for persist in H2
  - simple rabbitMq client for receive  message  and save in h2 . after send message to sender 

***
- *****simple Spring Boot application that does the following*****
  - Receives a message from RabbitMQ.
  - Saves the message into an H2 database.
  -  Sends back the message's ID as a response.
  

- ***Spring Boot application***
  - Spring AMQP for RabbitMQ communication.
  - Spring Data JPA for H2 database interaction.
  - Spring Boot Starter Web (optional if you want to expose APIs)
***

- ***Steps:***
  1. Set up RabbitMQ (make sure RabbitMQ is running).
  2. Configure Spring Boot application.
  3. Create an Entity and Repository for H2.
  4. Create a Service to handle message processing.
  5. Create a Listener for RabbitMQ.
  6. Send a response message back to RabbitMQ
****************

1. Add Dependencies (pom.xml)

        <dependencies>
         <!-- Spring Boot Starter for RabbitMQ -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>

        <!-- Spring Boot Starter for Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- H2 Database -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok (optional) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
        </dependencies>

    
2. Configure application.yml

       spring:
        rabbitmq:
        host: localhost
        port: 5672
        username: guest
        password: guest

        datasource:
        url: jdbc:h2:mem:testdb
        driver-class-name: org.h2.Driver
        username: sa
        password:
        jpa:
        database-platform: org.hibernate.dialect.H2Dialect
        hibernate:
        ddl-auto: update
        show-sql: true


3. Define the Entity (Product.java)


      import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Double price;
    }

4. Create Repository (ProductRepository.java)
  

       import org.springframework.data.jpa.repository.JpaRepository;

        public interface ProductRepository extends JpaRepository<Product, Long> {
        }

5. Create the Service (ProductService.java)


        import org.springframework.stereotype.Service;
         import org.springframework.transaction.annotation.Transactional;

         @Service
         public class ProductService {
         private final ProductRepository repository;

        public ProductService(ProductRepository repository) {
        this.repository = repository;
         }

         @Transactional
         public Long saveProduct(Product product) {
        return repository.save(product).getId();
         }
          }


6.  Define the boot.Message Listener (RabbitMqListener.java)


    import org.springframework.amqp.rabbit.annotation.RabbitListener;
    import org.springframework.amqp.rabbit.core.RabbitTemplate;
    import org.springframework.stereotype.Component;

    @Component
    public class RabbitMqListener {

    private final ProductService productService;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqListener(ProductService productService, RabbitTemplate rabbitTemplate) {
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "productQueue")
    public void receiveMessage(Product product) {
        System.out.println("Received message: " + product);
        Long productId = productService.saveProduct(product);
        System.out.println("Saved product with ID: " + productId);

        // Send response message
        rabbitTemplate.convertAndSend("responseQueue", productId);
    }
    }

7. Define RabbitMQ Config (RabbitMqConfig.java)

        import org.springframework.amqp.core.*;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

        @Configuration
        public class RabbitMqConfig {

        @Bean
        public Queue productQueue() {
        return new Queue("productQueue");
        }

        @Bean
        public Queue responseQueue() {
        return new Queue("responseQueue");
        }

        @Bean
        public TopicExchange exchange() {
        return new TopicExchange("productExchange");
        }

        @Bean
        public Binding binding(Queue productQueue, TopicExchange exchange) {
        return BindingBuilder.bind(productQueue).to(exchange).with("product.routingKey");
        }

        @Bean
        public Binding responseBinding(Queue responseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(responseQueue).to(exchange).with("response.routingKey");
        }
        }

8. Send boot.Message (Test Sender)

To test sending messages, create a simple sender.

     import org.springframework.amqp.rabbit.core.RabbitTemplate;
    import org.springframework.stereotype.Component;

    @Component
    public class ProductSender {

    private final RabbitTemplate rabbitTemplate;

    public ProductSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProduct(Product product) {
        System.out.println("Sending product: " + product);
        rabbitTemplate.convertAndSend("productExchange", "product.routingKey", product);
    }
    }


   10. Main Class (Application.java)
   
    import org.springframework.boot.CommandLineRunner;
      import org.springframework.boot.SpringApplication;
      import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication
    public class Application implements CommandLineRunner {

    private final ProductSender productSender;

    public Application(ProductSender productSender) {
        this.productSender = productSender;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        // Send a test product
        Product product = new Product(null, "Laptop", "Gaming Laptop", 1500.0);
        productSender.sendProduct(product);
    }
    }
10. Run the Application


    Start RabbitMQ.
    Run this Spring Boot application.
    It will:
    Send a test product.
    Receive the message in RabbitMQ.
    Save it in H2.
    Send back the product ID.


11. Verify in H2 Database
    You can check the saved data in H2:

      Open H2 console: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Run:
sql
Copy
Edit
SELECT * FROM PRODUCT;

12. RabbitMQ UI
    You can check the queues in RabbitMQ UI at: http://localhost:15672 (User: guest, Pass: guest).

