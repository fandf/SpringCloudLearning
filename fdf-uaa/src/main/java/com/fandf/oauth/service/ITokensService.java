package com.fandf.oauth.service;

import com.fandf.common.model.PageResult;
import com.fandf.oauth.model.TokenVO;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/11 14:37
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageResult<TokenVO> listTokens(Map<String, Object> params, String clientId);
}
