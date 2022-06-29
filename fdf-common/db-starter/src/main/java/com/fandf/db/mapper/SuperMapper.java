package com.fandf.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让 mp 扫描到！！
 * @param <T>
 * @author fandongfeng
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    //公共方法
}
