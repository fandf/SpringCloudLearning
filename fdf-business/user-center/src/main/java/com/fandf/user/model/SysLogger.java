package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author fandongfeng
 * @date 2022/7/13 23:41
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_logger")
@Data
public class SysLogger extends Model<SysLogger> {

    private static final long serialVersionUID = 4700580419840864286L;

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("application_name")
    private String appName;
    private String className;
    private String methodName;
    private Integer userId;
    private String userName;
    private String clientId;
    private String operation;
    private String timestamp;


}
