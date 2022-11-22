package com.fandf.mongo.core.base.dao;

import com.fandf.mongo.core.BaseDao;
import com.fandf.mongo.core.base.entity.User;

/**
 * @author fandongfeng
 * @date 2022/11/19 14:16
 */
public class UserDao extends BaseDao<User> {

    public UserDao(){
        super(User.class);
    }

}
