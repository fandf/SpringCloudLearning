package com.fandf.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fandf.user.mapper.UserMapper;
import com.fandf.user.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/6/28 08:27
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 扣减账号金额
     * @param userId
     * @param money
     */
    //@Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, int money) {
        if ("2".equals(userId)) {
            throw new RuntimeException("this is a mock Exception");
        }
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Account().setUserId(userId));
        Account account = userMapper.selectOne(wrapper);
        account.setMoney(account.getMoney() - money);
        userMapper.updateById(account);
    }

}
