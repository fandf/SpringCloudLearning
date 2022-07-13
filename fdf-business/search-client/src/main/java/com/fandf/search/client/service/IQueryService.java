package com.fandf.search.client.service;

import com.fandf.common.model.PageResult;
import com.fandf.search.model.LogicDelDTO;
import com.fandf.search.model.SearchDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:21
 */
public interface IQueryService {

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDTO searchDto);

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @param logicDelDto 逻辑删除Dto
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDTO searchDto, LogicDelDTO logicDelDto);

    /**
     * 访问统计聚合查询
     * @param indexName 索引名
     * @param routing es的路由
     */
    Map<String, Object> requestStatAgg(String indexName, String routing);

}
