package com.fandf.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/6/30 10:48
 */
@Component
@Mapper
public interface SysGeneratorMapper extends BaseMapper<T> {
    List<Map<String, Object>> queryList(Page<Map<String, Object>> page, @Param("p") Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
