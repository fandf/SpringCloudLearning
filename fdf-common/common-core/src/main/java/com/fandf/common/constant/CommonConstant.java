package com.fandf.common.constant;

/**
 * 全局公共常量
 * @author fandongfeng
 * @date 2022/6/26 21:56
 */

public interface CommonConstant {

    /**
     * 项目版本号(banner使用)
     */
    String PROJECT_VERSION = "1.0.0";
    /**
     * The access token issued by the authorization server. This value is REQUIRED.
     */
    String ACCESS_TOKEN = "access_token";
    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    /**
     * token start
     */
    String BEARER_TYPE = "Bearer";
    /**
     * 负载均衡策略-版本号 信息头
     */
    String L_T_VERSION = "l-t-version";
    /**
     * 注册中心元数据 版本号
     */
    String METADATA_VERSION = "version";
    /**
     * 租户id参数
     */
    String TENANT_ID_PARAM = "tenantId";

}
