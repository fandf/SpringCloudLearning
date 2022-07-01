package com.fandf.oauth2.common.token;

import lombok.Getter;

import java.io.Serializable;

/**
 * 表单登录的认证信息对象
 *
 * @author fandongfeng
 * @date 2022/6/29 16:53
 */
@Getter
public class CustomWebAuthenticationDetails implements Serializable {
    private static final long serialVersionUID = 2212045554150518088L;

    private final String accountType;
    private final String remoteAddress;
    private final String sessionId;

    public CustomWebAuthenticationDetails(String remoteAddress, String sessionId, String accountType) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; accountType: ").append(this.getAccountType());
        return sb.toString();
    }

}
