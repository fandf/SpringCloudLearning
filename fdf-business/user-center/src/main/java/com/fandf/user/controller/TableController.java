package com.fandf.user.controller;

import com.fandf.common.model.PageResult;
import com.fandf.user.model.TableVO;
import com.fandf.user.service.ISystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/18 10:16
 */
@Slf4j
@RestController
@Api(tags = "系统模块api")
public class TableController {

    @Resource
    ISystemService systemService;

    @ApiOperation(value = "查询")
    @GetMapping("sys/tables")
    public PageResult<TableVO> pageTables(@RequestParam Map<String, Object> params) {
        return systemService.pageTable(params);
    }

}
