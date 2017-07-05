package com.dispatch.sys.role.service;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.Role;
import com.dispatch.sys.bean.User;

public interface RoleService {
	public void saveUserRole(String userId,String roleIds);
	public void deleteUserRole(String userId,String roleIds);
	public List getRoleRightByUserId(String userId);
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getRoleListByUser(Role r,Org o2, User user, int pageIndex, int pagesize,String userId);
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getRoleListByUserRight(Role r, int pageIndex, int pagesize,String userId);
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getRoleList(Role r,User user, int pageIndex, int pagesize);

	/**
	 * 方法说明：保存用户 . <BR>
	 */
	public int saveRole(Role role);
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	public int updateRole(Role role);
	/**
	 * 方法说明：删除用户 . <BR>
	 */
	public int deleteRole(Role role);
	
	/**
	 * 方法说明：根据uid查找用户 . <BR>
	 */
   public Role findRoleById(String id);

}
