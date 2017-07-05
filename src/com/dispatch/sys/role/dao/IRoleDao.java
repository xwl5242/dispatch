package com.dispatch.sys.role.dao;

import java.util.List;
/**
 * @author mijan
 * @Description:     
 * @date 2014-11-25   shangwu
 */
import java.util.Map;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.Role;
import com.dispatch.sys.bean.User;
public interface IRoleDao {
	public void saveUserRole(String userId,String roleIds);
	public void deleteUserRole(String userId,String roleIds);
	public List getRoleRightByUserId(String userId);
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getRoleList(Role u,User user,int pageIndex,int pagesize);
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getRoleListByUser(Role r,Org o2,User user, int pageIndex, int pagesize,String userId);
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getRoleListByUserRight(Role r, int pageIndex, int pagesize,String userId);
    /**
     * 方法说明：保存用户. <BR>
     */
    public int saveRole(Role u);
    
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	public int updateRole(Role u);
	
	/**
	 * 方法说明：删除用户 . <BR>
	 */
	public int deleteRole(Role u);
	
	/**
	 * 方法说明：查找用户 . <BR>
	 */
    public	Role findRoleById(String id);
   
    public int getRoleCount();
}
