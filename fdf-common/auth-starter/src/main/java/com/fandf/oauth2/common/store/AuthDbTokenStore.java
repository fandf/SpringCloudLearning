package com.fandf.oauth2.common.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器使用数据库存取令牌
 *
 * @author fandongfeng
 * @date 2022/6/29 17:18
 */
@Configuration
@ConditionalOnProperty(prefix = "fdf.oauth2.token.store", name = "type", havingValue = "db")
public class AuthDbTokenStore {
    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }
}
