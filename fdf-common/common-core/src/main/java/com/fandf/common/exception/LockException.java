package com.fandf.common.exception;

/**
 * @author fandongfeng
 * @date 2022/6/27 11:56
 * 分布式锁异常
 */

public class LockException extends RuntimeException{
    public LockException(String message) {
        super(message);
    }
}
