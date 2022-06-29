package com.fandf.business.controller;

import com.fandf.business.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:13
 */
@RestController
@RequestMapping("business")
public class BusinessController {
    @Resource
    private BusinessService businessService;

    /**
     * 下单场景测试-正常
     */
    @GetMapping(path = "/placeOrder")
    public Boolean placeOrder() {
        businessService.placeOrder("1");
        return true;
    }

    /**
     * 下单场景测试-回滚
     */
    @GetMapping(path = "/placeOrderFallBack")
    public Boolean placeOrderFallBack() {
        businessService.placeOrder("2");
        return true;
    }
}
