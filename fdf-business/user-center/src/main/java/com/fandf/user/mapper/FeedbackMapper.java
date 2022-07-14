package com.fandf.user.mapper;

import com.fandf.db.mapper.SuperMapper;
import com.fandf.user.model.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fandongfeng
 * @date 2022/7/14 15:53
 */
@Mapper
public interface FeedbackMapper extends SuperMapper<Feedback> {
}
