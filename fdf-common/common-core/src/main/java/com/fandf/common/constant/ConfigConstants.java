package com.fandf.common.constant;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:04
 */
public interface ConfigConstants {

    /**
     * 是否开启自定义隔离规则
     */
    String CONFIG_RIBBON_ISOLATION_ENABLED = "fdf.ribbon.isolation.enabled";

    String CONFIG_LOADBALANCE_ISOLATION = "fdf.loadbalance.isolation";

    String CONFIG_LOADBALANCE_ISOLATION_ENABLE = CONFIG_LOADBALANCE_ISOLATION + ".enabled";

    String CONFIG_LOADBALANCE_ISOLATION_CHOOSER = CONFIG_LOADBALANCE_ISOLATION + ".chooser";

    String CONFIG_LOADBALANCE_VERSION = "fdf.loadbalance.version";


}
