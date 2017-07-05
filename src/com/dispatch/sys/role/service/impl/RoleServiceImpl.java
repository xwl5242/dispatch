package com.dispatch.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.Role;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.role.dao.IRoleDao;
import com.dispatch.sys.role.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
   @Autowired
	private IRoleDao RoleDao;
	
	/**
	 * 实现说明： 获取用户列表. <BR>
	 */
	@Override
	public Map getRoleList(Role u,User user,int pageIndex, int pagesize){
		
		return RoleDao.getRoleList(u, user, pageIndex, pagesize);
	}
	 
	/**
	 * 实现说明:修改用户. <BR>
	 */
	@Override
	public int updateRole(Role Role){
	 int i =0;
	    if(Role!=null){
	     i = RoleDao.updateRole(Role);
	    }else{return 0;}
	 
		return i;
	}

	 /**
	 * 实现说明：删除用户信息 根据ueser的id. <BR>
	 */
	@Override
	public int deleteRole(Role Role){
	 int i =0;
	   if(Role!=null){
	     i =RoleDao.deleteRole(Role);   
	   }else{return 0;}
		
		return i;
	}

	/**
	 * 实现说明：根据id查找到角色. <BR>
	 */	
	@Override
	public Role findRoleById(String id) {
		Role Role =null;
		if(!id.isEmpty()){
			Role =	RoleDao.findRoleById(id);
		}
		return Role;
	}
	
	 /**
	 * 实现说明 保存角色. <BR>
	 */
	@Override
	public int saveRole(Role Role) {
	  int i =0;
	  if(Role!=null){
	   i =RoleDao.saveRole(Role);
	   }else{ return 0;}
		return i;
	}

	@Override
	public Map getRoleListByUser(Role r,Org o2,User user, int pageIndex, int pagesize,String userId) {
		return RoleDao.getRoleListByUser(r,o2,user, pageIndex, pagesize, userId);
	}
	
	@Override
	public Map getRoleListByUserRight(Role r, int pageIndex, int pagesize,String userId) {
		return RoleDao.getRoleListByUserRight(r, pageIndex, pagesize, userId);
	}
	
	@Override
	public List getRoleRightByUserId(String userId) {
		return RoleDao.getRoleRightByUserId(userId);
	}
	
	@Override
	public void saveUserRole(String userId, String roleIds) {
		RoleDao.saveUserRole(userId, roleIds);
	}
	
	@Override
	public void deleteUserRole(String userId, String roleIds) {
		RoleDao.deleteUserRole(userId, roleIds);
	}

}
