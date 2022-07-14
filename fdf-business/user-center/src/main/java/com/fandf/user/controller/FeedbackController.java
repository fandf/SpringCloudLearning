package com.fandf.user.controller;

import com.fandf.common.model.PageResult;
import com.fandf.user.model.Feedback;
import com.fandf.user.service.IFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 15:50
 */
@RestController
@Api(tags = "意见反馈模块api")
@Slf4j
public class FeedbackController {

    @Resource
    IFeedbackService feedbackService;

    @ApiOperation(value = "查询")
    @GetMapping("/feedback")
    public PageResult<Feedback> feedback(@RequestParam Map<String, Object> params) {
        return feedbackService.pageFeedback(params);
    }

}
