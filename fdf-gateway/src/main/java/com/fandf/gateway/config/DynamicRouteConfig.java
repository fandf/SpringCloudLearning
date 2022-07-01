package com.fandf.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.fandf.gateway.route.NacosRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 *
 * @author fandongfeng
 * @date 2022/6/29 18:20
 */
@Configuration
@ConditionalOnProperty(prefix = "fdf.gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Nacos实现方式
     */
    @Configuration
    @ConditionalOnProperty(prefix = "fdf.gateway.dynamicRoute", name = "dataType", havingValue = "nacos", matchIfMissing = true)
    public class NacosDynRoute {
        @Autowired
        private NacosConfigManager nacosConfigManager;

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigManager);
        }
    }

}
