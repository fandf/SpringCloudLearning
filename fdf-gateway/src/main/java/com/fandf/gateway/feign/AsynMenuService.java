package com.fandf.gateway.feign;

import com.fandf.common.model.SysMenu;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author fandongfeng
 * @date 2022/6/29 18:25
 */
@Component
public class AsynMenuService {

    @Lazy
    @Resource
    private MenuService menuService;

    @Async
    public Future<List<SysMenu>> findByRoleCodes(String roleCodes) {
        List<SysMenu> result = menuService.findByRoleCodes(roleCodes);
        return new AsyncResult<>(result);
    }

}
