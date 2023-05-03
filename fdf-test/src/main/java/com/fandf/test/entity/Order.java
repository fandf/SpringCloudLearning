package com.fandf.test.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author fandongfeng
 * @date 2023/5/3 19:17
 */
@Data
public class Order {


    /**
     * 订单优惠前价格
     */
    private BigDecimal originalPrice;
    /**
     * 订单优惠后价格
     */
    private BigDecimal realPrice;

}
