package com.fandf.test.ratelimter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fandongfeng
 * @date 2023/4/9 20:25
 */
@RestController
@RequestMapping("limiter")
@Slf4j
public class UserController {

    @GetMapping
    @Limiter(qps = 1, msg = "您已被限流！")
    public String getUserName() {
        String userName = "zhangsan";
        log.info("userName = {}", userName);
        return userName;
    }

}
