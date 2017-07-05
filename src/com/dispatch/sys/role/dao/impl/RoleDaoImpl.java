package com.dispatch.sys.role.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.Role;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.role.dao.IRoleDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.common.UUIDGenerator;

@SuppressWarnings("unchecked")
@Repository
public class RoleDaoImpl extends PageListJdbcTemplate implements IRoleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	//
   /*private	connectiontTest contemple = new connectiontTest();*/
	
	/**
	 * 实现说明： 获取角色列表. <BR>
	 * 
	 * @see com.ecc.sys.Role.dao.impl.RoleDaoImpl
	 * @Author: mijian <BR>
	 * @Datetime：2014年11月26日 上午11:42:39 <BR>
	 */
	@Override
	public Map getRoleList(Role Role,User user, int pageIndex, int pageSize) {
		
		String sql="SELECT T.ID,   T.PARENTID, "+
				"               T.ROLENAME, "+
				"               T.ROLEDESC, "+
				"               T.SEQ, "+
				"               T.ISDELETE, "+
				"               T.CREATETIME,(SELECT username FROM sys_user  WHERE id=t.createman) username "+
				"          FROM SYS_ROLE T "+
				"         WHERE  1 = 1 "+
				"           AND ISDELETE = '"+EnumUtil.PARAMETERSSTATUS2+"' ";
		
		if(Role.getRolename()!=null&&!Role.getRolename().equals("")){ 
		    sql = sql + " and Rolename like '%"+Role.getRolename()+"%'";
		 }
		if(!"admin".equals(user.getLoginName())){
			sql+="and T.CREATEMAN = '"+user.getId()+"' ";
		}
		return super.queryGridist(sql, "", pageIndex, pageSize);
	}
	
	
	private List query(Role Role ,User user,String tableName, int pageNo, int pageSize) {
		
	if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
	
		
		
		String coutSql= "select count(*) from  "+ tableName;
		int count=	jdbcTemplate.queryForInt( coutSql);
		
		
		int _preIndex = (pageNo - 1) * pageSize + 1;
		int _endIndex = pageNo * pageSize;
		
		String sql = "";
		if("admin".equals(user.getLoginName())){
			sql = "select d.*  from ( select t.*,   row_number() over(order by createtime desc)  AS ROW_INDEX from sys_Role t where 1=1 ";
				if(Role.getRolename()!=null&&!Role.getRolename().equals("")){ 
					sql = sql + " and Rolename like '%"+Role.getRolename()+"%'";
			    }
			    sql = sql + " and isdelete = '"+EnumUtil.PARAMETERSSTATUS2+"'";//
				
				sql = sql + ") d " + "WHERE d.ROW_INDEX >=" + _preIndex
						+ "   AND d.ROW_INDEX <=" + _endIndex;
				
		}else{
		sql = "select d.*  from ( select t.id,t.parentid,t.rolename,t.roledesc,t.seq,t.isdelete,t.createtime,(select loginname from sys_user u where u.id=t.createman) createman,   row_number() over(order by createtime desc)  AS ROW_INDEX from sys_Role t where t.createman  = '"+user.getId()+"' and 1=1  ";
		//角色姓名
	    if(Role.getRolename()!=null&&!Role.getRolename().equals("")){ 
	    sql = sql + " and Rolename like '%"+Role.getRolename()+"%'";
	    }
	    sql = sql + " and isdelete = '"+EnumUtil.PARAMETERSSTATUS2+"'";//
		
		sql = sql + ") d " + "WHERE d.ROW_INDEX >=" + _preIndex
				+ "   AND d.ROW_INDEX <=" + _endIndex;
		
		}
		
		return 	jdbcTemplate.queryForList(sql); 
	}



	/**
	 * 实现说明：保存Role对象 . <BR>
	 * 
	 * @see com.ecc.sys.Role.dao.impl.RoleDaoImpl
	 * @Author: mijian <BR>
	 * @Datetime：2014年11月26日 上午10:07:19 <BR>
	 */

	  
	@Override
	public int saveRole(Role r) {
		int n =0;
		 String uuid=UUIDGenerator.getUUID();
		  r.setId(uuid);
		if(r!=null){ 
			n =	jdbcTemplate.update("insert into SYS_ROLE(ID,ROLENAME,ROLEDESC,SEQ,IsDelete,CREATETIME,CREATEMAN) values(?,?,?,?,?,?,?)",
					new Object[] {r.getId(),r.getRolename(),r.getRoledesc(),r.getSeq(), r.getIsDelete(),r.getCreatetime(),r.getCreateman()
					});
		}else{
			return 0;
		}
	
		return n;
	}
	/**
	 * 实现说明：修改对象 . <BR>
	 * 
	 * @see com.ecc.sys.Role.dao.impl.RoleDaoImpl
	 * @Author: mijian <BR>
	 * @Datetime：2014年11月26日 上午10:08:30 <BR>
	 */
	@Override
	public int updateRole(Role r ) {
	
		Role Role=this.findRoleById(r.getId());
		Role.setRoledesc(r.getRoledesc());
		Role.setRolename(r.getRolename());
		Role.setId(r.getId());
         /*更新实现 */ 
		
        int i = jdbcTemplate  
                .update(  
                        "update sys_Role set  ROLENAME=?,ROLEDESC=?"
					+ " where id=?",
					new Object[] {Role.getRolename(),Role.getRoledesc(),Role.getId()
                        }); 
       
        return i;  
	}
	/**
	 * 实现说明：删除角色 . <BR>
	 * 
	 * @see com.ecc.sys.Role.dao.impl.RoleDaoImpl
	 * @Author: mijian <BR>
	 * @Datetime：2014年11月26日 上午10:09:09 <BR>
	 */
	@Override
	public int deleteRole(Role r){
		 int i =0;
		if(r!=null){
         i = jdbcTemplate.update("update sys_Role set isdelete='"+EnumUtil.PARAMETERSSTATUS1+"' where id=? ",  //isdelete='0'为不展示数据
		                new Object[] { r.getId()}); 
		}else{ 
			return 0;
		}
		
		return i;
	}

	/**
	 * 实现说明通过id 查找相应的角色对象： . <BR>
	 * 
	 * @see com.ecc.sys.Role.dao.impl.RoleDaoImpl
	 * @Author: mijian <BR>
	 * @Datetime：2014年11月26日 上午10:09:32 <BR>
	 */
	@Override
	public Role findRoleById(String id) {
		Role sRole =null;
		sRole= (Role)jdbcTemplate.queryForObject("select * from sys_Role where id=?", new Object[] { id }, new RowMapper() {  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException {  
            	Role role1=new Role();
            	role1.setId(rs.getString("id"));   
            	role1.setRolename(rs.getString("ROLENAME"));
            	role1.setRoledesc(rs.getString("ROLEDESC"));
            	role1.setCreatetime(rs.getString("CREATETIME"));
            	role1.setCreateman(rs.getString("CREATEMAN"));
               return 	role1; 
            }
            }  );
		return sRole;
	}

	
	/**
	 * 实现说明：获取角色总条数 . <BR>
	 * @see com.ecc.sys.Role.dao.impl.RoleDaoImpl
	 * @Author: mijian <BR>
	 * @Datetime：2014年11月27日 上午9:05:46 <BR>
	 */
	@Override
	public int getRoleCount() {
		int count = jdbcTemplate.queryForInt("select count(*) from sys_Role sr where sr.isdelete='"+EnumUtil.PARAMETERSSTATUS2+"'");
		return count;
	}
	@Override
	public Map getRoleListByUser( Role role,Org o2,User user, int pageIndex,
			int pagesize,String userId) {
		String sql = " select t.*  from sys_Role t where 1=1 and t.isdelete='"+EnumUtil.PARAMETERSSTATUS2+"' and  t.id not in (select ur.roleid from sys_userrolerelation ur where ur.userid ='"+userId+"') and t.createman = '"+user.getId()+"'";
		String conditions=" ";
	    if(role.getRolename()!=null&&!role.getRolename().equals("")){ 
	    	conditions = conditions + " and rolename like '%"+role.getRolename()+"%'";
	    }
		return 	super.queryGridist(sql,conditions,pageIndex, pagesize); 
	}
	@Override
	public Map getRoleListByUserRight( Role role, int pageIndex,
			int pagesize,String userId) {
		// TODO Auto-generated method stub
		String sql = " select t.*  from sys_Role t where 1=1 and t.isdelete='"+EnumUtil.PARAMETERSSTATUS2+"' and t.id  in (select ur.roleid from sys_userrolerelation ur where ur.userid ='"+userId+"') ";
		String conditions=" ";
	    if(role.getRolename()!=null&&!role.getRolename().equals("")){ 
	    	conditions = conditions + " and rolename like '%"+role.getRolename()+"%'";
	    }
		return 	super.queryGridist(sql,conditions,pageIndex, pagesize); 
	}
	
	public List getRoleRightByUserId(String userId){
		String sql="select  distinct t.rightid  from sys_rolerightrelation t where 1=1  and t.roleid  in (select ur.roleid from sys_userrolerelation ur where ur.userid ='"+userId+"')";
		return super.queryForList(sql);
	}
	@Override
	public void saveUserRole(String userId, String roleIds) {
		// TODO Auto-generated method stub
		String[] ids=roleIds.split(",");
		for(int i=0;i<ids.length;i++){
			String uuid=UUIDGenerator.getUUID();
			String saveSql="INSERT INTO SYS_USERROLERELATION (id,roleid,userid) values (?,?,?)";
			super.update(saveSql,new Object[]{uuid,ids[i],userId});
		} 
	}
	@Override
	public void deleteUserRole(String userId, String roleIds) {
		// TODO Auto-generated method stub
		String[] ids=roleIds.split(",");
		for(int i=0;i<ids.length;i++){
			String saveSql="delete  SYS_USERROLERELATION where roleid=? and userid=?";
			super.update(saveSql,new Object[]{ids[i],userId});
		} 
	}

}
