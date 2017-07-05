package com.dispatch.sys.orgright.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.orgright.dao.OrgRightDao;
import com.dispatch.sys.orgright.service.OrgRightService;
@Service
public class OrgRightServiceImpl implements OrgRightService{
	@Autowired
	private OrgRightDao orgRightDao;
 
	public Map getOrgTreeGrid(Org o2) { 
		Map treeGridMap=orgRightDao.getOrgTreeGrid(o2);
		List list=(List)treeGridMap.get("rows");
		for(int i=0;i<list.size();i++){
			Map listMap=(Map)list.get(i);
			String parentid= listMap.get("PARENTID").toString();
			String id=listMap.get("ID").toString();
			listMap.remove("PARENTID");
			if(!id.equals(o2.getId())){
				listMap.put("_parentId", parentid);
			}
		}
		return treeGridMap;
	}

	public OrgRightDao getOrgRightDao() {
		return orgRightDao;
	}

	public void setOrgRightDao(OrgRightDao orgRightDao) {
		this.orgRightDao = orgRightDao;
	}
	/**
	 * 实现说明：保存权限 . <BR>
	 */
	@Override
	public boolean checkOrgRight(String  rightIds,String orgId) {
		return orgRightDao.checkOrgRight(rightIds,orgId);
	}

	@Override
	public List getRightByOrgId(String orgId) {
		return orgRightDao.getRightByOrgId(orgId);
	}     
	 
}
