package com.fandf.user.service;

import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.Advert;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/14 09:48
 */
public interface IAdvertService extends ISuperService<Advert> {
    PageResult<Advert> pageAdvert(Map<String, Object> params);

    Result saveAdvert(Advert advert) throws Exception;
}
