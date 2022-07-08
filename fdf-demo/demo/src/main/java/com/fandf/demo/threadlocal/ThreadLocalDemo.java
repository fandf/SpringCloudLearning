package com.fandf.demo.threadlocal;

/**
 * 父子线程之间传递变量
 *
 * @author fandongfeng
 * @date 2022/7/8 15:40
 */
public class ThreadLocalDemo {

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        threadLocal.set(12345);
        //主线程中生成一个子线程
        Thread thread = new MyThread();
        thread.start();
        System.out.println("主线程threadLocal = " + threadLocal.get());
        threadLocal.remove();
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("子线程threadLocal = " + threadLocal.get());
        }
    }

}
