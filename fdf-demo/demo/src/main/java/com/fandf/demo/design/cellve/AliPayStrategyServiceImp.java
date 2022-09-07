package com.fandf.demo.design.cellve;

import org.springframework.stereotype.Service;

/**
 * 阿里支付策略实现类
 *
 * @author fandongfeng
 * @date 2022-9-7 10:47
 */
@Service
public class AliPayStrategyServiceImp extends AbstractHandler {
    @Override
    boolean effective() {
        System.out.println("阿里支付不可用");
        return true;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        PayStrategyServiceFactory.register("ali", this);
    }
}
