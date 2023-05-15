package com.fandf.test.forkjoin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author fandongfeng
 */
@Slf4j
public class ForkJoinDemo extends RecursiveTask<Long> {

    /**
     * 小任务的大小阈值
     */
    public static final int TASK_SIZE = 100000;
    /**
     * 开始数字
     */
    private final Long start;
    /**
     * 结束数字
     */
    private final Long end;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0L;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= TASK_SIZE;
        if (canCompute) {
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            long middle = (start + end) / 2;
            ForkJoinDemo leftTask = new ForkJoinDemo(start, middle);
            ForkJoinDemo rightTask = new ForkJoinDemo(middle + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行结束合并其结果
            Long leftResult = leftTask.join();
            Long rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkjoinPool = new ForkJoinPool();
        //生成一个计算任务，计算1+2+3+4+...+100000000
        ForkJoinDemo task = new ForkJoinDemo(1L, 100000000L);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //执行一个任务
        Future<Long> result = forkjoinPool.submit(task);

        try {
            System.out.println("result:" + result.get());
        } catch (Exception e) {
            log.error("exception", e);
        }
        stopWatch.stop();
        System.out.println("总耗时：" + stopWatch.getTotalTimeMillis() + "毫秒");
        System.out.println("getParallelism:" + forkjoinPool.getParallelism());
        System.out.println("getPoolSize:" + forkjoinPool.getPoolSize());
    }
}
