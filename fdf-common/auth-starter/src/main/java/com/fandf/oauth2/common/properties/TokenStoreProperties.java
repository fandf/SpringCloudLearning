package com.fandf.oauth2.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author fandongfeng
 * @date 2022/6/29 16:37
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fdf.oauth2.token.store")
@RefreshScope
public class TokenStoreProperties {
    /**
     * token存储类型(redis/db/authJwt/resJwt)
     */
    private String type = "redis";
}
