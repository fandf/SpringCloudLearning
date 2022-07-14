package com.fandf.user.controller;

import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.log.annotation.AuditLog;
import com.fandf.user.model.DrawPrize;
import com.fandf.user.model.DrawPrizeDTO;
import com.fandf.user.service.IDrawPrizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/13 14:23
 */
@RestController
@Api(tags = "抽奖模块api")
@Slf4j
@RequestMapping("/draw")
public class DrawPrizeController {

    @Resource
    IDrawPrizeService drawPrizeService;

    @ApiOperation(value = "查询奖品")
    @GetMapping("/prize")
    public PageResult<DrawPrize> paegPrize(@RequestParam Map<String, Object> params) {
        return drawPrizeService.listPrize(params, true);
    }

    @PostMapping("/prize/saveOrUpdate")
    @ApiOperation(value = "保存或者修改奖品")
    @AuditLog(operation = "保存或者修改奖品")
    public Result saveOrUpdate(@RequestBody DrawPrizeDTO drawPrizeDTO) throws Exception {
        return drawPrizeService.saveDrawPrize(drawPrizeDTO);
    }


}
