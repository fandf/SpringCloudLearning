package com.fandf.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:25
 */
@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/user/reduce")
    Boolean reduce(@RequestParam("userId") String userId,
                   @RequestParam("money") Integer money);
}
