package com.fandf.demo.combination.decorator;

import com.fandf.demo.combination.service.DataProcessingService;

/**
 * 具体实现类
 * 被装饰者
 *
 * @author fandongfeng
 * @date 2022-11-4 14:03
 */
public class DataComponent implements Component {

    /**
     * 不改变 dataProcessingService 结构， 动态的增加功能
     *
     * @param dataProcessingService 数据处理服务
     */
    @Override
    public void handler(DataProcessingService dataProcessingService) {
        //do something
        System.out.println("先吃饭在干活");
        //处理数据
        dataProcessingService.process();
        //do something
        System.out.println("干完活又饿了");
    }
}
