package com.fandf.search.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author fandongfeng
 * @date 2022/7/10 15:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询文档DTO")
public class SearchDTO implements Serializable {

    private static final long serialVersionUID = 1212851454594123034L;

    @ApiModelProperty("搜索关键字")
    private String queryStr;

    @ApiModelProperty("当前页数")
    private Integer page;

    @ApiModelProperty("每页显示数")
    private Integer limit;

    @ApiModelProperty("排序字段")
    private String sortCol;

    @ApiModelProperty("排序顺序")
    private String sortOrder = "DESC";

    @ApiModelProperty("是否显示高亮")
    private Boolean isHighlighter;

    @ApiModelProperty("es的路由")
    private String routing;
    
}
