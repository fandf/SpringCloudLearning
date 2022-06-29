package com.fandf.common.lb.config;

import com.fandf.common.constant.ConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Import;

/**
 * 版本控制器
 *
 * @author fandongfeng
 * @date 2022/6/28 13:03
 */
@LoadBalancerClients(defaultConfiguration = VersionLoadBalancerConfig.class)
@ConditionalOnProperty(prefix = ConfigConstants.CONFIG_LOADBALANCE_ISOLATION, name = "enabled", havingValue = "true", matchIfMissing = false)
@Import({VersionRegisterBeanPostProcessor.class})
public class VersionIsolationAutoConfig {
}
