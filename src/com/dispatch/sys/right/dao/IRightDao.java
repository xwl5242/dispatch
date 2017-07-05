package com.dispatch.sys.right.dao;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;

/**
 * 功能描述： 权限菜单管理数据信息 .  <BR>
 */
public interface IRightDao {
	public List<Right> getSystemMenuList(User user,String id);
	public List getSystemList(String userid);
	public int getChildrenCountByUser(String id,String rightIds);
	public List<Map<String, Object>> getAllRightByUser(String userId);
	public List<Map<String, Object>> getRightTreeDataByUser(String pid,String rightids);
	/**
	 * 方法说明：查询并展示权限树 . <BR>
	 */
	public List<Map<String, Object>> getRightTreeData(String pid);
	
	/**
	 * 方法说明：查询权限子信息 . <BR>
	 */
	public int getChildrenCount(String id);
	
	/**
	 * 方法说明：通过权限id查询权限信息 . <BR>
	 */
	public Right getRightnByRightId(String rightId);
	
	/**
	 * 方法说明：新增权限信息 . <BR>
	 */
	public boolean addRight(Right right);
	
	/**
	 * 方法说明： 获得某权限直属上级 <BR>
	 */
    public List<Map<String,Object>> getParentRightList(Long id);
	
	public Right findRightById(String id);
	/**
	 * 方法说明：删除权限信息. . <BR>
	 */
	public int deleteRight(Right right);
	/**
	 * 方法说明：修改权限信息 . <BR>
	 */
	public boolean editRight(Right right);

	public int getChildrenRight(String id);
	
	/**
	 * 方法说明： 查询符合条件的权限菜单树. <BR>
	 */
	public List<Map<String, Object>> selectRightTreeData(String rightName);
	
	/**
	 * 方法说明： . <BR>
	 */
	public List userFavorite(User user);
	
	/**
	 * 方法说明： . <BR>
	 */
	public List selectRightByPid(User user,String pid);
	
	/**
	 * 方法说明： . <BR>
	 */
	public void updateRightSeq(String[] ids, String tableName);
}
