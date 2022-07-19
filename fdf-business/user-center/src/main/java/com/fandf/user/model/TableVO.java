package com.fandf.user.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author fandongfeng
 * @date 2022/7/18 10:14
 */
@ApiModel("表信息")
@Data
public class TableVO {

    private String tableName;
    private String tableRows;
    private String tableComment;

}
