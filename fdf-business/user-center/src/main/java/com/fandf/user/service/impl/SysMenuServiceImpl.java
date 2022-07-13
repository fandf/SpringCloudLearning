package com.fandf.user.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fandf.common.model.SysMenu;
import com.fandf.common.service.impl.SuperServiceImpl;
import com.fandf.user.mapper.SysMenuMapper;
import com.fandf.user.model.SysRoleMenu;
import com.fandf.user.service.ISysMenuService;
import com.fandf.user.service.ISysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author fandongfeng
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends SuperServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
 	@Resource
	private ISysRoleMenuService roleMenuService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void setMenuToRole(Long roleId, Set<Long> menuIds) {
		roleMenuService.delete(roleId, null);

		if (CollUtil.isNotEmpty(menuIds)) {
			List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
			menuIds.forEach(menuId -> roleMenus.add(new SysRoleMenu(roleId, menuId)));
			roleMenuService.saveBatch(roleMenus);
		}
	}

	/**
	 * 角色菜单列表
	 * @param roleIds
	 * @return
	 */
	@Override
	public List<SysMenu> findByRoles(Set<Long> roleIds) {
		return roleMenuService.findMenusByRoleIds(roleIds, null);
	}

	/**
	 * 角色菜单列表
	 * @param roleIds 角色ids
	 * @param roleIds 是否菜单
	 * @return
	 */
	@Override
	public List<SysMenu> findByRoles(Set<Long> roleIds, Integer type) {
		return roleMenuService.findMenusByRoleIds(roleIds, type);
	}

	@Override
	public List<SysMenu> findByRoleCodes(Set<String> roleCodes, Integer type) {
		return roleMenuService.findMenusByRoleCodes(roleCodes, type);
	}

    /**
     * 查询所有菜单
     */
	@Override
	public List<SysMenu> findAll() {
		return list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
	}

    /**
     * 查询所有一级菜单
     */
	@Override
	public List<SysMenu> findOnes() {
        return baseMapper.selectList(
                new QueryWrapper<SysMenu>()
                        .eq("type", 1)
                        .orderByAsc("sort")
        );
	}
}
