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
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author admin
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_info")
@ApiModel(value = "AppUser对象", description = "app用户表")
@NoArgsConstructor
public class UserInfo extends Model<UserInfo> {

    @ApiModelProperty(value = "用户token")
    @TableId(type = IdType.INPUT)
    private String token;
    @ApiModelProperty(value = "昵称")
    private String userName;
    private String liangHao;
    @ApiModelProperty(value = "邮箱地址")
    private String email;
    @ApiModelProperty(value = "头像url")
    private String userHeadImg;
    @ApiModelProperty(value = "用户收益")
    private BigDecimal money;
    @ApiModelProperty(value = "是否认证可以发帖 0没有认证 1已认证")
    @TableField("is_check")
    private Integer checked;
    @ApiModelProperty(value = "个性签名")
    private String descInfo;
    @ApiModelProperty(value = "是否发行名人币 0否1是")
    private Integer certification;
    @ApiModelProperty(value = "发行名人币  bt价格")
    private BigDecimal price;
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;
    @ApiModelProperty(value = "剩余免费修改次数")
    private Integer freeUpdateNum;
    @ApiModelProperty(value = "标记 0:正常用户  1:虚拟用户")
    private Integer flag;
    @ApiModelProperty(value = "h5邀请人token")
    private String inviter;
    @ApiModelProperty(value = "app，用户邀请code")
    private String invitationCode;
    @ApiModelProperty(value = "app我的导师code")
    private String leaderCode;
    @ApiModelProperty(value = "绑定导师时间")
    private Date leaderTime;
    @ApiModelProperty(value = "来源：h5  安卓:a   ios: i")
    private String source;
    @ApiModelProperty(value = "用户使用版本")
    private String appVersion;
    @ApiModelProperty(value = "设备信息")
    private String osv;
    @ApiModelProperty(value = "是否认领0否1是")
    private Integer claim;
    @ApiModelProperty(value = "创建时间")
    @TableField("created")
    private Date createTime;

    public UserInfo(String token, String userName, String userHeadImg, String source) {
        this.token = token;
        this.userName = userName;
        this.userHeadImg = userHeadImg;
        this.source = source;
    }
}
