package com.dispatch.sys.employee.dao.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.EmpLoyee;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.employee.dao.IEmpLoyeeDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;

@Repository
public class EmpLoyeeDaoImpl extends PageListJdbcTemplate implements IEmpLoyeeDao {

	/**
	 * 实现说明： 获取员工列表. <BR>
	 */
	public Map getEmpLoyeeList(EmpLoyee empLoyee , int pageNo, int pageSize) {
		String sql = " select id,name,age,mobile,code,sex,(select o.orgname from sys_org o where o.id=t.orgcode) as orgcode,isdelete,createtime,createman  from  sys_employee t  where 1=1 ";
		String conditions=" and isdelete = '2' ";
		//员工姓名
	    if(empLoyee.getName()!=null&& !empLoyee.getName().equals("")){ 
	    sql = sql + " and name like '%"+empLoyee.getName()+"%'";
	    }
	    //性别
	    if(empLoyee.getSex()!=null&&!empLoyee.getSex().equals("")){
		    sql = sql + " and Sex = '"+empLoyee.getSex()+"'";
		 }
	    //工号
	    if(empLoyee.getCode()!=null&&!empLoyee.getCode().equals("")){
		    sql = sql + " and Code like'%"+empLoyee.getCode()+"%'";
		 }
	    if(empLoyee.getOrgcode()!=null&&!empLoyee.getOrgcode().equals("")){
		    sql = sql + " and orgcode in (SELECT ID FROM  SYS_ORG   START WITH ID='"+empLoyee.getOrgcode()+"' CONNECT BY PARENTID= PRIOR id )";
		 }
		return 	super.queryGridist(sql, conditions, pageNo, pageSize);
	}

	/**
	 * 实现说明：保存EmpLoyee对象 . <BR>
	 */
	@Override
	public int saveEmpLoyee(EmpLoyee r) {
		int n =0;
		  String uuid=UUIDGenerator.getUUID();
		  r.setId(uuid);
		if(r!=null){ 
			n =	super.update("insert into SYS_EMPLOYEE(ID,NAME,AGE,MOBILE,CODE,SEX,ORGCODE,ISDELETE,CREATETIME,CREATEMAN)"
			+ " values(?,?,?,?,?,?,?,?,?,?)",
			new Object[] { r.getId(),r.getName(),r.getAge(),r.getMobile(),r.getCode()
					       ,r.getSex(),r.getOrgcode(),r.getIsdelete(),r.getCreatetime(),r.getCreateman()
					});
		}else{
			return 0;
		}
	
		
		return n;
	}
	
	/**
	 * 方法说明： . <BR>
	 */
	public int checkGH(EmpLoyee r){
		String sql = "select count(*) from sys_EmpLoyee se where  code='"+r.getCode()+"'";
		if(r.getId()!=null&& !r.getId().equals("")){
			sql += " and se.id not in '"+r.getId()+"'";
		}
		int count = super.queryForInt(sql);
		return count;
	}

	/**
	 * 实现说明：修改对象 . <BR>
	 */
	@Override
	public int updateEmpLoyee(EmpLoyee r ) {
		
		EmpLoyee employee=this.findEmpLoyeeById(r.getId());
		
		employee.setName(r.getName());
		employee.setAge(r.getAge());
		employee.setMobile(r.getMobile());
		employee.setCode(r.getCode()); 
		employee.setSex(r.getSex());
		employee.setOrgcode(r.getOrgcode());
		employee.setId(r.getId());
        int i = super.update(  "update sys_EmpLoyee set  NAME=?,AGE=?,MOBILE=?,CODE=?,SEX=?,ORGCODE=? where id=?",
					new Object[] {employee.getName(),employee.getAge(),employee.getMobile(),employee.getCode(),employee.getSex(),
        		employee.getOrgcode(),employee.getId()
                        });  
       
        return i;  
	}
	/**
	 * 实现说明：删除对象 . <BR>
	 */
	@Override
	public int deleteEmpLoyee(EmpLoyee e){
		 int i =0;
		if(e!=null){
		String sql="select id from sys_user where sysempid='"+e.getId()+"'";
		List<Map<String,Object>>  list=super.queryForList(sql);
		for(int a =0;a<list.size();a++){
			String usql="update sys_user set userstat='"+EnumUtil.USERSTATE2+"' where id='"+list.get(a).get("ID")+"'";
			super.execute(usql);
		}
         i = super.update("update sys_EmpLoyee set isdelete='"+EnumUtil.PARAMETERSSTATUS1+"' where id=? ",  
		                new Object[] { e.getId()}); 
		}else{ 
			return 0;
		}
		
		return i;
	}

	/**
	 * 实现说明通过id 查找相应的员工对象： . <BR>
	 */
	@Override
	public EmpLoyee findEmpLoyeeById(String id) {
		EmpLoyee sEmpLoyee =null;
		sEmpLoyee= (EmpLoyee)super.queryForObject("select * from sys_EmpLoyee where id=?", new Object[] { id }, new RowMapper() {  
            @Override  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException {  
            	EmpLoyee e2=new EmpLoyee();
            	e2.setId(rs.getString("id"));   
            	e2.setName(rs.getString("NAME"));
            	e2.setAge(rs.getString("AGE"));
            	e2.setMobile(rs.getString("MOBILE"));
            	e2.setCode(rs.getString("CODE"));
            	e2.setSex(rs.getString("SEX"));
            	e2.setOrgcode(rs.getString("ORGCODE"));
            	e2.setCreatetime(rs.getString("CREATETIME"));
            	e2.setCreateman(rs.getString("CREATEMAN"));
               return 	e2; 
            }
            }  );
		return sEmpLoyee;
	}

	
	/**
	 * 实现说明：获取员工总条数 . <BR>
	 */
	@Override
	public int getEmpLoyeeCount() {
		int count = super.queryForInt("select count(*) from sys_EmpLoyee se where se.isdelete='2'");
		return count;
	}

	public List<User> getUserByEmpId(String empId){
		List<Map<String,Object>> list=super.queryForList("select * from sys_user where sysempid='"+empId+"'");
		List<User> returnList =new ArrayList<User>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			User u;
			try {
				u = (User) ECCBeanUtil.mapToBean(User.class, map);
				returnList.add(u);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}

}
