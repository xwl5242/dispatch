package com.dispatch.sys.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.user.dao.IUserDao;
import com.dispatch.sys.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private IUserDao userDao;

	/**
	 * 实现说明： 获取用户列表. <BR>
	 */
	@Override
	public Map getUserList(User u,User us, Org o2,int pageIndex, int pagesize) {
		Map map = userDao.getUserList(u,us,o2, pageIndex, pagesize);
		return map;
	}

	/**
	 * 实现说明:修改用户. <BR>
	 */
	@Override
	public int updateUser(User user) {
		int i = 0;
		if (user != null) {
			i = userDao.updateUser(user);
		} else {
			return 0;
		}

		return i;
	}

	/**
	 * 实现说明：删除用户信息 根据ueser的id. <BR>
	 */
	@Override
	public int deleteUser(User user) {
		int i = 0;
		if (user != null) {
			i = userDao.deleteUser(user);
		} else {
			return 0;
		}

		return i;
	}

	/**
	 * 实现说明：根据id查找到用户 . <BR>
	 */
	@Override
	public User findUserById(String id) {
		User user = null;
		if (!id.isEmpty()) {
			user = userDao.findUserById(id);
		}
		return user;
	}

	/**
	 * 实现说明 保存用户 . <BR>
	 */
	@Override
	public int saveUser(User user) {
		int i = 0;
		if (user != null) {
			i = userDao.saveUser(user);
		} else {
			return 0;
		}
		return i;
	}

	/**
	 * 实现说明 启用 . <BR>
	 */
	@Override
	public int enableUser(User user) {
		int i = 0;
		if (user != null) {
			i = userDao.enableUser(user);
		} else {
			return 0;
		}

		return i;
	}

	/**
	 * 实现说明 停用 . <BR>
	 */
	@Override
	public int disableUser(User u) {
		int i = 0;
		if (u != null) {
			i = userDao.disableUser(u);
		} else {
			return 0;
		}
		return i;
	}

	/**
	 * 实现说明 登陆获取用户 . <BR>
	 */

	@Override
	public User userLogin(User user) {
		User u = userDao.userLogin(user);
		return u;
	}

	/**
	 * 实现说明： 修改密码. <BR>
	 */
	@Override
	public void updatePassWord(User updUser) {
		userDao.updatePassWord(updUser);
	}

	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public int checkLoginName(User user) {
		int count=userDao.checkLoginName(user);
		return count;
	}

	/**
	 * 实现说明： 查询员工是否存在 . <BR>
	 */
	@Override
	public int findEmpCount(String sysId) {
		return userDao.findEmpCount(sysId);
	}

	@Override
	public void saveUserCount(User user) {
		userDao.saveUserCount(user);
	}
	
	@Override
	public HashMap getDataBaseColumnList() {
		List tableList=userDao.getDataBaseGroupTable();
		List columnList=userDao.getDataBaseColumnList();
		HashMap finalMap=new HashMap();
		for(int i=0;i<tableList.size();i++){
			
			HashMap tableMap=(HashMap)tableList.get(i);
			String table=tableMap.get("TABLE_NAME")+"";
			List newList=new ArrayList();
			
			for(int k=0;k<columnList.size();k++){
				HashMap newMap=new HashMap();
				HashMap columnMap=(HashMap)columnList.get(k);
				String columnTable=columnMap.get("TABLE_NAME")+"";
				if(table.equals(columnTable)){
					newMap.put("column",columnMap.get("COLUMN_NAME")+"");
					newMap.put("type",columnMap.get("DATA_TYPE")+"");
					newList.add(newMap);
				}
				
			}
			finalMap.put(table, newList);
			
		}
		return finalMap;
	}
	/**
	 * 实现说明： 通过组织id查询组织名称. <BR>
	 */
	@Override
	public List findNamesByOid(String oid) {
		
		return userDao.findNamesByOid(oid);
	}

	@Override
	public List getUnitList(Org org) {
		return userDao.getUnitList(org);
	}

}
