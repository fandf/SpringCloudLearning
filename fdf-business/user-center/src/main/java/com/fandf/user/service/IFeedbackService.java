package com.fandf.user.service;

import com.fandf.common.model.PageResult;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.Feedback;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 15:54
 */
public interface IFeedbackService extends ISuperService<Feedback> {

    PageResult<Feedback> pageFeedback(Map<String, Object> params);

}
