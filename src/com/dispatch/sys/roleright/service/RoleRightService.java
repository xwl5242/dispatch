package com.dispatch.sys.roleright.service;

import java.util.List;

public interface RoleRightService {
	public void checkRoleRight(String rightIds,String roleId);
	public List getRightByRoleId(String roleId);
}
