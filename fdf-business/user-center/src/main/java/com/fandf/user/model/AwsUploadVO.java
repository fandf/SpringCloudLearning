package com.fandf.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fandongfeng
 * @date 2021/12/16 下午3:34
 */
@Data
@ApiModel("亚马逊上传返回实体")
@NoArgsConstructor
@AllArgsConstructor
public class AwsUploadVO {

    private String id;
    @ApiModelProperty("文件名")
    private String name;
    @ApiModelProperty("文件类型 photo:图片 file:文件 video:视频 audio:音频 other:其他")
    private String type;
    @ApiModelProperty("文件url地址")
    private String url;

}
