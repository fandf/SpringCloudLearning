package com.fandf.demo.combination.adapt;

import com.fandf.demo.combination.service.PermissionService;
import com.fandf.demo.combination.service.DataProcessingService;

/**
 * 基于组合的对象适配器
 *
 * @author fandongfeng
 * @date 2022-11-4 15:31
 */
public class PermissionAdapter implements DataProcessingService {

    private PermissionService permissionService;

    public PermissionAdapter(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void process() {
        permissionService.auth();
    }
}
