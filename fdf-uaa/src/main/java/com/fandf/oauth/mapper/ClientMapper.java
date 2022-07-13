package com.fandf.oauth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.db.mapper.SuperMapper;
import com.fandf.oauth.model.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/11 15:33
 */
@Mapper
public interface ClientMapper extends SuperMapper<Client> {
    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );
}
