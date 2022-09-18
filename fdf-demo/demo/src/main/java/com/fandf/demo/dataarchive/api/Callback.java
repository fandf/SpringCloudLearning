package com.fandf.demo.dataarchive.api;

/**
 * @author fandongfeng
 * @date 2022-9-16 19:18
 */
public abstract class Callback {
    /**
     * 回调方法
     * @param callback
     * @throws Exception
     */
    public abstract void callbackmsq(String callback) throws Exception;
}
