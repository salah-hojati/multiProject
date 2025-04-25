package com.example.rabbitmq.rabbitmqdemo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    static final String queue_name = "rabbit_test_queue";

    @Bean
    public Queue queue() {
        return new Queue(queue_name, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("rabbit_test_exchange");
    }

    @Bean
    public Binding queueBinding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(queue_name);
    }

    @Bean
    SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setMessageListener(listenerAdapter);
        messageListenerContainer.setQueueNames(queue_name);
        return messageListenerContainer;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitListener rabbitListener) {
        return new MessageListenerAdapter(rabbitListener, "receiveMessage");
    }

    public static String getQueue_name() {
        return queue_name;
    }
}
