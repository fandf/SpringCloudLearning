package com.fandf.demo.transaction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fandongfeng
 * @date 2023/6/17 14:43
 */
@EqualsAndHashCode(callSuper = true)
@TableName("account_log")
@Data
@Builder
public class AccountLog extends Model<AccountLog> {

    private static final long serialVersionUID = 5648238459610595434L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("第三方原始数据")
    private String data;

    @ApiModelProperty("是否成功: 0否1是")
    @Builder.Default
    private boolean success = true;

    @ApiModelProperty("错误数据")
    private String errorMsg;

}
