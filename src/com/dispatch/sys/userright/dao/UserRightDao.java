package com.dispatch.sys.userright.dao;

import java.util.List;

public interface UserRightDao {

	public void checkUserRight(String rightIds,String userId);
	public List getRightByUserId(String userId);
	public void saveUserOrgRight (String orgIds,String userId);
	public List getOrgByUserId( String userId);
	public List getAllId();
}
