package com.fandf.search.controller;

import com.fandf.search.service.IAggregationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:52
 */
@Slf4j
@RestController
@Api(tags = "聚合统计模块api")
@RequestMapping("/agg")
public class AggregationController {

    private final IAggregationService aggregationService;

    public AggregationController(IAggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    /**
     * 访问统计聚合查询
     * @param indexName 索引名
     * @param routing es的路由
     */
    @GetMapping("/requestStat/{indexName}/{routing}")
    @ApiOperation("访问统计聚合查询")
    public Map<String, Object> requestStatAgg(@PathVariable String indexName, @PathVariable String routing) throws IOException {
        return aggregationService.requestStatAgg(indexName, routing);
    }

}
