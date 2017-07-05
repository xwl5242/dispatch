package com.dispatch.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;

public interface IUserDao {
	
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getUserList(User user,User u,Org o2, int pageNo, int pageSize);
	
    /**
     * 方法说明：保存用户. <BR>
     */
    public int saveUser(User u);
    
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	public int updateUser(User u);
	
	/**
	 * 方法说明：删除用户 . <BR>
	 */
	public int deleteUser(User u);
	
	/**
	 * 方法说明：查找用户 . <BR>
	 */
   public	User findUserById(String id);
   
   public int getUserCount();

   public int enableUser(User u);

   public int disableUser(User u);
   
   /**
	 * 方法说明：登陆获取用户. <BR>
	 */ 
   public User userLogin(User user);
   /**
	 * 方法说明：修改密码 . <BR>
	 */
	public void updatePassWord(User updUser);
	
	/**
	 * 方法说明： . <BR>
	 */
	public int checkLoginName(User user);

	/**
	 * 方法说明：查询员工是否存在  . <BR>
	 */
	public int findEmpCount(String sysId);

	public void saveUserCount(User user);
	/**
	 * 方法说明： . <BR>
	 */
	public List getDataBaseColumnList();
	
	/**
	 * 方法说明： . <BR>
	 */
	public List getDataBaseGroupTable();

	public List findNamesByOid(String oid);
	
	public List getUnitList(Org org) ;
}
