package com.fandf.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandf.common.lock.DistributedLock;
import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.user.mapper.AdvertMapper;
import com.fandf.user.model.Advert;
import com.fandf.user.service.IAdvertService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 09:49
 */
@Service
public class AdvertServiceImpl extends SuperServiceImpl<AdvertMapper, Advert> implements IAdvertService {

    private final static String LOCK_KEY_POSITION = "advert:";

    @Resource
    private DistributedLock lock;

    @Override
    public PageResult<Advert> pageAdvert(Map<String, Object> params) {
        Page<Advert> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        LambdaQueryWrapper<Advert> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(params.containsKey("searchTxt"), Advert::getTitle, params.get("searchTxt"));
        wrapper.orderByDesc(Advert::getId);
        page(page, wrapper);
        return PageResult.<Advert>builder().data(page.getRecords()).code(0).count(page.getTotal()).build();
    }

    @Override
    public Result saveAdvert(Advert advert) throws Exception {
        String img = advert.getImg();
        super.saveOrUpdateIdempotency(advert, lock
                , LOCK_KEY_POSITION+img
                , new QueryWrapper<Advert>().eq("img", img)
                , "图片已存在");
        return Result.succeed("操作成功");
    }
}
