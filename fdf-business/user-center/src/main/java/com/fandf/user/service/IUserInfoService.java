package com.fandf.user.service;

import com.fandf.common.model.PageResult;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.UserInfo;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/19 12:10
 */
public interface IUserInfoService extends ISuperService<UserInfo> {
    PageResult<UserInfo> pageUserInfo(Map<String, Object> params);
}
