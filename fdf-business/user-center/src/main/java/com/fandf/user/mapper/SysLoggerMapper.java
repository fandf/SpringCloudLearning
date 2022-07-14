package com.fandf.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.db.mapper.SuperMapper;
import com.fandf.user.model.SysLogger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/13 23:45
 */
@Mapper
public interface SysLoggerMapper  extends SuperMapper<SysLogger> {

    List<SysLogger> findList(Page<SysLogger> page, @Param("r")Map<String, Object> params);

}
