package com.fandf.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 审计日志配置
 * @author fandf
 * @date 2022/6/26 10:39
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fdf.audit-log")
@RefreshScope
public class AuditLogProperties {

    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;
    /**
     * 日志记录类型(logger/redis/db/es)
     */
    private String logType;

}
