package com.fandf.business.service;

import com.fandf.business.feign.OrderFeignClient;
import com.fandf.business.feign.StorageFeignClient;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:14
 */
@Slf4j
@Service
public class BusinessService {

    private static final String COMMODITY_CODE = "P1";
    private static final int ORDER_COUNT = 1;

    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private StorageFeignClient storageFeignClient;

    /**
     * 下订单
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public void placeOrder(String userId) {
        storageFeignClient.deduct(COMMODITY_CODE, ORDER_COUNT);
        orderFeignClient.create(userId, COMMODITY_CODE, ORDER_COUNT);
    }

}
