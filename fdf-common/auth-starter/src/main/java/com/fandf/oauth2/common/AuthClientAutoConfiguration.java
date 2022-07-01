package com.fandf.oauth2.common;

import com.fandf.oauth2.common.properties.SecurityProperties;
import com.fandf.oauth2.common.properties.TokenStoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fandongfeng
 * @date 2022/6/29 16:36
 */
@EnableConfigurationProperties({SecurityProperties.class, TokenStoreProperties.class})
@ComponentScan
public class AuthClientAutoConfiguration {
}
