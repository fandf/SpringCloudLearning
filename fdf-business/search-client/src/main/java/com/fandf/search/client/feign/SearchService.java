package com.fandf.search.client.feign;

import com.fandf.common.constant.ServiceNameConstants;
import com.fandf.common.model.PageResult;
import com.fandf.search.client.feign.fallback.SearchServiceFallbackFactory;
import com.fandf.search.model.SearchDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:25
 */
@FeignClient(name = ServiceNameConstants.SEARCH_SERVICE, fallbackFactory = SearchServiceFallbackFactory.class, decode404 = true)
public interface SearchService {

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    @PostMapping(value = "/search/{indexName}")
    PageResult<JsonNode> strQuery(@PathVariable("indexName") String indexName, @RequestBody SearchDTO searchDto);

}
