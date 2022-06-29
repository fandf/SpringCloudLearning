package com.fandf.order.service;

import com.fandf.order.feign.UserFeignClient;
import com.fandf.order.mapper.OrderMapper;
import com.fandf.order.model.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:25
 */
@Service
public class OrderService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private OrderMapper orderMapper;

    //@Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, Integer count) {
        //订单金额
        Integer orderMoney = count * 2;

        Order order = new Order()
                .setUserId(userId)
                .setCommodityCode(commodityCode)
                .setCount(count)
                .setMoney(orderMoney);
        orderMapper.insert(order);

        userFeignClient.reduce(userId, orderMoney);
    }


}
