package com.dispatch.sys.userright.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dispatch.sys.userright.dao.UserRightDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.common.UUIDGenerator;
@Repository
public class UserRightDaoImpl extends PageListJdbcTemplate implements UserRightDao {
	public void checkUserRight(String rightIds, String userId) {
		String[] ids=rightIds.split(","); 
		String deleteSql="delete SYS_USERRIGHTRELATION where userId='"+userId+"'";
		super.execute(deleteSql);
		if(rightIds!=""){
			for(int i=0;i<ids.length;i++){
				String uuid=UUIDGenerator.getUUID();
				String saveSql="INSERT INTO SYS_USERRIGHTRELATION (id,rightid,userId) values (?,?,?)";
				super.update(saveSql,new Object[]{uuid,ids[i],userId});
			}  
		}
	}

	@Override
	public List getRightByUserId(String userId) {
		String sql="select * from SYS_USERRIGHTRELATION where userId='"+userId+"'";
		return super.queryForList(sql); 
	}

	@Override
	public void saveUserOrgRight(String orgIds, String userId) {
		String[] ids=orgIds.split(",");
		String deleteSql="delete SYS_ORGUSER where userId='"+userId+"'";
		super.execute(deleteSql);
		if(orgIds!=""){
		for(int i=0;i<ids.length;i++){
			String uuid=UUIDGenerator.getUUID();
			String saveSql="INSERT INTO SYS_ORGUSER (id,ORGCODE,userId) values (?,?,?)";
			super.update(saveSql,new Object[]{uuid,ids[i],userId});
		}  
		}
		
	}

	@Override
	public List getOrgByUserId(String userId) {
		String sql="select * from SYS_ORGUSER where userId='"+userId+"'";
		return super.queryForList(sql);
	}

	@Override
	public List getAllId() {
		String sql="select id from SYS_RIGHT t where t.isdelete='2'";
		return super.queryForList(sql);
	} 

}
