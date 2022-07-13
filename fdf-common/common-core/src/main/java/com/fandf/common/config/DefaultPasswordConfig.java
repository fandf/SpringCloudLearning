package com.fandf.common.config;

import com.fandf.common.utils.PwdEncoderUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密配置类
 *
 * @author fandongfeng
 * @date 2022/7/11 14:53
 */
public class DefaultPasswordConfig {
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PwdEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
    }
}
