package com.fandf.test.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author fandongfeng
 * @date 2023/5/21 19:05
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {

        //创建队列，供生产者投递，消费者消费
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        //生产者线程
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                boolean offer = blockingQueue.offer(i);
                System.out.println(Thread.currentThread().getName() + ",入队：" + i + ",结果：" + offer);
            }
        }, "生产者").start();

        //消费者线程
        new Thread(() -> {
            while (true) {
                Integer poll = blockingQueue.poll();
                if(poll != null) {
                    System.out.println(Thread.currentThread().getName() + ",出队：" + poll);
                }
            }
        }, "消费者").start();

    }

}
