package com.fandf.demo.dataarchive.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.fandf.demo.dataarchive.service.ArchiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author fandongfeng
 * @date 2022-9-14 13:48
 */
@RestController
@RequestMapping("archive")
public class ArchiveApi {

    @Resource
    ArchiveService archiveService;

    @GetMapping
    public String getArticle() throws Exception {
        //模拟500条数据需要处理
        int count = 500;
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(RandomUtil.randomInt(1000, 3000));
        }

        // 存放异步返回结果的List
        List<FutureTask<Object>> futureTaskList = new ArrayList<>();
        // 线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 50, 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(count), new ThreadPoolExecutor.AbortPolicy());
        // 异常结果存放
        final List<String> results = new ArrayList<>();

        try {
            for (int i = 0; i < list.size(); i++) {
                Task task = new Task(i, list.get(i), new Callback() {
                    @Override
                    public void callbackmsq(String callback) {
                        if (callback != null) {
                            results.add(callback);
                            //一条错误立即停止
//                            executor.shutdownNow();
                            //所有错误都打印出来
                            executor.shutdown();
                        }
                    }
                });
                FutureTask<Object> futureTask = new FutureTask<>(task);
                executor.submit(futureTask);
                futureTaskList.add(futureTask);
            }
            for (FutureTask<Object> item : futureTaskList) {
                System.out.println("task运行结果" + item.get());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (!executor.isShutdown()) {
            executor.shutdown();
        }
        if (CollUtil.isNotEmpty(results)) {
            results.forEach(System.out::println);
        }
        return count + "";
    }


}
