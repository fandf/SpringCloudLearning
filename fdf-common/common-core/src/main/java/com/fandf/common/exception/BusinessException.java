package com.fandf.common.exception;

/**
 * @author fandongfeng
 * @date 2022/7/10 14:24
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 830832783100056109L;

    public BusinessException(String message) {
        super(message);
    }

}
