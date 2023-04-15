package com.fandf.test.ratelimter;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@SpringBootTest
public class UserServiceTest {

    @Resource
    UserService userService;

    public void getUserName() throws InterruptedException {
        ExecutorService executorService = ThreadUtil.newExecutor(10);
        executorService.execute(() -> System.out.println(userService.getUserName()));
        Thread.sleep(10000);

    }
}