package com.fandf.demo.controller;

import com.fandf.demo.design.策略.PayStrategyServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fandongfeng
 * @date 2022-9-7 10:58
 */
@RestController
@Slf4j
public class TestController {


    @GetMapping("pay")
    public boolean pay(String type) {
        log.info("info日志");
        log.debug("debug日志");
//        return PayStrategyServiceFactory.getInvokeStrategyMap(type).processBiz();
        return false;
    }

}
