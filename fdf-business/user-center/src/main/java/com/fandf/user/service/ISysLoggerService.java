package com.fandf.user.service;

import com.fandf.common.model.PageResult;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.SysLogger;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/13 23:40
 */
public interface ISysLoggerService extends ISuperService<SysLogger> {

    PageResult<SysLogger> pageSysLogger(Map<String, Object> params);
}
