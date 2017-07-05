package com.dispatch.sys.orgright.service;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.Org;

public interface OrgRightService {
	/**
	 * 方法说明： 获取组织机构树形列表. <BR>
	 */
	public Map getOrgTreeGrid(Org o2);
	
	/**
	 * 方法说明： 组织保存选中权限. <BR>
	 */
	public boolean checkOrgRight(String  rightIds,String orgId);
	
	/**
	 * 方法说明： 根据组织及狗id获取 所拥有权限. <BR>
	 */
	public List getRightByOrgId(String orgId);
	 
}
