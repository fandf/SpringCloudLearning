package com.fandf.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fandf.common.model.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/6/30 10:50
 */
public interface SysGeneratorService extends IService {

    PageResult queryList(Integer pageNo, Integer pageSize, String tableName);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames);
}
