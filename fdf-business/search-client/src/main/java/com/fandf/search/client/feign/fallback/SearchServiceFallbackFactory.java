package com.fandf.search.client.feign.fallback;

import com.fandf.common.model.PageResult;
import com.fandf.search.client.feign.SearchService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:27
 */
@Slf4j
public class SearchServiceFallbackFactory implements FallbackFactory<SearchService> {
    @Override
    public SearchService create(Throwable throwable) {
        return (indexName, searchDto) -> {
            log.error("通过索引{}搜索异常:{}", indexName, throwable);
            return PageResult.<JsonNode>builder().build();
        };
    }
}
