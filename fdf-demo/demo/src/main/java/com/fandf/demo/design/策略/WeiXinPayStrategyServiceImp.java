package com.fandf.demo.design.策略;

import org.springframework.stereotype.Service;

/**
 * 微信支付策略实现类
 *
 * @author fandongfeng
 * @date 2022-9-7 10:47
 */
@Service
public class WeiXinPayStrategyServiceImp extends AbstractHandler {
    @Override
    boolean effective() {
        System.out.println("微信支付有效");
        return true;
    }

    @Override
    boolean pay() {
        System.out.println("微信支付成功");
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        PayStrategyServiceFactory.register("weixin", this);
    }
}
