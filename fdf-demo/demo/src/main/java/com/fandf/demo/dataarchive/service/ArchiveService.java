package com.fandf.demo.dataarchive.service;

import com.fandf.demo.dataarchive.exception.AsyncThreadException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author fandongfeng
 * @date 2022-9-14 13:47
 */
@Service
public class ArchiveService {

    @Async
    public void updateReadCountHasResult(CountDownLatch countDownLatch, Integer a) throws Exception {
        // TODO 模拟耗时操作
        Thread.sleep(a);
        if (a > 2500) {
            throw new AsyncThreadException("1");
        }

        System.out.println("处理了一条数据" + Thread.currentThread().getName());
        countDownLatch.countDown();
    }

}
