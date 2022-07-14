package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fandongfeng
 * @date 2022/7/14 09:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("advert")
@ApiModel(value = "Advert对象", description = "广告表")
@NoArgsConstructor
public class Advert extends Model<Advert> {
    private static final long serialVersionUID = -691242718235643046L;

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String img;

    @ApiModelProperty(value = "状态 0初始状态1上线状态")
    private Integer onlineStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date autoOnlineTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date autoOutlineTime;

}
