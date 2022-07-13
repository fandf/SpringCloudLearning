package com.fandf.search.controller;

import com.fandf.common.model.PageResult;
import com.fandf.search.model.SearchDTO;
import com.fandf.search.service.ISearchService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:51
 */
@Slf4j
@RestController
@Api(tags = "搜索模块api")
@RequestMapping("/search")
public class SearchController {

    private final ISearchService searchService;

    public SearchController(ISearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    @PostMapping("/{indexName}")
    @ApiOperation("查询文档列表")
    public PageResult<JsonNode> strQuery(@ApiParam("索引名称") @PathVariable String indexName, @RequestBody(required = false) SearchDTO searchDto) throws IOException {
        if (searchDto == null) {
            searchDto = new SearchDTO();
        }
        return searchService.strQuery(indexName, searchDto);
    }
}
