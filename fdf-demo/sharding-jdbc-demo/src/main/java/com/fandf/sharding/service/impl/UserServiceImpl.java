package com.fandf.sharding.service.impl;

import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.sharding.mapper.UserMapper;
import com.fandf.sharding.model.User;
import com.fandf.sharding.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author fandongfeng
 * @date 2022/6/29 11:07
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {
}
