package com.fandf.search.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:29
 */
@Setter
@Getter
@AllArgsConstructor
@ApiModel("逻辑删除DTO")
public class LogicDelDTO {

    @ApiModelProperty("逻辑删除字段名")
    private String logicDelField;

    @ApiModelProperty("逻辑删除字段未删除的值")
    private String logicNotDelValue;
}
