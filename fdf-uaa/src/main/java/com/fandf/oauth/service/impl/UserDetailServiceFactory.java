package com.fandf.oauth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fandf.common.constant.SecurityConstants;
import com.fandf.oauth.service.UaaUserDetailsService;
import com.fandf.oauth2.common.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户service工厂
 *
 * @author fandongfeng
 * @date 2022/7/11 14:43
 */
@Slf4j
@Service
public class UserDetailServiceFactory {

    private static final String ERROR_MSG = "找不到账号类型为 {} 的实现类";

    @Resource
    private List<UaaUserDetailsService> userDetailsServices;

    public UaaUserDetailsService getService(Authentication authentication) {
        String accountType = AuthUtils.getAccountType(authentication);
        return this.getService(accountType);
    }

    public UaaUserDetailsService getService(String accountType) {
        if (StrUtil.isEmpty(accountType)) {
            accountType = SecurityConstants.DEF_ACCOUNT_TYPE;
        }
        log.info("UserDetailServiceFactory.getService:{}", accountType);
        if (CollUtil.isNotEmpty(userDetailsServices)) {
            for (UaaUserDetailsService userService : userDetailsServices) {
                if (userService.supports(accountType)) {
                    return userService;
                }
            }
        }
        throw new InternalAuthenticationServiceException(StrUtil.format(ERROR_MSG, accountType));
    }

}
