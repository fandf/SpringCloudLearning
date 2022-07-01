package com.fandf.gateway.filter;

import com.fandf.common.constant.CommonConstant;
import com.fandf.common.constant.ConfigConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author fandongfeng
 * @date 2022/6/29 19:16
 */
@Component
public class VersionLbIsolationFilter implements GlobalFilter, Ordered {

    @Value("${"+ ConfigConstants.CONFIG_LOADBALANCE_ISOLATION_ENABLE+":}")
    private Boolean enableVersionControl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(Boolean.TRUE.equals(enableVersionControl)
                && exchange.getRequest().getQueryParams().containsKey(CommonConstant.L_T_VERSION)){
            String version = exchange.getRequest().getQueryParams().get(CommonConstant.L_T_VERSION).get(0);
            ServerHttpRequest rebuildRequest = exchange.getRequest().mutate().headers(header -> {
                header.add(CommonConstant.L_T_VERSION, version);
            }).build();
            ServerWebExchange rebuildServerWebExchange = exchange.mutate().request(rebuildRequest).build();
            return chain.filter(rebuildServerWebExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
