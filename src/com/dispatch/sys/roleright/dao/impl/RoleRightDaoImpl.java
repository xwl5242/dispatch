package com.dispatch.sys.roleright.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dispatch.sys.roleright.dao.RoleRightDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.common.UUIDGenerator;
@Repository
public class RoleRightDaoImpl extends PageListJdbcTemplate implements RoleRightDao {

	@Override
	public void checkRoleRight(String rightIds, String roleId) {
		String[] ids=rightIds.split(",");
		String deleteSql="delete SYS_ROLERIGHTRELATION where roleid='"+roleId+"'";
		super.execute(deleteSql);
		if(rightIds!=""){
			for(int i=0;i<ids.length;i++){
				String uuid=UUIDGenerator.getUUID();
				String saveSql="INSERT INTO SYS_ROLERIGHTRELATION (id,rightid,roleid) values (?,?,?)";
				super.update(saveSql,new Object[]{uuid,ids[i],roleId});
			} 
		}
	}

	@Override
	public List getRightByRoleId(String roleId) {
		String sql="select * from SYS_ROLERIGHTRELATION where roleId='"+roleId+"'";
		return super.queryForList(sql);
	}

}
