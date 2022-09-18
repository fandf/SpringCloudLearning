package com.fandf.demo.design.抽象工厂.adapter.impl;

import com.fandf.demo.design.抽象工厂.adapter.ICacheAdapter;
import com.fandf.demo.design.抽象工厂.redis.cluster.EGM;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2022/9/18 17:08
 */
@Component
public class EGMCacheAdapter implements ICacheAdapter {

//    @Resource
//    EGM egm;
    EGM egm = new EGM();


    @Override
    public String get(String key) {
        return egm.gain(key);
    }

    @Override
    public void set(String key, String value) {
        egm.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        egm.setEx(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        egm.del(key);
    }
}
