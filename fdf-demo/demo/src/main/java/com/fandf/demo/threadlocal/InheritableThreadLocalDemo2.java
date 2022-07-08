package com.fandf.demo.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池环境下变量读取
 *
 * @author fandongfeng
 * @date 2022/7/8 16:25
 */
public class InheritableThreadLocalDemo2 {

    public static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        threadLocal.set(1);

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });

        TimeUnit.SECONDS.sleep(1);

        threadLocal.set(2);

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });
    }

}
