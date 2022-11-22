package com.fandf.mongo.core.exception;

public class BaseException extends RuntimeException {
    
    public BaseException(String message) {
        super(message);
    }
    
    public BaseException(Throwable e) {
        super(e);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
