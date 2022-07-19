package com.fandf.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.log.annotation.AuditLog;
import com.fandf.user.mapper.FollowRecommendMapper;
import com.fandf.user.model.FollowRecommend;
import com.fandf.user.model.UserInfo;
import com.fandf.user.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/19 15:15
 */
@RestController
@Api(tags = "关注模块api")
@Slf4j
@RequestMapping("/follow")
public class FollowController {

    @Resource
    FollowRecommendMapper followRecommendMapper;
    @Resource
    IUserInfoService userInfoService;

    @ApiOperation(value = "推荐关注列表")
    @GetMapping("/recommend")
    public  PageResult<FollowRecommend> findMenusByRoleId(@RequestParam Map<String, Object> params) {
        Page<FollowRecommend> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        LambdaQueryWrapper<FollowRecommend> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(params.containsKey("searchTxt") && StrUtil.isNotBlank(params.get("searchTxt").toString()), FollowRecommend::getToken, params.get("searchTxt"));
        wrapper.orderByDesc(Arrays.asList(FollowRecommend::getOrder, FollowRecommend::getToken));
        Page<FollowRecommend> followRecommendPage = followRecommendMapper.selectPage(page, wrapper);
        if (followRecommendPage.getRecords().size() > 0) {
            followRecommendPage.getRecords().forEach(d -> {
                UserInfo one = userInfoService.getById(d.getToken());
                if (one != null) {
                    d.setUserName(one.getUserName());
                }
            });
        }
        return PageResult.<FollowRecommend>builder().data(followRecommendPage.getRecords()).code(0).count(followRecommendPage.getTotal()).build();
    }

    @ApiOperation(value = "修改")
    @PostMapping("/recommend/saveOrUpdate")
    @AuditLog(operation = "'保存或者修改推荐关注:' + #followRecommend.token")
    public Result saveOrUpdate(@RequestBody FollowRecommend followRecommend) {
        FollowRecommend recommend = followRecommendMapper.selectById(followRecommend.getToken());
        if(recommend != null) {
            recommend.setOrder(followRecommend.getOrder());
            recommend.updateById();
            return Result.succeed("修改成功");
        }else {
            followRecommend.insert();
            return Result.succeed("新增成功");
        }
    }

    /**
     * 删除用户
     *
     * @param token
     */
    @DeleteMapping(value = "/recommend/{token}")
    @AuditLog(operation = "'删除推荐关注:' + #token")
    public Result delete(@PathVariable String token) {
        followRecommendMapper.deleteById(token);
        return Result.succeed("删除成功");
    }
}
