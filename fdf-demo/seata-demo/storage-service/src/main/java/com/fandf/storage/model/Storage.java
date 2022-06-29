package com.fandf.storage.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fandongfeng
 * @date 2022/6/28 14:38
 */
@Data
@Accessors(chain = true)
@TableName("storage")
public class Storage {

    @TableId
    private Long id;
    private String commodityCode;
    private Long count;

}
