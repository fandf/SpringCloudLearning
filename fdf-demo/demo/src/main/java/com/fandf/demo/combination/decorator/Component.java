package com.fandf.demo.combination.decorator;

import com.fandf.demo.combination.service.DataProcessingService;

/**
 * 抽象构件(Component)角色：定义一个抽象接口以规范准备接收附加责任的对象.
 * <p>
 * 指在不改变现有对象结构的情况下，
 * 动态地给该对象增加一些职责（即增加其额外功能）的模式，
 * 它属于对象结构型模式。
 *
 * @author fandongfeng
 * @date 2022-11-4 14:01
 */
public interface Component {

    /**
     * 定义方法规范
     *
     * @param dataProcessingService 数据处理服务
     */
    public void handler(DataProcessingService dataProcessingService);

}
