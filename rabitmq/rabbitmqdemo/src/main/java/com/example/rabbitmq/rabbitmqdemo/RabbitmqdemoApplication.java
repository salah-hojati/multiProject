package com.example.rabbitmq.rabbitmqdemo;

import java.util.concurrent.TimeUnit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqdemoApplication implements CommandLineRunner {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitListener listener;

	@Override
	public void run(String... strings) throws Exception {
		rabbitTemplate.convertAndSend(RabbitConfig.getQueue_name(), "Hello from RabbitMQ");
		listener.getCountDownLatch().await(10000, TimeUnit.MICROSECONDS);
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqdemoApplication.class, args);
	}
}
