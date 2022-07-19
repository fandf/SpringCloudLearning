package com.fandf.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fandongfeng
 * @date 2022/7/19 15:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("follow_recommend")
@ApiModel(value="follow_recommend对象")
public class FollowRecommend extends Model<FollowRecommend> {
    private static final long serialVersionUID = 37262883214153579L;
    @TableId(type= IdType.ASSIGN_ID)
    private String token;
    @TableField(exist = false)
    private String userName;
    @TableField("`order`")
    private Integer order;
}
