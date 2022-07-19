package com.fandf.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.model.PageResult;
import com.fandf.user.mapper.AdvertMapper;
import com.fandf.user.model.TableVO;
import com.fandf.user.service.ISystemService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/18 10:18
 */
@Service
public class SystemServiceImpl implements ISystemService {

    @Resource
    AdvertMapper advertMapper;

    @Override
    public PageResult<TableVO> pageTable(Map<String, Object> params) {
        Page<TableVO> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<TableVO> list = advertMapper.listTable(page, params);
        return PageResult.<TableVO>builder().data(list).code(0).count(page.getTotal()).build();
    }
}
