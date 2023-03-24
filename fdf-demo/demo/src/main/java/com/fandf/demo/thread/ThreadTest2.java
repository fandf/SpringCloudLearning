package com.fandf.demo.thread;

/**
 * @author fandongfeng
 */
public class ThreadTest2 {

    private Object obj;

    public Thread print() {
        return new Thread(() -> {
            synchronized (obj) {
                System.out.println(1);
            }
            try {
                obj.wait();
            } catch (InterruptedException e) {

            }
            System.out.println(2);
        });
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(()-> {
            for (int i = 0; i < 30; i++) {
                if(i == 20) {
                    System.out.println(Thread.currentThread().getName() + "让出cpu执行权");
                    Thread.yield();
                }
                System.out.println(Thread.currentThread().getName() + "拿到cpu执行权" + i);
            }
        }, "t1");

        Thread t2 = new Thread(()-> {
            for (int i = 0; i < 30; i++) {
                if(i == 20) {
                    System.out.println(Thread.currentThread().getName() + "让出cpu执行权");
                    Thread.yield();
                }
                System.out.println(Thread.currentThread().getName() + "拿到cpu执行权" + i);
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
