package com.fandf.user.service;


import com.fandf.common.model.SysRole;
import com.fandf.common.service.ISuperService;
import com.fandf.user.model.SysRoleUser;

import java.util.List;

/**
 * @author fandongfeng
 */
public interface ISysRoleUserService extends ISuperService<SysRoleUser> {
	int deleteUserRole(Long userId, Long roleId);

	int saveUserRoles(Long userId, Long roleId);

	/**
	 * 根据用户id获取角色
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Long userId);

	/**
	 * 根据用户ids 获取
	 *
	 * @param userIds
	 * @return
	 */
	List<SysRole> findRolesByUserIds(List<Long> userIds);
}
