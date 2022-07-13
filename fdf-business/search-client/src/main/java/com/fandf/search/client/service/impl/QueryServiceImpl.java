package com.fandf.search.client.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fandf.common.model.PageResult;
import com.fandf.search.client.feign.AggregationService;
import com.fandf.search.client.feign.SearchService;
import com.fandf.search.client.service.IQueryService;
import com.fandf.search.model.LogicDelDTO;
import com.fandf.search.model.SearchDTO;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 搜索客户端Service
 *
 * @author fandongfeng
 * @date 2022/7/10 15:25
 */
public class QueryServiceImpl implements IQueryService {
    @Resource
    private SearchService searchService;

    @Resource
    private AggregationService aggregationService;

    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDTO searchDto) {
        return strQuery(indexName, searchDto, null);
    }

    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDTO searchDto, LogicDelDTO logicDelDto) {
        setLogicDelQueryStr(searchDto, logicDelDto);
        return searchService.strQuery(indexName, searchDto);
    }

    /**
     * 拼装逻辑删除的条件
     *
     * @param searchDto   搜索dto
     * @param logicDelDto 逻辑删除dto
     */
    private void setLogicDelQueryStr(SearchDTO searchDto, LogicDelDTO logicDelDto) {
        if (logicDelDto != null
                && StrUtil.isNotEmpty(logicDelDto.getLogicDelField())
                && StrUtil.isNotEmpty(logicDelDto.getLogicNotDelValue())) {
            String result;
            //搜索条件
            String queryStr = searchDto.getQueryStr();
            //拼凑逻辑删除的条件
            String logicStr = logicDelDto.getLogicDelField() + ":" + logicDelDto.getLogicNotDelValue();
            if (StrUtil.isNotEmpty(queryStr)) {
                result = "(" + queryStr + ") AND " + logicStr;
            } else {
                result = logicStr;
            }
            searchDto.setQueryStr(result);
        }
    }

    /**
     * 访问统计聚合查询
     *
     * @param indexName 索引名
     * @param routing   es的路由
     */
    @Override
    public Map<String, Object> requestStatAgg(String indexName, String routing) {
        return aggregationService.requestStatAgg(indexName, routing);
    }
}
