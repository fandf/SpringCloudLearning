package com.fandf.rabbitmq.sender;

import com.fandf.rabbitmq.constant.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的生产者
 * @author dongfengfan
 */
@Component
@Slf4j
public class CancelOrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Long orderId,final long delayTimes){
        //给延迟队列发送消息
        rabbitTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        log.info("send orderId:{}",orderId);
    }
}