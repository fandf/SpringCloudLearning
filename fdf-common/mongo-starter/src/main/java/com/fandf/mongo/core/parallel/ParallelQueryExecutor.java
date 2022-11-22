package com.fandf.mongo.core.parallel;

import com.fandf.mongo.core.exception.BaseException;
import com.fandf.mongo.core.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParallelQueryExecutor {

    private static final Logger LOGGER = Logger.getLogger("com.bugull.mongo");

    /**
     * Execute BuguQuery or BuguAggregation in parallel.
     *
     * @param querys
     * @return
     */
    public List<Iterable> execute(Parallelable... querys) {
        List<ParallelTask> taskList = new ArrayList<>();
        for (Parallelable query : querys) {
            taskList.add(new ParallelTask(query));
        }
        int len = querys.length;
        if (len <= 1) {
            LOGGER.log(Level.WARNING, "You should NOT use parallel query when only one query!");
        }
        int max = Runtime.getRuntime().availableProcessors() * 2 + 1;
        if (len > max) {
            len = max;
        }
        ExecutorService es = Executors.newFixedThreadPool(len);
        List<Iterable> result = new ArrayList<>();
        try {
            List<Future<Iterable>> futureList = es.invokeAll(taskList);
            for (Future<Iterable> future : futureList) {
                result.add(future.get());
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new BaseException(ex.getMessage());
        } finally {
            ThreadUtil.safeClose(es);
        }
        return result;
    }

}
