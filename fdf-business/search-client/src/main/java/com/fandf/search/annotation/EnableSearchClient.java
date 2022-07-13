package com.fandf.search.annotation;

import com.fandf.search.client.feign.fallback.SearchServiceFallbackFactory;
import com.fandf.search.client.service.impl.QueryServiceImpl;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制是否加载搜索中心客户端的Service
 *
 * @author fandongfeng
 * @date 2022/7/10 15:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableFeignClients(basePackages = "com.fandf")
@Import({SearchServiceFallbackFactory.class, QueryServiceImpl.class})
public @interface EnableSearchClient {
}
