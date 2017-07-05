package com.dispatch.sys.userright.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.userright.dao.UserRightDao;
import com.dispatch.sys.userright.service.UserRightService;
@Service
public class UserRightServiceImpl implements UserRightService{
	@Autowired
	private UserRightDao userRightDao;
	
	public void checkUserRight(String rightIds, String userId) {
		userRightDao.checkUserRight(rightIds, userId);
	}

	@Override
	public List getRightByUserId(String userId) {
		return userRightDao.getRightByUserId(userId);
	} 

	public UserRightDao getUserRightDao() {
		return userRightDao;
	}

	public void setUserRightDao(UserRightDao userRightDao) {
		this.userRightDao = userRightDao;
	}

	@Override
	public void saveUserOrgRight(String orgIds, String userId) {
		userRightDao.saveUserOrgRight(orgIds, userId);
	}

	@Override
	public List getOrgByUserId(String userId) {
		return userRightDao.getOrgByUserId(userId);
	}

	@Override
	public List getAllId() {
		return userRightDao.getAllId();
	}
	
}
