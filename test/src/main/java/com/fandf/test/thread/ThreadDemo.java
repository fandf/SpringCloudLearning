package com.fandf.test.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author fandongfeng
 * @date 2023/5/22 07:23
 */
public class ThreadDemo {


    public static void main(String[] args) {
        int poolSize = 10;
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("custom-thread-%d").build();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize, threadFactory);
//        // ... 使用 executor 执行任务 ...
//        executor.shutdown();

    }

    private static void test1() {
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            new Thread(()-> System.out.println("new Thread:" + finalI)).start();
        }
    }
 private static void test2() {
     ExecutorService executorService = Executors.newSingleThreadExecutor();
     executorService.execute();
 }

}
