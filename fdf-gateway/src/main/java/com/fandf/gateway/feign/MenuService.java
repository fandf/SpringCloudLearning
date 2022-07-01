package com.fandf.gateway.feign;

import com.fandf.common.constant.ServiceNameConstants;
import com.fandf.common.model.SysMenu;
import com.fandf.gateway.feign.fallback.MenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/6/29 18:25
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {

    /**
     * 角色菜单列表
     * @param roleCodes
     */
    @GetMapping(value = "/menus/{roleCodes}")
    List<SysMenu> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);

}
