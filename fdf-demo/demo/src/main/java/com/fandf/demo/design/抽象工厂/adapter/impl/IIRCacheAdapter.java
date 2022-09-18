package com.fandf.demo.design.抽象工厂.adapter.impl;

import com.fandf.demo.design.抽象工厂.adapter.ICacheAdapter;
import com.fandf.demo.design.抽象工厂.redis.cluster.IIR;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2022/9/18 17:08
 */
@Component
public class IIRCacheAdapter implements ICacheAdapter {

    IIR iir = new IIR();

    @Override
    public String get(String key) {
        return iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        iir.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        iir.setExpire(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        iir.del(key);
    }
}
