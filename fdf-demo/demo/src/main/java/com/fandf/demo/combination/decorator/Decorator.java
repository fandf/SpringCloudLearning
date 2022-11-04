package com.fandf.demo.combination.decorator;

import com.fandf.demo.combination.service.DataProcessingService;

/**
 * 装饰角色 持有一个Component对象的实例
 * 对具体装饰者可以做统一基础处理
 *
 * @author fandongfeng
 * @date 2022-11-4 14:03
 */
public class Decorator implements Component {

    private final Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void handler(DataProcessingService dataProcessingService) {
        component.handler(dataProcessingService);
    }
}
