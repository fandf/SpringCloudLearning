package com.fandf.rabbitmq.constant;

import lombok.Getter;

/**
 * 消息队列枚举类
 * Created by macro on 2018/9/14.
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("fdf.order.direct", "fdf.order.cancel", "fdf.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("fdf.order.direct.ttl", "fdf.order.cancel.ttl", "fdf.order.cancel.ttl");

    /**
     * 交换名称
     */
    private final String exchange;
    /**
     * 队列名称
     */
    private final String queue;
    /**
     * 路由键
     */
    private final String routeKey;

    QueueEnum(String exchange, String queue, String routeKey) {
        this.exchange = exchange;
        this.queue = queue;
        this.routeKey = routeKey;
    }
}