package com.fandf.user.controller;

import com.fandf.log.annotation.AuditLog;
import com.fandf.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/6/28 08:28
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 账号扣钱
     */
    @GetMapping("reduce")
    @AuditLog(operation = "账号扣钱")
    @ApiOperation("账号扣钱")
    public Boolean reduce(@RequestParam("userId") String userId
            ,@RequestParam("money") Integer money) {
        userService.reduce(userId, money);
        return true;
    }
}
