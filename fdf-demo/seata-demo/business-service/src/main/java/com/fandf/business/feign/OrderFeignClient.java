package com.fandf.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:14
 */
@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @GetMapping("order/create")
    Boolean create(@RequestParam("userId") String userId,
                   @RequestParam("commodityCode") String commodityCode,
                   @RequestParam("count") Integer count);

}
