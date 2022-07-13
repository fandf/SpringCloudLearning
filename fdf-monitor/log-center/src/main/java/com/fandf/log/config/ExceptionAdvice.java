package com.fandf.log.config;

import com.fandf.common.exception.DefaultExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 统一异常处理
 *
 * @author fandongfeng
 * @date 2022/7/12 12:04
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
