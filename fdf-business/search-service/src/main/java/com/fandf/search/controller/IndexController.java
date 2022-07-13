package com.fandf.search.controller;

import com.fandf.search.service.IIndexService;
import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.search.model.IndexDTO;
import com.fandf.search.properties.IndexProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 索引管理
 *
 * @author fandongfeng
 * @date 2022/7/10 14:04
 */
@Slf4j
@RestController
@Api(tags = "索引管理api")
@RequestMapping("/admin")
public class IndexController {

    @Resource
    IIndexService indexService;

    @Resource
    IndexProperties indexProperties;

    @PostMapping("/index")
    @ApiOperation("创建索引")
    public Result createIndex(@RequestBody IndexDTO indexDto) throws IOException {
        if (indexDto.getNumberOfShards() == null) {
            indexDto.setNumberOfShards(1);
        }
        if (indexDto.getNumberOfReplicas() == null) {
            indexDto.setNumberOfReplicas(0);
        }
        indexService.create(indexDto);
        return Result.succeed("操作成功");
    }

    /**
     * 索引列表
     */
    @ApiOperation("索引列表")
    @GetMapping("/indices")
    public PageResult<Map<String, String>> list(@RequestParam(required = false) String queryStr) throws IOException {
        return indexService.list(queryStr, indexProperties.getShow());
    }

    /**
     * 索引明细
     */
    @ApiOperation("索引明细")
    @GetMapping("/index")
    public Result<Map<String, Object>> showIndex(String indexName) throws IOException {
        Map<String, Object> result = indexService.show(indexName);
        return Result.succeed(result);
    }

    /**
     * 删除索引
     */
    @ApiOperation("删除索引")
    @DeleteMapping("/index")
    public Result deleteIndex(String indexName) throws IOException {
        indexService.delete(indexName);
        return Result.succeed("操作成功");
    }

}
