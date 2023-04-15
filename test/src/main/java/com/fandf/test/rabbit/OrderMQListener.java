package com.fandf.test.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author fandongfeng
 * @date 2023/4/15 15:42
 */
@Component
@RabbitListener(queues = "ikun_queue")
public class OrderMQListener {

//    /**
//     * RabbitHandler 会自动匹配 消息类型（消息自动确认）
//     * @param msg 内容
//     * @param message mq消息
//     */
//    @RabbitHandler
//    public void consumer(String msg, Message message) {
//        long msgTag = message.getMessageProperties().getDeliveryTag();
//        System.out.println("msg="+msg);
//        System.out.println("msgTag="+msgTag);
//        System.out.println("message="+ message);
//        System.out.println("监听到消息：消息内容:"+message.getBody());
//    }

    @RabbitHandler
    public void consumer(String body, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("msgTag="+msgTag);
        System.out.println("message="+ message);
        System.out.println("body="+body);

        //成功确认，使用此回执方法后，消息会被 rabbitmq broker 删除
        channel.basicAck(msgTag,false);
//        channel.basicNack(msgTag,false,true);

    }

}
