package com.fandf.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: fandongfeng
 * @date: 2023-3-2416:45
 */
@SpringBootTest
class RedissonTest {

    @Resource
    private RedissonClient redisson;


    @Test
    public void watchDog() throws InterruptedException {
        RLock lock = redisson.getLock("123");
        lock.lock();
        Thread.sleep(1000000);
    }
}