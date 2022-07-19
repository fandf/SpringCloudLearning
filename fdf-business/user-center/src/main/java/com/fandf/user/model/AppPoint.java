package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fandongfeng
 * @date 2022/7/18 15:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_point")
@ApiModel(value = "app_point对象", description = "埋点表")
@NoArgsConstructor
public class AppPoint extends Model<AppPoint> {

    private static final long serialVersionUID = -2427464113729639328L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String applicationName;
    private String token;
    private String operation;
    private Date created;

}
