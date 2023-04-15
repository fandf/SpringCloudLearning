package com.fandf.test.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author fandongfeng
 * @date 2023/4/15 15:42
 */
@Component
@RabbitListener(queues = "ikun_queue")
public class OrderMQListener {

    /**
     * RabbitHandler 会自动匹配 消息类型（消息自动确认）
     * @param msg 内存
     * @param message mq消息
     */
    @RabbitHandler
    public void releaseCouponRecord(String msg, Message message) {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("msg="+msg);
        System.out.println("msgTag="+msgTag);
        System.out.println("message="+ message);
        System.out.println("监听到消息：消息内容:"+message.getBody());
    }

}
