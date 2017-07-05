package com.dispatch.sys.right.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.right.dao.IRightDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.frames.util.CreateSqlTool;
import com.raq.common.Logger;
import com.util.EnumUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;

/**
 * 功能描述：  权限菜单管理数据信息.  <BR>
 */
@Repository
public class RightDaoImpl extends PageListJdbcTemplate implements IRightDao  {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static Right right = new Right();
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * 实现说明：保存权限信息 . <BR>
	 */
	@Override
	public boolean addRight(Right r) {
		boolean bool = false;
		  String uuid=UUIDGenerator.getUUID();
		  r.setId(uuid);
		  try {
			 String sqlHI = CreateSqlTool.genInsertSql("com.dispatch.sys.bean.Right",r,"sys_right");
			super.update(sqlHI);
			bool = true;
		} catch (Exception e) {
			Logger.error(e.toString(), e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 实现说明：删除权限信息 . <BR>
	 */
	@Override
	public int deleteRight(Right right) {
		int i =0;
		if(right!=null){
		 String sql="select ID from sys_right t start with t.id='"+right.getId()+"' connect by  prior t.id= t.parentid";
         List<Map<String,Object>> list =jdbcTemplate.queryForList(sql);
         String id="";
         for(int a=0;a<list.size();a++){ 
        	 id+="'"+list.get(a).get("ID")+"'";
        	 if(a<list.size()-1){
        		 id+=",";
        	 }
        	
         }
         sql="update sys_right set isdelete='"+EnumUtil.RIGHTSTAT1+"'  where id in ("+id+")";
         i = jdbcTemplate.update(sql); 
		}else{ 
			return 0;
		}
		
		return i;
	}

	
	/**
	 * 实现说明：查询子权限条数 . <BR>
	 */
	@Override
	public int getChildrenCount(String id) {
		String sql ="select count(1) from  sys_right where PARENTID='"+id+"'and isdelete =2";
		int ChildrenCount =jdbcTemplate.queryForInt(sql);
		 
		return ChildrenCount;
	}
	
	public int getChildrenCountByUser(String id,String rightIds) {
		String sql ="select count(1) from  sys_right where PARENTID='"+id+"'and isdelete =2 and id in ("+rightIds+")";
		int ChildrenCount =jdbcTemplate.queryForInt(sql);
		return ChildrenCount;
	}
	@Override
	public List<Map<String, Object>> getParentRightList(Long id) {
		return null;
	}
	
	/**
	 * 实现说明：权限信息查询 . <BR>
	 */
	@Override
	public List<Map<String, Object>> getRightTreeData(String pid) {
		List<Map<String, Object>>  list;
		   if("0".equals(pid)){ 
			    list =jdbcTemplate.queryForList("SELECT id,RIGHTNAME FROM sys_right where parentid ='"+pid+"' and isdelete='"+EnumUtil.RIGHTSTAT2+"' order by seq");
		   }else{
			    list =jdbcTemplate.queryForList("SELECT id,RIGHTNAME FROM sys_right where parentid ='"+pid+"' and isdelete='"+EnumUtil.RIGHTSTAT2+"' order by seq");
		   }
			return list;
	}
	@Override
	public List<Map<String, Object>> getRightTreeDataByUser(String pid,String rightids) {
		List<Map<String, Object>>  list;
		   if("0".equals(pid)){ 
			    list =jdbcTemplate.queryForList("SELECT id,RIGHTNAME,RIGHTURL FROM sys_right where parentid ='"+pid+"' and isdelete='"+EnumUtil.RIGHTSTAT2+"' and id in ("+rightids+") order by seq");
		   }else{
			    list =jdbcTemplate.queryForList("SELECT id,RIGHTNAME,RIGHTURL FROM sys_right where parentid ='"+pid+"' and isdelete='"+EnumUtil.RIGHTSTAT2+"' and id in ("+rightids+")  order by seq");
		   }
			return list;
	}
	@Override
	public Right getRightnByRightId(String rightId) {
		return null;
	}
	/**
	 * 实现说明：修改权限信息 . <BR>
	 */
	@Override
	public boolean editRight(Right rg) {
		boolean bool = false;
		try {
			String sqlHU = CreateSqlTool.genUpdateSql("com.dispatch.sys.bean.Right",rg,"sys_right"," where id='"+rg.getId()+"'");
			super.update(sqlHU);
			bool = true;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			bool = false;
		}
       
        return bool;  
	}
	/**
	 * 实现说明：通过id查询权限信息 . <BR>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Right findRightById(String id) {
		Right rsRight =null;
		rsRight= (Right)jdbcTemplate.queryForObject("select t.*,(SELECT COUNT(1) FROM Sys_Right WHERE parentid=t.id) childnums from sys_right t where id=? and isdelete=2", new Object[] { id }, new RowMapper() {  
            @Override  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException { 
            	  try {
        				ECCBeanUtil.resultSetToBean(rs, right);
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
               return 	right; 
            }
            }  );
		return rsRight;
	}

	/**
	 * 查询是否有子信息
	 */
	@Override
	public int getChildrenRight(String id) {
		int count = jdbcTemplate.queryForInt("select count(*) from sys_right se where se.isdelete='2' and se.parentId='"+id+"'");
		return count;
	}
	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public List<Map<String, Object>> getAllRightByUser(String userId) {
		// TODO Auto-generated method stub
		String sql="select distinct rightid from ( "+
					"select rightid from sys_userrightrelation where userid='"+userId+"' "+
					"union all "+
					"select rightid from sys_rolerightrelation where roleid in (select roleid from sys_userrolerelation where userid='"+userId+"') "+
					"union all "+
					"select rightid from sys_orgright where orgid in (select orgcode from sys_orguser where userid ='"+userId+"') "+
					"union all "+
					"select id as rightid from sys_right  where rightstat='"+EnumUtil.RIGHTSTAT1+"')";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 实现说明： . <BR>
	 */
	public List getSystemList(String userid){
		String sql="select * from  sys_right T where t.isdelete='2' AND t.righttype='3' ORDER BY seq DESC  ";
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 实现说明： . <BR>
	 */
	public List<Right> getSystemMenuList(User user,String id){
		
		List<Right> rightList=new  ArrayList<Right>();
		List list= jdbcTemplate.queryForList(this.getRightListSql(user,id));
		for(int i=0;i<list.size();i++){
			Right   right=new Right();
			HashMap map=(HashMap)list.get(i);
			right.setId(map.get("ID")+"");
			right.setRightname(map.get("RIGHTNAME")+"");
			right.setRighturl(map.get("RIGHTURL")+"");
			right.setTreeUrl(map.get("TREEURL")+"");
			right.setParentid(map.get("PARENTID")+"");
			boolean has=Integer.parseInt(map.get("NUMS")+"")>0?true:false;
			right.setHasSubRight(has);
			right.setPicUrl(map.get("PICURL")+"");
			if(has){
				right.setSubRight(jdbcTemplate.queryForList(this.getRightListSql(user,right.getId())));
			}
			rightList.add(right);
		}
		
		return rightList;
	}
	/**
	 * 方法说明： . <BR>
	 */
	public String  getRightListSql(User user,String id){
		String sql="SELECT T.PICURL,t.treeurl,t.id,t.rightname,(SELECT COUNT(1) FROM sys_right s WHERE s.parentid=t.id) nums ,t.righturl,T.parentid,su.id fid "+
				"  FROM SYS_RIGHT  t , (SELECT *  FROM SYS_USERRIGHTCOLLECTION WHERE USERID = '"+user.getId()+"')  su "+
				" WHERE t.ID IN "+
				"       (SELECT DISTINCT RIGHTID "+
				"          FROM (SELECT RIGHTID "+
				"                  FROM SYS_USERRIGHTRELATION "+
				"                 WHERE USERID = '"+user.getId()+"' "+
				"                UNION ALL "+
				"                SELECT RIGHTID "+
				"                  FROM SYS_ROLERIGHTRELATION "+
				"                 WHERE ROLEID IN "+
				"                       (SELECT ROLEID "+
				"                          FROM SYS_USERROLERELATION "+
				"                         WHERE USERID = '"+user.getId()+"') "+
				"                UNION ALL "+
				"                SELECT RIGHTID "+
				"                  FROM SYS_ORGRIGHT "+
				"                 WHERE ORGID IN "+
				"                       (SELECT ORGCODE "+
				"                          FROM SYS_ORGUSER "+
				"                         WHERE USERID = '"+user.getId()+"') "+
				"                UNION ALL "+
				"                SELECT ID AS RIGHTID "+
				"                  FROM SYS_RIGHT "+
				"                 WHERE RIGHTSTAT = '"+EnumUtil.RIGHTSTAT1+"')) "+
				"   AND ISDELETE = '2' "+
				"   AND PARENTID = '"+id+"' and t.id=su.rightid(+) ORDER BY t.seq ";
		if(user.getLoginName().equals("admin")){
			sql="SELECT t.id,t.rightname,(SELECT COUNT(1) FROM sys_right s WHERE s.parentid=t.id) nums ,t.righturl from sys_right t " +
					" where PARENTID = '"+id+"' ORDER BY t.seq ";
		}
		return sql;
	}

	/**
	 * 实现说明：查询符合条件的权限菜单树 . <BR>
	 */
	@Override
	public List<Map<String, Object>> selectRightTreeData(String rightName) {
		String sql = "select id,rightName from SYS_RIGHT where 1=1 ";
		if(rightName!=null&&!"".equals(rightName)){
			sql += " and rightName like '%"+rightName+"%' ";
		}
		sql += " order by seq";
		List<Map<String, Object>>  list =jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public List userFavorite(User user) {
		String sql="select rightId ,sr.rightname,sr.righturl,nvl(sr.treeurl,'') treeurl from sys_UserRightCollection su,Sys_Right sr WHERE su.rightid=sr.id  "+
				"AND su.userid='"+user.getId()+"' ";

		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 实现说明： . <BR>
	 */
	public List selectRightByPid(User user,String pid){
		List  list =jdbcTemplate.queryForList("SELECT id,rightname as name,SEQ FROM sys_right where parentid ='"+pid+"' and isdelete='"+EnumUtil.RIGHTSTAT2+"' order by seq");
		return list;
	}
	/**
	 * 实现说明： . <BR>
	 */
	public void updateRightSeq(String ids [],String tableName){
		
		for(int i=0;i<ids.length;i++){
			String sql="update "+tableName+" set seq="+i+" where id='"+ids[i]+"'";
			jdbcTemplate.execute(sql);
		}
		
	}
}
