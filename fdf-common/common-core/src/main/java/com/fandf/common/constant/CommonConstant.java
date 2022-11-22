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

    String LOCK_KEY_PREFIX = "LOCK_KEY";

    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";
    String TIME_ZONE_GMT8 = "GMT+8";

    /**
     * 超级管理员用户名
     */
    String ADMIN_USER_NAME = "admin";
    /**
     * 目录
     */
    Integer CATALOG = -1;

    /**
     * 菜单
     */
    Integer MENU = 1;

    /**
     * 权限
     */
    Integer PERMISSION = 2;

    String DEF_USER_PASSWORD = "123456";
    /**
     * 文件分隔符
     */
    String PATH_SPLIT = "/";
}
