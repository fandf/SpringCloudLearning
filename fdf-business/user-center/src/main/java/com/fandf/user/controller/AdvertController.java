package com.fandf.user.controller;

import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.log.annotation.AuditLog;
import com.fandf.user.model.Advert;
import com.fandf.user.service.IAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 09:50
 */
@RestController
@Api(tags = "广告管理")
public class AdvertController {

    @Resource
    IAdvertService advertService;

    @ApiOperation(value = "查询")
    @GetMapping("advert")
    public PageResult<Advert> pageAdvert(@RequestParam Map<String, Object> params) {
        return advertService.pageAdvert(params);
    }

    @PostMapping("advert/saveOrUpdate")
    @ApiOperation(value = "保存或者修改广告")
    @AuditLog(operation = "'保存或者修改广告:' + #advert.title")
    public Result saveOrUpdate(@RequestBody Advert advert) throws Exception {
        return advertService.saveAdvert(advert);
    }

}
