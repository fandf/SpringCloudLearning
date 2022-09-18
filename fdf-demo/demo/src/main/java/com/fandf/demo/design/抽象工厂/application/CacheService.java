package com.fandf.demo.design.抽象工厂.application;

import java.util.concurrent.TimeUnit;

/**
 * 缓存接口
 * @author fandongfeng
 * @date 2022/9/18 16:52
 */
public interface CacheService {

    String get(final String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);

}
