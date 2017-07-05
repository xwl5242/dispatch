package com.dispatch.sys.employee.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.EmpLoyee;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.employee.dao.IEmpLoyeeDao;
import com.dispatch.sys.employee.service.EmpLoyeeService;
import com.dispatch.sys.user.dao.IUserDao;

/**
 * 功能描述：  .员工管理服务类  <BR>
 */
@Service
public class EmpLoyeeServiceImpl implements EmpLoyeeService{
    @Autowired
	private IEmpLoyeeDao EmpLoyeeDao;
    @Autowired
	private IUserDao userDao;
	
	/**
	 * 实现说明： 获取用户列表. <BR>
	 */
	@Override
	public Map getEmpLoyeeList(EmpLoyee u,int pageIndex, int pagesize){
		Map map=EmpLoyeeDao.getEmpLoyeeList(u,pageIndex,pagesize);
		return map; 
	}
	 
	/**
	* 实现说明:修改用户. <BR>
	*/
	@Override
	public int updateEmpLoyee(EmpLoyee EmpLoyee){
	 int i =0;
	    if(EmpLoyee!=null){
	     i = EmpLoyeeDao.updateEmpLoyee(EmpLoyee);
	    }else{return 0;}
	 
		return i;
	}

	/**
	* 实现说明：删除用户信息 根据ueser的id. <BR>
	*/
	@Override
	public int deleteEmpLoyee(EmpLoyee EmpLoyee){
	 int i =0;
	   if(EmpLoyee!=null){
		   List<User> list=EmpLoyeeDao.getUserByEmpId(EmpLoyee.getId());
		   for(int a=0;a<list.size();a++){
			   User user=list.get(a);
			   userDao.disableUser(user);
		   }
	     i =EmpLoyeeDao.deleteEmpLoyee(EmpLoyee);   
	   }else{return 0;}
		
		return i;
	}

	/**
	 * 实现说明：根据id查找到用户 . <BR>
	 */	
	@Override
	public EmpLoyee findEmpLoyeeById(String id) {
		EmpLoyee EmpLoyee =null;
		if(!id.isEmpty()){
			EmpLoyee =	EmpLoyeeDao.findEmpLoyeeById(id);
		}
		return EmpLoyee;
	}
	
	/**
	* 实现说明 保存用户 . <BR>
	*/
	@Override
	public int saveEmpLoyee(EmpLoyee EmpLoyee) {
	  int i =0;
	  if(EmpLoyee!=null){
	   i =EmpLoyeeDao.saveEmpLoyee(EmpLoyee);
	   }else{ return 0;}
		return i;
	}
	/**
	 * 实现说明： 检查工号. <BR>
	 */
	public int checkGH(EmpLoyee EmpLoyee){
		int i =0;
		if (EmpLoyee != null) {
			i = EmpLoyeeDao.checkGH(EmpLoyee);
		} else {
			return 0;
		}
			return i;
	}

	public List getUserByEmpId(String empId){
		return EmpLoyeeDao.getUserByEmpId(empId);
	}

	public IEmpLoyeeDao getEmpLoyeeDao() {
		return EmpLoyeeDao;
	}

	public void setEmpLoyeeDao(IEmpLoyeeDao empLoyeeDao) {
		EmpLoyeeDao = empLoyeeDao;
	}
	
}
