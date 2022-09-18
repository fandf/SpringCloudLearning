package com.fandf.demo.design.抽象工厂.factory;

import com.fandf.demo.design.抽象工厂.adapter.ICacheAdapter;
import com.fandf.demo.design.抽象工厂.utils.ClassLoaderUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author fandongfeng
 * @date 2022/9/18 17:38
 */
public class JDKInvocationHandler implements InvocationHandler {

    private final ICacheAdapter cacheAdapter;

    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {
        this.cacheAdapter = cacheAdapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return ICacheAdapter.class.getMethod(method.getName(),
                ClassLoaderUtils.getClazzByArgs(args))
                .invoke(cacheAdapter, args);
    }
}
