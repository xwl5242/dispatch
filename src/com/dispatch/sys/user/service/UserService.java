package com.dispatch.sys.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;


public interface UserService {

	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getUserList(User u,User us,Org o2, int pageIndex, int pagesize);

	/**
	 * 方法说明：保存用户 . <BR>
	 */
	public int saveUser(User user);
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	public int updateUser(User user);
	/**
	 * 方法说明：删除用户 . <BR>
	 */
	public int deleteUser(User user);
	
	/**
	 * 方法说明：根据uid查找用户 . <BR>
	 */
    public User findUserById(String id);
  /**
 	 * 方法说明：启用 . <BR>
 	 */
  public int enableUser(User user);
  /**
	 * 方法说明：停用 . <BR>
	 */ 
  public int disableUser(User u);
  /**
	 * 方法说明：登陆获取用户. <BR>
	 */ 
  public User userLogin(User user);
  
 	/**
 	 * 方法说明： 修改密码. <BR>
 	 */
 	public void updatePassWord(User updUser);
 	
 	/**
 	 * 方法说明： . <BR>
 	 */
 	public int checkLoginName(User user);

 	/**
 	 * 方法说明：查询员工是否存在 . <BR>
 	 */
	public int findEmpCount(String sysId);

	public void saveUserCount(User user);
	
	
	public List getUnitList(Org org);
	
	/**
	 * 方法说明： . <BR>
	 */
	public HashMap getDataBaseColumnList();

	public List findNamesByOid(String oid);
}
