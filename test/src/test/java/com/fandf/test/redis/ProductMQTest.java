package com.fandf.test.redis;

import com.fandf.test.rabbit.RabbitMQConfig;
import org.junit.jupiter.api.Test;
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

}
