package com.fandf.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:15
 */
@FeignClient(name = "storage-service")
public interface StorageFeignClient {

    @GetMapping("storage/deduct")
    Boolean deduct(@RequestParam("commodityCode") String commodityCode,
                   @RequestParam("count") Integer count);

}
