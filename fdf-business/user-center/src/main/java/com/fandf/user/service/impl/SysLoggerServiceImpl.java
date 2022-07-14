package com.fandf.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.model.PageResult;
import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.user.mapper.SysLoggerMapper;
import com.fandf.user.model.SysLogger;
import com.fandf.user.service.ISysLoggerService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/13 23:45
 */
@Service
public class SysLoggerServiceImpl extends SuperServiceImpl<SysLoggerMapper, SysLogger> implements ISysLoggerService {
    @Override
    public PageResult<SysLogger> pageSysLogger(Map<String, Object> params) {
        Page<SysLogger> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<SysLogger> list = baseMapper.findList(page, params);
        return PageResult.<SysLogger>builder().data(list).code(0).count(page.getTotal()).build();
    }
}
