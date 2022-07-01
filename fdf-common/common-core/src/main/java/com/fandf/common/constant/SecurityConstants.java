package com.fandf.common.constant;

/**
 * Security 权限常量
 * @author fandongfeng
 * @date 2022/6/28 12:53
 */
public interface SecurityConstants {

    /**
     * 租户信息头(应用)
     */
    String TENANT_HEADER = "x-tenant-header";
    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-userid-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";
    /**
     * 账号类型参数名
     */
    String ACCOUNT_TYPE_PARAM_NAME = "account_type";
    /**
     * rsa公钥
     */
    String RSA_PUBLIC_KEY = "pubkey.txt";
    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";
    /**
     * 默认token过期时间(1小时)
     */
    Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
    /**
     * redis中授权token对应的key
     */
    String REDIS_TOKEN_AUTH = "auth:";
    /**
     * redis中应用对应的token集合的key
     */
    String REDIS_CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    /**
     * redis中用户名对应的token集合的key
     */
    String REDIS_UNAME_TO_ACCESS = "uname_to_access:";
    /**
     * 账号类型信息头
     */
    String ACCOUNT_TYPE_HEADER = "x-account-type-header";
}
