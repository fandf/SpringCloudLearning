package com.fandf.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author fandongfeng
 * @date 2022/7/11 15:25
 */
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
