package com.fandf.user.controller;

import com.fandf.common.model.PageResult;
import com.fandf.user.model.SysLogger;
import com.fandf.user.service.ISysLoggerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 审计日志
 *
 * @author fandongfeng
 * @date 2022/7/13 14:23
 */
@RestController
public class AuditLogController {


    @Resource
    ISysLoggerService sysLoggerService;

    @ApiOperation(value = "审计日志列表")
    @GetMapping(value = "/auditLog")
    public PageResult<SysLogger> getPage(@RequestParam Map<String, Object> params) {
        return sysLoggerService.pageSysLogger(params);
    }
}
