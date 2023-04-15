package com.fandf.test.redis;

import com.fandf.test.rabbit.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2023/4/15 15:46
 */
@SpringBootTest
public class ProductMQTest {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"ikun.mei", "鸡你太美");
        Thread.sleep(10000);
    }

    @Test
    public void testConfirmCallback() throws InterruptedException {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 配置
             * @param ack 交换机是否收到消息，true是成功，false是失败
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm=====>");
                System.out.println("confirm==== ack="+ack);
                System.out.println("confirm==== cause="+cause);
                //根据ACK状态做对应的消息更新操作 TODO
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"ikun.mei", "鸡你太美");
        Thread.sleep(10000);
    }


    @Test
    void testReturnCallback() {
        //为true,则交换机处理消息到路由失败，则会返回给生产者  配置文件指定，则这里不需指定
        rabbitTemplate.setMandatory(true);
        //开启强制消息投递（mandatory为设置为true），但消息未被路由至任何一个queue，则回退一条消息
        rabbitTemplate.setReturnsCallback(returned -> {
            int code = returned.getReplyCode();
            System.out.println("code="+code);
            System.out.println("returned="+ returned);
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"123456","测试returnCallback");
    }

}
