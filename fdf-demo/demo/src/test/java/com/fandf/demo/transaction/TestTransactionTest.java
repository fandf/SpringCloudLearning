package com.fandf.demo.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestTransactionTest {
    @Resource
    TestTransaction testTransaction;

    @Test
    void syncAccount() {
        testTransaction.syncAccount(ThirdAccount.of("2", "去掉@Transactional注解测试失败数据"));
    }
}