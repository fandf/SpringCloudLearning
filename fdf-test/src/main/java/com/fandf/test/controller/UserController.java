package com.fandf.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fandongfeng
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @GetMapping("name")
    public String getName() {
        return "zhangsan";
    }


}
