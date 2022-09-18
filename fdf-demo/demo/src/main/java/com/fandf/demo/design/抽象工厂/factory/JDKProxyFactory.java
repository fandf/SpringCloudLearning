package com.fandf.demo.design.抽象工厂.factory;

import com.fandf.demo.design.抽象工厂.adapter.ICacheAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK代理工厂
 * @author fandongfeng
 * @date 2022/9/18 17:33
 */
public class JDKProxyFactory {

    public static <T> T getProxy(Class<T> cacheClazz, Class<? extends ICacheAdapter> cacheAdapter) throws Exception {
        InvocationHandler handler = new JDKInvocationHandler(cacheAdapter.newInstance());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{cacheClazz}, handler);
    }

}
