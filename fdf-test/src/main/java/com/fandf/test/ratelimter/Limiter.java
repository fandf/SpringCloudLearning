package com.fandf.test.ratelimter;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 *
 * @author fandongfeng
 * @date 2023/4/9 20:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiter {

    int NOT_LIMITED = 0;

    /**
     * qps (每秒并发量)
     */
    double qps() default NOT_LIMITED;

    /**
     * 超时时长,默认不等待
     */
    int timeout() default 0;

    /**
     * 超时时间单位,默认毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 返回错误信息
     */
    String msg() default "系统忙，请稍后再试";
}
