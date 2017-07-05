package com.dispatch.sys.employee.dao;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.EmpLoyee;
public interface IEmpLoyeeDao {
	
	/**
	 * 方法说明：获取用户列表 . <BR>
	 */
	public Map getEmpLoyeeList(EmpLoyee u,int pageIndex,int pagesize);
	
    /**
     * 方法说明：保存用户. <BR>
     */
    public int saveEmpLoyee(EmpLoyee u);
    
    /**
     * 方法说明：检查工号 . <BR>
     */
    public int checkGH(EmpLoyee u);
    
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	public int updateEmpLoyee(EmpLoyee u);
	
	/**
	 * 方法说明：删除用户 . <BR>
	 */
	public int deleteEmpLoyee(EmpLoyee u);
	
	/**
	 * 方法说明：查找用户 . <BR>
	 */
   public EmpLoyee findEmpLoyeeById(String id);
   
   public int getEmpLoyeeCount();
   
   public List getUserByEmpId(String empId);
}
