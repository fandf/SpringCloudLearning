package com.fandf.log.annotation;

import java.lang.annotation.*;

/**
 * 审计日志 保存操作到数据库
 * @author fandongfeng
 * @date 2022-06-25
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 操作信息
     */
    String operation();

}
