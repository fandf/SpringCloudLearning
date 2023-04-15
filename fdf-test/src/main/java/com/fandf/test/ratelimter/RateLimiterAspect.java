package com.fandf.test.ratelimter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 限流切面
 * @author fandongfeng
 * @date 2023/4/9 20:19
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    /**
     * key: 类全路径+方法名
     */
    private static final ConcurrentMap<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();


    @Around("@within(limiter) || @annotation(limiter)")
    public Object pointcut(ProceedingJoinPoint point, Limiter limiter) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if (limiter != null && limiter.qps() > Limiter.NOT_LIMITED) {
            double qps = limiter.qps();
            //这个key可以根据具体需求配置,例如根据ip限制,或用户
            String key = method.getDeclaringClass().getName() + method.getName();
            if (RATE_LIMITER_CACHE.get(key) == null) {
                // 初始化 QPS
                RATE_LIMITER_CACHE.put(key, RateLimiter.create(qps));
            }
            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(key) != null && !RATE_LIMITER_CACHE.get(key).tryAcquire(limiter.timeout(), limiter.timeUnit())) {
                log.error("触发限流操作{}", key);
                throw new RuntimeException(limiter.msg());
            }
        }
        return point.proceed();
    }
}
