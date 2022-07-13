package com.fandf.search.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:29
 */
@Getter
@Setter
@ApiModel("聚合查询VO")
public class AggItemVO {
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("结果")
    private Long value;
}
