package com.fandf.rabbitmq.sender;

import com.fandf.rabbitmq.constant.QueueEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/7/22 18:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send() throws InterruptedException {
        rabbitTemplate.setConfirmCallback(( correlationData,  ack,  cause) ->{
            System.out.println("correlationData: " + correlationData);
            System.out.println("ack: " + ack);
            if(!ack){
                System.out.println("异常处理....");
            }
            System.out.println("接收成功....");
        });
        rabbitTemplate.setReturnsCallback(message->{
            System.out.println("ReturnsCallback: " + message);
        });
        rabbitTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_CANCEL.getExchange(),
                QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey(), "123456");
        Thread.sleep(5000);
    }


}
