package com.fandf.test.deadlock;

/**
 * 模拟死锁
 *
 * @author fandongfeng
 */
public class DeadLockTest {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();


    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock1) {
                try {
                    System.out.println("thread1 begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                synchronized (lock2) {
                    System.out.println("thread1 end");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lock2) {
                try {
                    System.out.println("thread2 begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                synchronized (lock1) {
                    System.out.println("thread2 end");
                }
            }
        }).start();
        System.out.println("main thread end");
    }

}
