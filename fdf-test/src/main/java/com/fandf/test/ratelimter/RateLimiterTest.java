package com.fandf.test.ratelimter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2023/4/9 19:55
 */
public class RateLimiterTest {

    public static void main(String[] args) {
        //指定速率，每秒产生一个令牌
        RateLimiter rateLimiter = RateLimiter.create(1);
        System.out.println(rateLimiter.getRate());
        rateLimiter.setRate(2);
        System.out.println(rateLimiter.getRate());

        //获取1个令牌
        boolean result = rateLimiter.tryAcquire(1);
        System.out.println(result);

        boolean result2 = rateLimiter.tryAcquire(1, 2, TimeUnit.SECONDS);
        System.out.println(result2);
    }

}
