package com.fandf.user.service;


import com.fandf.common.model.SysMenu;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author fandongfeng
 */
public interface ISysRoleMenuService extends ISuperService<SysRoleMenu> {
	int save(Long roleId, Long menuId);

	int delete(Long roleId, Long menuId);

	List<SysMenu> findMenusByRoleIds(Set<Long> roleIds, Integer type);

	List<SysMenu> findMenusByRoleCodes(Set<String> roleCodes, Integer type);
}
