package com.fandf.demo.combination.service.serviceImpl;

import com.fandf.demo.combination.service.DataProcessingService;

/**
 * 状态处理实现类
 *
 * @author fandongfeng
 * @date 2022-11-4 13:49
 */
public class StateProcessServiceImpl implements DataProcessingService {

    @Override
    public void process() {
        System.out.println("数据处理接口---》》》StateProcessServiceImpl 我的行为是状态处理");
    }
}
