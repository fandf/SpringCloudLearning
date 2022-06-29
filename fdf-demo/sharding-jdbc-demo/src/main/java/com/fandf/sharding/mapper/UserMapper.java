package com.fandf.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fandf.sharding.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fandongfeng
 * @date 2022/6/29 11:08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
