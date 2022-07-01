package com.fandf.oauth2.common.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * openId登录的认证信息对象
 *
 * @author fandongfeng
 * @date 2022/6/29 17:56
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = -7395777321075310544L;

    private final Object principal;

    public OpenIdAuthenticationToken(String openId) {
        super(null);
        this.principal = openId;
        setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
