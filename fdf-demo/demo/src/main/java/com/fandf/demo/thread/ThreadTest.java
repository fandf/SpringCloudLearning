package com.fandf.demo.thread;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fandongfeng
 */
public class ThreadTest extends Thread {

    private volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {

        }
        System.out.println("线程中止");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest t1 = new ThreadTest();
        t1.start();
        t1.flag = false;
    }
}
