package com.dispatch.sys.employee.service;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.EmpLoyee;


public interface EmpLoyeeService {

	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getEmpLoyeeList(EmpLoyee u, int pageIndex, int pagesize);
	
	/**
	 * 方法说明： . <BR>
	 */
	public int checkGH(EmpLoyee EmpLoyee);

	/**
	 * 方法说明：保存用户 . <BR>
	 */
	public int saveEmpLoyee(EmpLoyee EmpLoyee);
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	public int updateEmpLoyee(EmpLoyee EmpLoyee);
	/**
	 * 方法说明：删除用户 . <BR>
	 */
	public int deleteEmpLoyee(EmpLoyee EmpLoyee);
	
	/**
	 * 方法说明：根据uid查找用户 . <BR>
	 */
    public EmpLoyee findEmpLoyeeById(String id);
    
    public List getUserByEmpId(String empId);
}
