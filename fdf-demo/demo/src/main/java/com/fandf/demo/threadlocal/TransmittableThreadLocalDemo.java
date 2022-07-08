package com.fandf.demo.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2022/7/8 16:31
 */
public class TransmittableThreadLocalDemo {

    public static ThreadLocal<Map<String, Object>> threadLocal = new TransmittableThreadLocal<>();
    public static ExecutorService executorService =
            TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("name", "fandf");
        threadLocal.set(map);
        System.out.println("主线程读取本地变量：" + threadLocal.get().get("name"));

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get().get("name"));
        });

        TimeUnit.SECONDS.sleep(1);

        threadLocal.get().put("name", "ffff");
        System.out.println("主线程读取本地变量：" + threadLocal.get().get("name"));

        executorService.submit(() -> {
            //[读到了主线程修改后的新值]
            System.out.println("子线程读取本地变量：" + threadLocal.get().get("name"));
            threadLocal.get().put("name", "dddd");
            System.out.println("子线程读取本地变量：" + threadLocal.get().get("name"));
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("主线程读取本地变量：" + threadLocal.get().get("name"));
    }

}
