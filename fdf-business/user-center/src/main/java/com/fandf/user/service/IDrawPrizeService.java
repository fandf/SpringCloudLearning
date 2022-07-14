package com.fandf.user.service;

import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.DrawPrize;
import com.fandf.user.model.DrawPrizeDTO;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/13 14:29
 */
public interface IDrawPrizeService extends ISuperService<DrawPrize> {

    /**
     * 查询奖品列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<DrawPrize> listPrize(Map<String, Object> params, boolean isPage);

    Result saveDrawPrize(DrawPrizeDTO drawPrizeDTO) throws Exception;
}
