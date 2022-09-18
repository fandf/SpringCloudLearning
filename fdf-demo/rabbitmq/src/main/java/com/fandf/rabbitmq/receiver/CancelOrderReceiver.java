package com.fandf.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的消费者
 *
 * @author dongfengfan
 */
@Component
@RabbitListener(queues = "fdf.order.cancel")
public class CancelOrderReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderReceiver.class);

    @RabbitHandler
    public void handle(Channel channel, String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            //String message= (String)amqpTemplate.receiveAndConvert(RabbitMQConfig.DIRECT_QUEUE);
            System.out.println("接收的mq消息：" + message);
            // 业务处理 异常测试
            // System.out.println("业务处理"+1/0);
            // long deliveryTag 消息接收tag boolean multiple 是否批量确认
            System.out.println("deliveryTag=" + deliveryTag);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

            e.printStackTrace();

            /**
             * 有异常就绝收消息
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
             *         false:将消息丢弃
             */

            try {
                channel.basicNack(deliveryTag, false, true);
                // long deliveryTag, boolean requeue
                // channel.basicReject(deliveryTag,true);
                Thread.sleep(1000);     // 这里只是便于出现死循环时查看
                /*
                 * 一般实际异常情况下的处理过程：记录出现异常的业务数据，将它单独插入到一个单独的模块，
                 * 然后尝试3次，如果还是处理失败的话，就进行人工介入处理
                 */
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}