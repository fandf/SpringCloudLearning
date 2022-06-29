package com.fandf.sharding.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fandf.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fandongfeng
 * @date 2022/6/29 10:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends SuperEntity {

    private String companyId;
    private String name;

}
