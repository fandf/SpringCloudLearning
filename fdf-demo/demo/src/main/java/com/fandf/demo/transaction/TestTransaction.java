package com.fandf.demo.transaction;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2023/6/17 14:42
 */
@Service
public class TestTransaction {

    @Resource
    AccountLogMapper accountLogMapper;

    public void syncAccount(ThirdAccount account) {
        AccountLog accountLog = account.toAccountLog();
        try {
            //模拟id为2 则抛出异常
            if ("2".equals(account.getId())) {
                throw new Exception("模拟抛出异常");
            }
        } catch (Exception e) {
            accountLog.setSuccess(false);
            accountLog.setErrorMsg(e.getMessage());
            throw new IllegalArgumentException("同步第三方账号错误：" + e.getMessage());
        } finally {
            accountLogMapper.insert(accountLog);
        }
    }


}
