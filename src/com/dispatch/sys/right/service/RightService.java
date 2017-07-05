package com.dispatch.sys.right.service;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;

/**
 * 功能描述：权限菜单管理服务信息  .  <BR>
 */
public interface RightService {
	
	/**
	 * 方法说明：查询所有信息展示成树形. <BR>
	 */
	public   List<Map<String, Object>> getRightTreeData(String pid);
	/**
	 * 方法说明： . <BR>
	 */
	public Right getRightnByRightId(String RightId);
	/**
	 * 方法说明： 保存权限信息. <BR>
	 */
	public boolean saveRight(Right right);
	/**
	 * 方法说明：修改权限信息 . <BR>
	 */
	public boolean updateRight(Right right);
	/**
	 * 方法说明： 删除权限信息. <BR>
	 */
	public int deleteRight(Right right);
	/**
	 * 方法说明：通过id查询权限信息 . <BR>
	 */
	public Right findRightById(String id);
	public List<Map<String, Object>> getAllRightByUser(String userId);
	public   List<Map<String, Object>> AllRightTreeData(String pid);
	public   List<Map<String, Object>> AllRightTreeByUser(String pid,String rightIds);
	/**
	 * 方法说明： . <BR>
	 */
	public List getSystemRight(String userId);
	/**
	 * 方法说明： . <BR>
	 */
	public List<Right> getSystemMenuList(User user,String id);
	
	/**
	 * 方法说明：获取用户特别关注的功能 . <BR>
	 */
	public List userFavorite(User user);
	public int findChildrenCount(String id);
	
	/**
	 * 方法说明： 查询符合条件的权限菜单树. <BR>
	 */
	public List<Map<String, Object>> selectRightTreeData(String rightName);
	
	/**
	 * 方法说明： . <BR>
	 */
	public List selectRightByPid(User user,String pid);
	
	/**
	 * 方法说明： . <BR>
	 */
	public void updateRightSeq(String ids [], String tableName);
}
