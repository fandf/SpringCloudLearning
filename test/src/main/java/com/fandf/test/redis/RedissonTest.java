package com.fandf.test.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 */
@Component
@Slf4j
public class RedissonTest {

    @Resource
    RedissonClient redissonClient;

    public void test() {
        RLock rLock = redissonClient.getLock("product");
        //rLock.lock(10, TimeUnit.SECONDS);
        rLock.lock();
        try {
            // do something
        } catch (Exception e) {
            log.error("业务异常", e);
        } finally {
            rLock.unlock();
        }

    }

}
