package com.fandf.test.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2023/5/21 16:57
 */
public class QueueDemo {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        //存入队列
        queue.offer("java");
        queue.offer("python");
        queue.offer("aaa");
        //poll 从队列中取出第一个元素，如果取出成功则删除该元素
        //输出
        //java
        //python
        //null
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        //存入队列
        queue.offer("java",1,);
        queue.offer("python");
        //peek取出第一个元素，但不会删除
        //输出
        //java
        //java
        //java
        System.out.println(queue.peek());
        System.out.println(queue.peek());
        System.out.println(queue.peek());

        //最多等待三秒，若队列还是为空，直接返回为null
        //输出
        //java
        //python
        //阻塞三秒
        //null
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
        System.out.println(queue.poll(3, TimeUnit.SECONDS));
    }

}
