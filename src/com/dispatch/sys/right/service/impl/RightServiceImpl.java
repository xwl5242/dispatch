package com.dispatch.sys.right.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.right.dao.IRightDao;
import com.dispatch.sys.right.service.RightService;

/**
 * 功能描述：权限菜单管理服务信息   .  <BR>
 */
@Service
public class RightServiceImpl implements RightService {
	
	@Autowired
	IRightDao rightdao;
	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public int deleteRight(Right right) {
		int i =0;
		if(right!=null){
			i = rightdao.deleteRight(right);
		}else{
			return 0;
		}
		return i;
	}
	/**
	 * 实现说明： 通过id查询权限信息. <BR>
	 */
	@Override
	public Right findRightById(String id) {
		Right right = null;
		if(!id.isEmpty()){
			right=rightdao.findRightById(id);
		}
		return right;
	}
	/**
	 * 实现说明： 查询所有权限信息以树形展示. <BR>
	 */
	@Override
	public List<Map<String, Object>> getRightTreeData(String pid) {
		List<Map<String, Object>> list = rightdao.getRightTreeData(pid);
		 List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
		 for (Map<String, Object> map : list) {
				Map<String, Object> m=new HashMap<String, Object>(); 
					m.put("id",map.get("ID")); 
					m.put("text", map.get("RIGHTNAME"));
				    String num = rightdao.getChildrenCount((String) map.get("ID")) + "";
		            m.put("state", "0".equals(num) ? "open" : "closed");
		            returnlist.add(m);
			}
				return returnlist;
	}
	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public Right getRightnByRightId(String RightId) {
		return rightdao.getRightnByRightId(RightId);
	}
	/**
	 * 实现说明：保存权限信息 . <BR>
	 */
	@Override
	public boolean saveRight(Right right) {
	
			return  rightdao.addRight(right);
	}
	/**
	 * 实现说明：修改权限信息 . <BR>
	 */
	@Override
	public boolean updateRight(Right right) {
			return rightdao.editRight(right);
	}

	@Override
	public List<Map<String, Object>> AllRightTreeData(String pid) { 
		List<Map<String, Object>> list = rightdao.getRightTreeData(pid);
		 List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
		 for (Map<String, Object> map : list) {
				Map<String, Object> m=new HashMap<String, Object>(); 
					m.put("id",map.get("ID"));
					m.put("text", map.get("RIGHTNAME"));
				    String num = rightdao.getChildrenCount((String) map.get("ID")) + "";
		            m.put("state", "0".equals(num) ? "open" : "closed");
		            if(!"0".equals(num)){
		            	m.put("children", AllRightTreeData(map.get("ID").toString()));
		            }
		            m.put("url", map.get("RIGHTURL"));
		            if((map.get("RIGHTURL")+"").equals("#")){
		            	m.put("type", "0");
		            }else{
		            	m.put("type", "1");
		            }
		            returnlist.add(m);
			} 
				return returnlist;
	}
	/**
	 * 实现说明：查询是否有子节点 . <BR>
	 */
	@Override
	public int findChildrenCount(String id) {
		int count = 0;
		if(!id.isEmpty()){
			 count=rightdao.getChildrenRight(id);
		}
		return count; 
	}
	@Override
	public List<Map<String, Object>> getAllRightByUser(String userId) {
		return rightdao.getAllRightByUser(userId);
	}
	@Override
	public List<Map<String, Object>> AllRightTreeByUser(String pid,
			String rightIds) { 
		List<Map<String, Object>> list = rightdao.getRightTreeDataByUser(pid,rightIds);
		 List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
		 for (Map<String, Object> map : list) {
				Map<String, Object> m=new HashMap<String, Object>(); 
					m.put("id",map.get("ID"));
					m.put("text", map.get("RIGHTNAME"));
				    String num = rightdao.getChildrenCountByUser((String) map.get("ID"),rightIds) + "";
		            m.put("state", "0".equals(num) ? "open" : "closed");
		            if(!"0".equals(num)){
		            	m.put("children", AllRightTreeByUser(map.get("ID").toString(),rightIds));
		            }
		            m.put("url", map.get("RIGHTURL"));
		            if(map.get("RIGHTURL").equals("#")){
		            	m.put("type", "0");
		            }else{
		            	m.put("type", "1");
		            }
		            returnlist.add(m);
			} 
				return returnlist;
	}
	/**
	 * 实现说明： . <BR>
	 */
	public List getSystemRight(String userid){
		return rightdao.getSystemList(userid);
		
	}
	/**
	 * 实现说明： . <BR>
	 */
	public List<Right> getSystemMenuList(User user,String id){
		return rightdao.getSystemMenuList(user, id);
	}
	
	/**
	 * 实现说明：查询符合条件的权限菜单树 . <BR>
	 */
	@Override
	public List<Map<String, Object>> selectRightTreeData(String rightName) {
		return rightdao.selectRightTreeData(rightName);
	}
	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public List userFavorite(User user) {
		if(user==null){
			return new ArrayList();
		}
		return rightdao.userFavorite(user);
	}
	/**
	 * 实现说明： . <BR>
	 */
	public List selectRightByPid(User user,String pid){
		
		return rightdao.selectRightByPid(user,pid);
	}
	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public void updateRightSeq(String[] ids,String tableName) {
		 rightdao.updateRightSeq(ids,tableName);;
	}
	
}
