package com.dispatch.sys.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.user.dao.IUserDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;
@Repository
public class UserDaoImpl extends PageListJdbcTemplate implements IUserDao {

	private static User user = new User();
	
	/**
	 * 实现说明：获取list 列表 . <BR>
	 */ 
	public Map getUserList(User user,User u,Org o2,int pageNo,int pageSize) {
		String sql = "";
		String conditions=" ";
		if("admin".equals(u.getLoginName())){
			sql = "select u.ID,u.USERNAME,u.LOGINNAME,(select d.dicname from sys_datadic d where " +
				  " d.keyname = u.USERSTAT and d.typecode = 'USERSTATE') USERSTAT,u.MOBILE,u.MAIL,u.LASTLOGINTIME," +
				  " u.LOGINCOUNT,u.CREATETIME,(select t.loginname from sys_user t where t.id=u.CREATEMAN) CREATEMAN from sys_user u where 1=1 ";
		}else{
		sql = "select u.ID,u.USERNAME,u.LOGINNAME,(select d.dicname from sys_datadic d where d.keyname = u.USERSTAT and " +
			  " d.typecode = 'USERSTATE') USERSTAT,u.MOBILE,u.MAIL,u.LASTLOGINTIME,u.LOGINCOUNT,u.CREATETIME," +
			  " (select t.loginname from sys_user t where t.id=u.CREATEMAN) CREATEMAN from sys_user u,sys_employee e" +
			  "  where u.sysempid=e.id and e.orgcode in (select o.id from sys_org o start with o.id='"+o2.getUnitParentId()+"' " +
			  "  connect by prior o.id=o.parentid) and 1=1 ";
		}
		//拼接 条件语句
		//用户姓名
	    if(user.getUserName()!=null&&!user.getUserName().equals("")){ 
	    	conditions = conditions + " and username like '%"+user.getUserName()+"%'";
	    }
	    //帐    号 
	    if(user.getLoginName()!=null&&!user.getLoginName().equals("")){
	    	conditions = conditions + " and loginname like '%"+user.getLoginName()+"%'";
		 }
	    //用户状态  
	    if(user.getUserStat()!=null&&!user.getUserStat().equals("")){
	    	conditions = conditions + " and userstat ='"+user.getUserStat()+"'";
		 }
	    conditions=conditions+" order by u.createTime desc";
		
		return 	super.queryGridist(sql,conditions,pageNo, pageSize);   
	}

	/**
	 * 实现说明：保存user对象 . <BR>
	 */
	@Override
	public int saveUser(User u) {
		int n =0;
	    String uuid=UUIDGenerator.getUUID();
	    u.setId(uuid);
		n =	super.update("INSERT INTO sys_user (id,sysEmpid,LOGINNAME,PASSWORD"
				+ ",USERSTAT,USERNAME,MOBILE,MAIL,LOGINTIME,LASTLOGINTIME,LOGINCOUNT"
				+ ",CREATETIME,CREATEMAN)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] {  u.getId(),u.getSysEmpId(),u.getLoginName(),u.getPassWord()
						       ,u.getUserStat(),u.getUserName(),u.getMobile(),u.getMail()
						       ,u.getLoginTime(),u.getLastLoginTime(),u.getLoginCount()
						       ,u.getCreateTime(),u.getCreateMan()
						       
				});
		return n;
	}

	 
	/**
	 * 实现说明：修改对象 . <BR>
	 */
	@Override
	public int updateUser(User u ) {
		User user=this.findUserById(u.getId());
		user.setMail(u.getMail()); 
		user.setMobile(u.getMobile());
		user.setUserStat(u.getUserStat());
		user.setId(u.getId());
		String sql = "update sys_user set  USERSTAT='"+user.getUserStat()+"',MOBILE='"+user.getMobile()+"',MAIL='"+user.getMail()+"' where id='"+user.getId()+"'";
        /* 更新实现 */  
        int i = super.update(sql,
					new Object[] {});  
       
        return i;  
	}
	
	/**
	 * 方法说明：  修改密码. <BR>
	 */
	public void updatePassWord(User updUser){
		super.update("update sys_user set password=? where id=?",new Object[]{updUser.getPassWord(),updUser.getId()});
	}
	
	/**
	 * 实现说明：删除对象 . <BR>
	 */
	@Override
	public int deleteUser(User u){
		 int i =0;
		if(u!=null){
            i = super.update("update sys_user set isdelete='0'  where id=? ",  new Object[] { u.getId()}); 
		}else{ 
			return 0;
		}
		
		return i;
	}

	/**
	 * 实现说明通过id 查找相应的用户对象： . <BR>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User findUserById(String id) {
		User suser =null;
		suser= (User)super.queryForObject("select ID,USERNAME,LOGINNAME,userStat,mobile,mail from sys_User where id=?", new Object[] { id }, new RowMapper() {  
            @Override  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException {  
               try {
				ECCBeanUtil.resultSetToBean(rs, user); 
			} catch (Exception e) {
				e.printStackTrace();
			}
               return 	user; 
            }
            }  );
		return suser;
	}

	
	/**
	 * 实现说明：获取用户总条数 . <BR>
	 */
	@Override
	public int getUserCount() {
		int count = super.queryForInt("select count(*) from sys_user");
		return count;
	}
	
	@Override
	public int enableUser(User u) {
		int i =0;
		if(u!=null){
         i = super.update("update sys_user set userStat='"+EnumUtil.USERSTATE1+"'  where id=? ",  
		                new Object[] { u.getId()}); 
		}else{ 
			return 0;
		}
		return i;
	}
	
	@Override
	public int disableUser(User u) {
		 int i =0;
			if(u!=null){
	         i = super.update("update sys_user set userStat='"+EnumUtil.USERSTATE2+"'  where id=? ",  
			                new Object[] { u.getId()}); 
			}else{ 
				return 0;
			}
			return i;
	}
	
	@Override
	public User userLogin(User user) {
		User suser = null;
		List<User> returnlist = new ArrayList<User>();
		List<Map<String, Object>> list = super.queryForList(
				"select * from sys_User where loginName=? and password=? ",
				new Object[] { user.getLoginName(), user.getPassWord() });
		for (Map<String, Object> map : list) {
			Map<String, Object> m = new HashMap<String, Object>();
			user.setLoginName(map.get("LOGINNAME").toString());
			user.setUserStat(map.get("USERSTAT").toString());
			user.setUserName(map.get("USERNAME").toString());
			user.setMobile(map.get("MOBILE") == null ? "" : map.get("MOBILE")
					.toString());
			user.setMail(map.get("MAIL") == null ? "" : map.get("MAIL")
					.toString());
			user.setLoginTime(map.get("LOGINTIME") == null ? "" : map.get(
					"LOGINTIME").toString());
			user.setLastLoginTime(map.get("LASTLOGINTIME") == null ? "" : map
					.get("LASTLOGINTIME").toString());
			user.setLoginCount(map.get("LOGINCOUNT") == null ? 0 : new Integer(
					map.get("LOGINCOUNT").toString()));
			user.setCreateTime(map.get("CREATETIME") == null ? "" : map.get(
					"CREATETIME").toString());
			user.setCreateMan(map.get("CREATRMAN") == null ? "" : map.get(
					"CREATRMAN").toString());
			user.setId(map.get("ID").toString());
			user.setSysEmpId(map.get("SYSEMPID") == null ? "" : map.get(
					"SYSEMPID").toString());
			returnlist.add(user);
		}
		if (returnlist.size() > 0) {
			suser = returnlist.get(0);
		}
		if (returnlist.size() > 0 && suser != null) {
			String sql = "SELECT su.id,SU.LOGINNAME, SU.LOGINNAME, SE.ORGCODE, SO.ORGNAME, SO.ISDELETE "
					+ "  FROM SYS_USER SU, SYS_EMPLOYEE SE, SYS_ORG SO "
					+ " WHERE SE.ID = SU.SYSEMPID "
					+ "   AND SO.ID = SE.ORGCODE "
					+ "   AND su.id='"
					+ suser.getId()
					+ "' "
					+ "   AND so.isdelete='1' "
					+ "   AND se.isdelete='2' ";
			List listUser = super.queryForList(sql);
			suser.setUserStat(listUser.size() == 0 ? "3" : suser.getUserStat());
		}

		return suser;  
	}
	
	@Override
	public int checkLoginName(User user) {
		int count = super.queryForInt("select count(*) from sys_user where loginname='"+user.getLoginName()+"'");
		return count;
	}

	/**
	 * 实现说明：查询员工是否存在  . <BR>
	 */
	@Override
	public int findEmpCount(String sysId) {
		String sql = "select count(1) from SYS_EMPLOYEE where isdelete='"+EnumUtil.RIGHTSTAT2+"' and id='"+sysId+"'";
		return super.queryForInt(sql);
	}

	@Override
	public void saveUserCount(User user) {
		 super.update("update sys_user set logintime=(select  to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') from dual),LastLoginTime=?,LoginCount=LoginCount+1 where id=? ",  
		 new Object[] {user.getLoginTime(),user.getId()}); 
	}

	@Override
	public List getDataBaseColumnList() {
		String sql="SELECT table_name,Column_name,data_type FROM USER_TAB_COLS ";
		return super.queryForList(sql);
	}

	@Override
	public List getDataBaseGroupTable() {
		String sql="SELECT table_name FROM USER_TAB_COLS GROUP BY table_name ORDER BY table_name";
		return super.queryForList(sql);
	}
	
	/**
	 * 实现说明：根据用户主键查询用户名和组织名 . <BR>
	 */
	@Override
	public List findNamesByOid(String userid) {
		String sql = "SELECT t.username,so.orgname FROM SYS_USER T, SYS_EMPLOYEE SE, SYS_ORG SO WHERE t.sysempid=se.id AND so.id=se.orgcode AND t.id='"+userid+"'";
		return super.queryForList(sql);
	}
	
	public List getUnitList(Org org) {
		String sql="";
		if(org.getUnitParentId().equals("1")){
			sql="select t.id ,t.unittype,t.unitname from ecc_ieu_unitinfo t where t.unittype='1' and t.maintype='1' order by t.seq  ";
		}else{
			if(org.getManagerType().equals("1")){
				sql="select t.id ,t.unittype,CASE WHEN t.shortname IS NOT NULL THEN t.shortname ELSE t.unitname END  unitname from ecc_ieu_unitinfo t where t.unittype='9' and t.unitid='"+org.getUnitParentId()+"' order by t.seq ";
			}else{
				sql="select t.id,t.unittype ,CASE WHEN t.shortname IS NOT NULL THEN t.shortname ELSE t.unitname END  unitname from ecc_ieu_unitinfo t where t.id='"+org.getId()+"' order by t.seq ";
			}
		}
		
		return super.queryForList(sql);
	}

}
