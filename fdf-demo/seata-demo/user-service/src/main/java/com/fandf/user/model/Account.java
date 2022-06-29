package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fandongfeng
 * @date 2022/6/28 08:27
 */
@Data
@Accessors(chain = true)
@TableName("account")
public class Account {

    @TableId
    private Long id;
    private String userId;
    private Integer money;

}
