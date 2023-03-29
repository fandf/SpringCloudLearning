package com.fandf.test.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * @Description:
 * @author: fandongfeng
 * @date: 2023-3-2416:45
 */
@SpringBootTest
class SignServiceTest {

    @Resource
    SignService signService;
    @Resource
    RedisLock redisLock;

    @Test
    void sign() {
        signService.sign();
    }

    @Test
    void signCount() {
        Integer result = signService.signCount();
        System.out.println(result);
    }


    @RepeatedTest(100)
    @Execution(CONCURRENT)
    public void redisLock() {
        String result = redisLock.kill();
        if("加锁失败".equals(result)) {

        }else {
            System.out.println(result);
        }
    }
}