package com.fandf.test.controller;

import com.fandf.test.service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Resource
    MyUserService userService;


    @GetMapping("name")
    public String getName() {
        log.info("收到请求信息 {}", System.currentTimeMillis());
        return "zhangsan";
    }

    @GetMapping
    public String getUser(String name) {
        log.info("收到请求信息 name = {}", name);
        return userService.getByName(name);
    }


}
