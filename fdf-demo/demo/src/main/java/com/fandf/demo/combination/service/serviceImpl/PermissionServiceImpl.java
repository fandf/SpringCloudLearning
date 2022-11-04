package com.fandf.demo.combination.service.serviceImpl;

import com.fandf.demo.combination.service.PermissionService;

/**
 * 权限校验实现类
 *
 * @author fandongfeng
 * @date 2022-11-4 13:53
 */
public class PermissionServiceImpl implements PermissionService {

    @Override
    public void auth() {
        System.out.println("权限接口---》》》 PermissionServiceImpl 我的行为是校验权限");
    }
}
