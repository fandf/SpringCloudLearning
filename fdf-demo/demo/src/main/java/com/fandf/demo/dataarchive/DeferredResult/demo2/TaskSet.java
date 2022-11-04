package com.fandf.demo.dataarchive.DeferredResult.demo2;

import com.fandf.demo.dataarchive.api.Task;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Data
public class TaskSet {

   private final ExecutorService executorService = Executors.newFixedThreadPool(12);

    private Set<DeferredResult<ResponseMsg<String>>> set = new HashSet<>();

    private Cache<String, Object> cacheMap = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public void put(String key, Task task) {
        cacheMap.put(key, "正在执行");
        executorService.submit(task);

    }

}