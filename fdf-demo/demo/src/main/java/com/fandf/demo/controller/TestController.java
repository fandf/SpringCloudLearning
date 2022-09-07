package com.fandf.demo.controller;

import com.fandf.demo.design.cellve.PayStrategyServiceFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fandongfeng
 * @date 2022-9-7 10:58
 */
@RestController
public class TestController {


    @GetMapping("pay")
    public boolean pay(String type) {
        return PayStrategyServiceFactory.getInvokeStrategyMap(type).processBiz();
    }

}
