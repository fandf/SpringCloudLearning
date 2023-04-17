package com.fandf.test.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2023/4/15 15:38
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 订单交换机
     */
    public static final String ORDER_EXCHANGE = "order_exchange";
    /**
     * 订单队列
     */
    public static final String ORDER_QUEUE = "order_queue";
    /**
     * 订单路由key
     */
    public static final String ORDER_QUEUE_ROUTING_KEY = "order.#";

    /**
     * 死信交换机
     */
    public static final String ORDER_DEAD_LETTER_EXCHANGE = "order_dead_letter_exchange";
    /**
     * 死信队列 routingKey
     */
    public static final String ORDER_DEAD_LETTER_QUEUE_ROUTING_KEY = "order_dead_letter_queue_routing_key";

    /**
     * 死信队列
     */
    public static final String ORDER_DEAD_LETTER_QUEUE = "order_dead_letter_queue";


    /**
     * 创建死信交换机
     */
    @Bean("orderDeadLetterExchange")
    public Exchange orderDeadLetterExchange() {
        return new TopicExchange(ORDER_DEAD_LETTER_EXCHANGE, true, false);
    }

    /**
     * 创建死信队列
     */
    @Bean("orderDeadLetterQueue")
    public Queue orderDeadLetterQueue() {
        return QueueBuilder.durable(ORDER_DEAD_LETTER_QUEUE).build();
    }

    /**
     * 绑定死信交换机和死信队列
     */
    @Bean("orderDeadLetterBinding")
    public Binding orderDeadLetterBinding(@Qualifier("orderDeadLetterQueue") Queue queue, @Qualifier("orderDeadLetterExchange")Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_DEAD_LETTER_QUEUE_ROUTING_KEY).noargs();
    }


    /**
     * 创建订单交换机
     */
    @Bean("orderExchange")
    public Exchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE, true, false);
    }

    /**
     * 创建订单队列
     */
    @Bean("orderQueue")
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>(3);
        //消息过期后，进入到死信交换机
        args.put("x-dead-letter-exchange", ORDER_DEAD_LETTER_EXCHANGE);

        //消息过期后，进入到死信交换机的路由key
        args.put("x-dead-letter-routing-key", ORDER_DEAD_LETTER_QUEUE_ROUTING_KEY);

        //过期时间，单位毫秒
        args.put("x-message-ttl", 10000);

        return QueueBuilder.durable(ORDER_QUEUE).withArguments(args).build();
    }

    /**
     * 绑定订单交换机和队列
     */
    @Bean("orderBinding")
    public Binding orderBinding(@Qualifier("orderQueue") Queue queue, @Qualifier("orderExchange")Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_QUEUE_ROUTING_KEY).noargs();
    }


}
