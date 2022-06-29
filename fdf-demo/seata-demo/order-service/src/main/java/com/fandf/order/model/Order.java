package com.fandf.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fandongfeng
 * @date 2022/6/28 13:23
 */
@Data
@Accessors(chain = true)
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;

}
