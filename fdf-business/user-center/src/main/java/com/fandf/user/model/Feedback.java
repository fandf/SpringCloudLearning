package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author fandongfeng
 * @date 2022/7/14 15:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("feedback")
@ApiModel(value="feedback对象", description="意见反馈表")
public class Feedback extends Model<Feedback> {
    private static final long serialVersionUID = -987647388675383932L;

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String token;
    @ApiModelProperty("反馈类型")
    private Integer type;
    @ApiModelProperty(value = "反馈内容")
    @TableField("`content`")
    private String content;
    private String imgs;
    private String did;
    private String osv;
    private String ipAddress;
    @ApiModelProperty(value = "设备 a安卓  i ios")
    private String device;
    private String appVersion;
    @TableField("`status`")
    private Integer status;
    private String reply;
    private Integer deleted;
    private Date addTime;

}
