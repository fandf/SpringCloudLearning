package com.fandf.demo.dataarchive.api;

import java.util.concurrent.Callable;

/**
 * @author fandongfeng
 * @date 2022-9-16 19:18
 */
public class Task implements Callable<Object> {

    private int num = 0;
    private int line = 0;

    private Callback callback;

    public Task(int line, int num, Callback callback) {
        this.line = line;
        this.num = num;
        this.callback = callback;
    }

    @Override
    public String call() throws Exception {
        // TODO 模拟耗时操作
        Thread.sleep(num);
        if (num > 2500) {
            callback.callbackmsq("第" + line + "行代码有问题，停止应用了");
        }
        System.out.println("处理了一条数据" + Thread.currentThread().getName());
        return "第" + line + "行结果出来了";
    }
}
