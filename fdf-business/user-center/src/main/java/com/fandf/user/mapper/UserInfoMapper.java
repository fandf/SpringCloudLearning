package com.fandf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fandf.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2021/12/24 下午5:00
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("SELECT osv, count(1) as cnt from user_info WHERE osv != '' GROUP BY osv ")
    List<Map<String, Integer>> getOsv();
}
