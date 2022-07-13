package com.fandf.search.service.impl;

import com.fandf.common.model.PageResult;
import com.fandf.es.utils.SearchBuilder;
import com.fandf.search.model.SearchDTO;
import com.fandf.search.service.ISearchService;
import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:41
 */
@Service
public class SearchServiceImpl implements ISearchService {

    private final RestHighLevelClient client;

    public SearchServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDTO searchDto) throws IOException {
        return SearchBuilder.builder(client, indexName)
                .setStringQuery(searchDto.getQueryStr())
                .addSort(searchDto.getSortCol(), searchDto.getSortOrder())
                .setIsHighlight(searchDto.getIsHighlighter())
                .getPage(searchDto.getPage(), searchDto.getLimit());
    }

}
