package com.fandf.common.exception;

/**
 * @author fandongfeng
 * @date 2022/6/29 11:02
 */
public class IdempotencyException extends RuntimeException {
    private static final long serialVersionUID = -9126969614004474851L;

    public IdempotencyException(String message) {
        super(message);
    }
}
