package com.fandf.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fandongfeng
 * @date 2022/6/29 16:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends SuperEntity{

    private static final long serialVersionUID = 1873723069510010763L;

    private String code;
    private String name;
    @TableField(exist = false)
    private Long userId;

}
