package com.fandf.demo.dataarchive.conf;

import com.fandf.demo.dataarchive.exception.AsyncThreadException;
import com.fandf.demo.dataarchive.handler.CustomAsyncExceptionHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Administrator
 */
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(new MyThreadFactory());
        // 设置核心线程数
        executor.setCorePoolSize(50);
        // 设置最大线程数
        executor.setMaxPoolSize(200);
        // 设置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(60);
        // 设置线程名前缀+分组名称
        executor.setThreadNamePrefix("AsyncOperationThread-");
        executor.setThreadGroupName("AsyncOperationGroup");
        // 所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }

//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() throws AsyncThreadException {
//        return (throwable, method, params) -> {
//            System.out.println("异常捕获---------------------------------");
//            System.out.println("Exception message - " + throwable.getMessage());
//            System.out.println("Method name - " + method.getName());
//            for (Object param : params) {
//                System.out.println("Parameter value - " + param);
//            }
//            System.out.println("异常捕获---------------------------------");
//            throw new AsyncThreadException(params[0].toString());
//        };
//    }

    static class MyThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler((t, e)->{
                throw new AsyncThreadException(e.getMessage());
            } );
            return thread;
        }
    }

}

