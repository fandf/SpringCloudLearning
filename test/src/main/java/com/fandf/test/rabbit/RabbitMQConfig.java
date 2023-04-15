package com.fandf.test.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fandongfeng
 * @date 2023/4/15 15:38
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "ikun_exchange";
    public static final String QUEUE_NAME = "ikun_queue";

    /**
     * 交换机
     */
    @Bean
    public Exchange ikunExchange() {
        //return new TopicExchange(EXCHANGE_NAME, true, false);
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }
    /**
     * 队列
     */
    @Bean
    public Queue ikunQueue() {
        //return new Queue(QUEUE_NAME, true, false, false, null);
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 交换机和队列绑定关系
     */
    @Bean
    public Binding ikunBinding(Queue queue, Exchange exchange) {
        //return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_NAME, "ikun.#", null);
        return BindingBuilder.bind(queue).to(exchange).with("ikun.#").noargs();
    }

}
