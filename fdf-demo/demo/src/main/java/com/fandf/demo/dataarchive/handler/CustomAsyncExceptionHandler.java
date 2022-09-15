package com.fandf.demo.dataarchive.handler;

import com.fandf.demo.dataarchive.exception.AsyncThreadException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @author Administrator
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) throws AsyncThreadException {
        System.out.println("异常捕获---------------------------------");
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
        System.out.println("异常捕获---------------------------------");
        throw new AsyncThreadException(obj[0].toString());
    }

}