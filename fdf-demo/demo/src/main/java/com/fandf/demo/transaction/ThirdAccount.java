package com.fandf.demo.transaction;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fandongfeng
 * @date 2023/6/17 14:45
 */
@Data
@AllArgsConstructor(staticName = "of")
@ApiModel("第三方数据实体")
public class ThirdAccount {

    private String id;
    private String data;


    public AccountLog toAccountLog() {
       return AccountLog.builder().data(JSONUtil.toJsonStr(this)).build();
    }

}
