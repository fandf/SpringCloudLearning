package com.fandf.search.service;

import com.fandf.common.model.PageResult;
import com.fandf.search.model.SearchDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author fandongfeng
 * @date 2022/7/10 14:12
 */
public interface ISearchService {

    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDTO searchDto) throws IOException;
}
