package com.fandf.oauth.service;

import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.common.service.ISuperService;
import com.fandf.oauth.model.Client;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/11 14:39
 */
public interface IClientService extends ISuperService<Client> {
    Result saveClient(Client clientDto) throws Exception;

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

    void delClient(long id);

    Client loadClientByClientId(String clientId);
}
