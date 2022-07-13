package com.fandf.search.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *  索引配置
 *
 * @author fandongfeng
 * @date 2022/7/10 14:03
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fdf.indices")
@RefreshScope
public class IndexProperties {

    /**
     * 配置过滤的索引名：默认只显示这些索引
     */
    private String show;

}
