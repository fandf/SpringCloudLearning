package com.fandf.log.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fandf
 * @date 2022/6/26 10:59
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fdf.audit-log.datasource")
public class LogDbProperties extends HikariConfig {
}
