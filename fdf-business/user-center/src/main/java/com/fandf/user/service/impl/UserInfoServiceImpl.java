package com.fandf.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.lock.DistributedLock;
import com.fandf.common.model.PageResult;
import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.user.mapper.UserInfoMapper;
import com.fandf.user.model.UserInfo;
import com.fandf.user.service.IUserInfoService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/19 12:11
 */
@Service
public class UserInfoServiceImpl extends SuperServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private final static String LOCK_KEY_POSITION = "user_info:";

    @Resource
    private DistributedLock lock;

    @Override
    public PageResult<UserInfo> pageUserInfo(Map<String, Object> params) {
        Page<UserInfo> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        String searchKey = MapUtils.getString(params, "searchKey", null);
        String searchValue = MapUtils.getString(params, "searchValue", null);
        if (StrUtil.isNotBlank(searchKey) && StrUtil.isNotBlank(searchValue)) {
            wrapper.like("liang_hao".equals(searchKey), UserInfo::getLiangHao, searchValue);
            wrapper.like("token".equals(searchKey), UserInfo::getToken, searchValue);
            wrapper.like("user_name".equals(searchKey), UserInfo::getUserName, searchValue);
        }
        wrapper.eq("claim".equals(searchKey), UserInfo::getClaim, 1);
        wrapper.orderByDesc(UserInfo::getToken);
        page(page, wrapper);
        if (page.getRecords().size() > 0) {
            page.getRecords().forEach(d -> {
                if (StrUtil.isNotBlank(d.getLeaderCode())) {
                    UserInfo one = getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getInvitationCode, d.getLeaderCode()).last("limit 1"));
                    if (one != null) {
                        d.setLeaderName(one.getUserName());
                    }
                }
            });
        }
        return PageResult.<UserInfo>builder().data(page.getRecords()).code(0).count(page.getTotal()).build();
    }

}
