package com.fandf.oauth2.common.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置
 *
 * @author fandongfeng
 * @date 2022/6/29 16:43
 */
@Setter
@Getter
public class ValidateCodeProperties {

    /**
     * 设置认证通时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}
