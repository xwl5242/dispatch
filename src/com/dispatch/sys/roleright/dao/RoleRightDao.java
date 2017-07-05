package com.dispatch.sys.roleright.dao;

import java.util.List;

public interface RoleRightDao {
	public void checkRoleRight(String rightIds,String roleId);
	public List getRightByRoleId(String roleId);
}
