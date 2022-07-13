package com.fandf.search.service;

import com.fandf.common.model.PageResult;
import com.fandf.search.model.IndexDTO;

import java.io.IOException;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/10 14:05
 */
public interface IIndexService {

    /**
     * 创建索引
     */
    boolean create(IndexDTO indexDto) throws IOException;

    /**
     * 删除索引
     * @param indexName 索引名
     */
    boolean delete(String indexName) throws IOException;

    /**
     * 索引列表
     * @param queryStr 搜索字符串
     * @param indices 默认显示的索引名
     */
    PageResult<Map<String, String>> list(String queryStr, String indices) throws IOException;

    /**
     * 显示索引明细
     * @param indexName 索引名
     */
    Map<String, Object> show(String indexName) throws IOException;

}
