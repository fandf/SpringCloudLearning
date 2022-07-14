package com.fandf.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.model.PageResult;
import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.user.mapper.FeedbackMapper;
import com.fandf.user.model.Feedback;
import com.fandf.user.service.IFeedbackService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 15:55
 */
@Service
public class FeedbackServiceImpl extends SuperServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

    @Override
    public PageResult<Feedback> pageFeedback(Map<String, Object> params) {
        Page<Feedback> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(params.containsKey("searchTxt"), Feedback::getContent, params.get("searchTxt"));
        wrapper.orderByDesc(Feedback::getId);
        page(page, wrapper);
        return PageResult.<Feedback>builder().data(page.getRecords()).code(0).count(page.getTotal()).build();
    }
}
