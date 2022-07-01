package com.fandf.oauth2.common.constants;

/**
 * 权限常量
 *
 * @author fandongfeng
 * @date 2022/6/29 17:04
 */
public enum AuthoritiesEnum {

    /**
     * 管理员
     */
    ADMIN("ROLE_ADMIN"),
    /**
     * 普通用户
     */
    USER("ROLE_USER"),
    /**
     * 匿名用户
     */
    ANONYMOUS("ROLE_ANONYMOUS");

    private String role;

    AuthoritiesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
