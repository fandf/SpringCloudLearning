package com.fandf.user.controller;

import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.log.annotation.AuditLog;
import com.fandf.user.model.UserInfo;
import com.fandf.user.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/19 12:09
 */
@RestController
@Api(tags = "用户管理")
public class AppUserController {

    @Resource
    IUserInfoService userInfoService;

    @ApiOperation(value = "查询")
    @GetMapping("app_user")
    public PageResult<UserInfo> pageUserInfo(@RequestParam Map<String, Object> params) {
        return userInfoService.pageUserInfo(params);
    }

    /**
     * 修改用户状态
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "修改用户认证状态")
    @GetMapping("app_user/update_claim")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true),
            @ApiImplicitParam(name = "claim", value = "是否认证", required = true)
    })
    @AuditLog(operation = "'修改用户认证状态:' + #params.get('token')")
    public Result updateEnabled(@RequestParam Map<String, Object> params) {
        String token = MapUtils.getString(params, "token");
        int claim = MapUtils.getIntValue(params, "claim");
        userInfoService.updateById(new UserInfo().setToken(token).setClaim(claim));
        return Result.succeed("修改成功");
    }

}
