package com.fandf.demo.thread;

/**
 * @author fandongfeng
 */
public class ThreadYield extends Thread{

    public ThreadYield(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            if(i == 0 && Thread.currentThread().getName().equals("t1")) {
                System.out.println(Thread.currentThread().getName() + "让出cpu执行权");
                Thread.yield();
            }
            System.out.println(Thread.currentThread().getName() + "拿到cpu执行权" + i);
        }
    }

    public static void main(String[] args) {
        new ThreadYield("t1").start();
        new ThreadYield("t2").start();
    }
}
