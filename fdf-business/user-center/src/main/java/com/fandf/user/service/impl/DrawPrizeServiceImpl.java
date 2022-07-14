package com.fandf.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.lock.DistributedLock;
import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.user.mapper.DrawPrizeMapper;
import com.fandf.user.model.DrawPrize;
import com.fandf.user.model.DrawPrizeDTO;
import com.fandf.user.service.IDrawPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/13 14:30
 */
@Slf4j
@Service
public class DrawPrizeServiceImpl extends SuperServiceImpl<DrawPrizeMapper, DrawPrize> implements IDrawPrizeService {

    private final static String LOCK_KEY_POSITION = "drawPrize:";

    @Autowired
    private DistributedLock lock;

    @Override
    public PageResult<DrawPrize> listPrize(Map<String, Object> params, boolean isPage) {
        Page<DrawPrize> page;
        if (isPage) {
            page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        } else {
            page = new Page<>(1, -1);
        }
        LambdaQueryWrapper<DrawPrize> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(params.containsKey("searchTxt"), DrawPrize::getName, params.get("searchTxt"));
        wrapper.orderByAsc(DrawPrize::getPosition);
        page(page, wrapper);
        return PageResult.<DrawPrize>builder().data(page.getRecords()).code(0).count(page.getTotal()).build();
    }

    @Override
    public Result saveDrawPrize(DrawPrizeDTO drawPrizeDTO) throws Exception {
        DrawPrize drawPrize = BeanUtil.copyProperties(drawPrizeDTO, DrawPrize.class);
        Integer position = drawPrize.getPosition();
        super.saveOrUpdateIdempotency(drawPrize, lock
                , LOCK_KEY_POSITION+position
                , new QueryWrapper<DrawPrize>().eq("position", position)
                , "位置"+position.toString() + "已存在");
        return Result.succeed("操作成功");
    }
}
