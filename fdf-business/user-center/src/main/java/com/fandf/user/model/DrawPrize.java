package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author fandongfeng
 * @date 2022/6/15 下午1:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("draw_prize")
@ApiModel(value="draw_prize对象", description="抽奖奖品表")
public class DrawPrize extends Model<DrawPrize> {
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long activityId;
    private String name;
    private String url;
    @TableField("`value`")
    private String value;
    private Double probability;
    @ApiModelProperty("类型1BT 2积分3再抽一次4谢谢参与")
    private Integer type;
    private Integer status;
    private Integer position;
    private Integer dayMaxTimes;
    private Integer monthMaxTimes;
    @TableField("`show`")
    private Integer show;
    private Date created;
    private Date updated;

}
