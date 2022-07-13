package com.fandf.user.service;


import com.fandf.common.model.PageResult;
import com.fandf.common.model.Result;
import com.fandf.common.model.SysRole;
import com.fandf.common.service.ISuperService;

import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 */
public interface ISysRoleService extends ISuperService<SysRole> {
	void saveRole(SysRole sysRole) throws Exception;

	void deleteRole(Long id);

	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	PageResult<SysRole> findRoles(Map<String, Object> params);

	/**
	 * 新增或更新角色
	 * @param sysRole
	 * @return Result
	 */
	Result saveOrUpdateRole(SysRole sysRole) throws Exception;

	/**
	 * 查询所有角色
	 * @return
	 */
	List<SysRole> findAll();
}
