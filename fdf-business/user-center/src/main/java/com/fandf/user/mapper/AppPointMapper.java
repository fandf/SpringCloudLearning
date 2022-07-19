package com.fandf.user.mapper;

import com.fandf.db.mapper.SuperMapper;
import com.fandf.user.model.AppPoint;
import com.fandf.user.model.ConsoleDateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/7/18 15:52
 */
@Mapper
public interface AppPointMapper extends SuperMapper<AppPoint> {

    @Select("SELECT DATE_FORMAT(p.created,'%Y-%m-%d %H:00') dateStr,count(*) as count " +
            "FROM app_point p " +
            "where p.created >= #{dateTime} " +
            "GROUP BY datestr ORDER BY datestr asc")
    List<ConsoleDateVO> getDayPv(Date dateTime);
    @Select("SELECT DATE_FORMAT(p.created,'%Y-%m-%d %H:00') dateStr,count(DISTINCT token) as count " +
            "FROM app_point p " +
            "where p.created >= #{dateTime} " +
            "GROUP BY datestr ORDER BY datestr asc")
    List<ConsoleDateVO> getDayUv(Date dateTime);

    @Select("SELECT DATE_FORMAT(p.created,'%Y-%m-%d') dateStr,count(*) as count " +
            "FROM app_point p " +
            "where p.created >= #{dateWeek} " +
            "GROUP BY datestr ORDER BY datestr asc")
    List<ConsoleDateVO> getWeekPv(Date dateWeek);

    @Select("SELECT DATE_FORMAT(p.created,'%Y-%m-%d') dateStr,count(DISTINCT token) as count " +
            "FROM app_point p " +
            "where p.created >= #{dateWeek} " +
            "GROUP BY datestr ORDER BY datestr asc")
    List<ConsoleDateVO> getWeekUv(Date dateWeek);
}
