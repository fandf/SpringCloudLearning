package com.fandf.common.lb.chooser;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * service选择器类
 * @author fandongfeng
 * @date 2022/6/28 12:54
 */
public interface IRuleChooser {

    ServiceInstance choose(List<ServiceInstance> instances);
}
