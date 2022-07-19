package com.fandf.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.db.mapper.SuperMapper;
import com.fandf.user.model.Advert;
import com.fandf.user.model.TableVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 09:47
 */
@Mapper
public interface AdvertMapper extends SuperMapper<Advert> {

    @Select({"<script>",
            "select table_name,table_rows,table_comment from information_schema.tables " ,
            "where table_schema= 'userlink' ",
            "<when test='r.searchTxt != null'>",
            " and table_name like CONCAT('%',#{r.searchTxt},'%') ",
            "</when>",
            "order by table_rows desc",
            "</script>"})
    List<TableVO> listTable(Page<TableVO> page, @Param("r") Map<String, Object> params);
}
