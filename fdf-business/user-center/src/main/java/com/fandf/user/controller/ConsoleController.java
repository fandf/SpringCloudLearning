package com.fandf.user.controller;

import com.fandf.user.service.IConsoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 访问统计
 *
 * @author zlt
 * @date 2019/5/8
 */
@RestController
public class ConsoleController {

    @Resource
    IConsoleService consoleService;

    @ApiOperation(value = "访问统计")
    @GetMapping(value = "/console/requestStat")
    public Map<String, Object> requestStatAgg() {
        return consoleService.requestStat();
    }
}
