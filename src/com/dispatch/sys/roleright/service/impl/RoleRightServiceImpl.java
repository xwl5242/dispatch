package com.dispatch.sys.roleright.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.roleright.dao.RoleRightDao;
import com.dispatch.sys.roleright.service.RoleRightService;
@Service
public class RoleRightServiceImpl implements RoleRightService{
	@Autowired
	private RoleRightDao roleRightDao;
	@Override
	public void checkRoleRight(String rightIds, String roleId) {
		roleRightDao.checkRoleRight(rightIds, roleId);
	}

	@Override
	public List getRightByRoleId(String roleId) {
		return roleRightDao.getRightByRoleId(roleId);
	}

	public RoleRightDao getRoleRightDao() {
		return roleRightDao;
	}

	public void setRoleRightDao(RoleRightDao roleRightDao) {
		this.roleRightDao = roleRightDao;
	}

}
