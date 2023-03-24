package com.fandf.test.redis;

import com.fandf.common.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: fandongfeng
 * @date: 2023-3-2416:45
 */
@SpringBootTest
class SignServiceTest {

    @Resource
    SignService signService;

    @Test
    void sign() {
        signService.sign();
    }

    @Test
    void signCount() {
        Integer result = signService.signCount();
        System.out.println(result);
    }
}