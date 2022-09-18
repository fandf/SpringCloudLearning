package com.fandf.demo.design.抽象工厂.redis.cluster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 模拟redis缓存服务， IIR
 * @author fandongfeng
 * @date 2022/9/18 16:52
 */
@Slf4j
@Component
public class IIR {

    @Bean
    public IIR iir() {
        IIR iir = new IIR();
        return iir;
    }

    /**
     * 模拟缓存
     */
    private final Map<String, String> dataMap = new ConcurrentHashMap<>();

    public String get(String key) {
        log.info("IIR 获取数据key :{}", key);
        return dataMap.get(key);
    }

    public void set(String key, String value) {
        log.info("IIR 写入数据key :{} , value: {}", key, value);
        dataMap.put(key, value);
    }

    public void setExpire(String key, String value, long timeout, TimeUnit timeUnit) {
        log.info("IIR 写入数据key :{} , value: {}, timeout:{}, timeUnit:{}", key, value, timeout, timeUnit);
        dataMap.put(key, value);
    }

    public void del(String key) {
        log.info("IIR 删除数据key :{}", key);
        dataMap.remove(key);
    }

}
