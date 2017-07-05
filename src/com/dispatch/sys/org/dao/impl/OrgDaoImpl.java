package com.dispatch.sys.org.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.org.dao.IOrgDao;
import com.dispatch.unit.bean.UnitInfo;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;
/**
 * 功能描述： 机构数据信息 .  <BR>
 */

@Repository
public class OrgDaoImpl extends PageListJdbcTemplate implements IOrgDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 实现说明：组织机构查询  . <BR>
	 */
	@Override
	public List<Map<String, Object>>  getOrgTreeData(Org org,String t){
		List<Map<String, Object>>  list;
		String sql="SELECT t.id,t.ORGNAME,t.ISDELETE,t.ORGTYPE,(SELECT COUNT(1) FROM sys_org so WHERE so.parentid=t.id ) nums  FROM SYS_ORG t  where 1=1 ";
		   if("0".equals(org.getId()) &&!"1".equals(t)){ 
			    sql+=" and parentid ='0'   ";
			}else  if("1".equals(t)){
			   sql+="  and parentid ='"+org.getId()+"'    ";
		   }else{
			   sql+="  and  id ='"+org.getId()+"'   ";
		   }
		   if(org.getIsDelete()!=null&&!org.getIsDelete().equals("")){
			   sql+="  and  isdelete='"+org.getIsDelete()+"'";
		   }
		   
		   sql+=" order by seq";
		   
		   return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 实现说明： 获得某组织机构直属下级组织机构. <BR>
	 */
	@Override
	public int getChildrenCount(String id) {
		
		String sql ="select count(1) from  SYS_ORG where PARENTID='"+id+"'";
		int ChildrenCount =jdbcTemplate.queryForInt(sql);
		 
		return ChildrenCount;
	}

	
	/**
	 * 实现说明： 根据orgId取得结果集 . <BR>
	 */
	@Override
	public Org getOrgnByOrgId(String orgId) {
		Org sOrg =null;
				sOrg= (Org)jdbcTemplate.queryForObject("select * from sys_ORG where id=?", new Object[] { orgId }, new RowMapper() {  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException {  
            	Org org2=new Org();
            	org2.setId(rs.getString("id"));
            	org2.setOrgName(rs.getString("ORGNAME"));
            	org2.setOrgType(rs.getString("ORGTYPE"));
            	org2.setOrgCode(rs.getString("ORGCODE"));
            	org2.setParentId(rs.getString("PARENTID"));
            	org2.setCreateMan(rs.getString("CREATEMAN"));//
            	org2.setCreateTime(rs.getString("CREATETIME"));
            	org2.setCompanyType(rs.getString("COMPANYTYPE"));
            	org2.setIsDelete(rs.getString("ISDELETE"));
               return 	org2; 
            }
            }  );
		return sOrg;
	}

	/**
	 * 新增管理权限
	 */
	@Override
	public Integer addOrg( Org r, HttpServletRequest request) {
		Map<String,Object> sessionMap=(Map<String, Object>) request.getSession().getAttribute("ECCSESSIONMAP");
		Org org=(Org)sessionMap.get("ECCORG");
		User user = (User) request.getSession().getAttribute("ECCUSER");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String createDate = df.format(new Date());// new Date()为获取当前系统时间
		UnitInfo unitInfo = new UnitInfo();
		int n =0;
		String uuid=UUIDGenerator.getUUID();
		String maxSql = "select max(To_number(orgCode,'99999')) from sys_org";
		int orgcode = jdbcTemplate.queryForInt(maxSql);
		String maxUnitSql = "select max(To_number(unitcode,'99999')) from ecc_ieu_unitinfo";
		int unitcode = jdbcTemplate.queryForInt(maxUnitSql);
		String code = "";
		if(orgcode-unitcode>0){
			code = orgcode+1+"";
		}else{
			code = unitcode+1+"";
		}
		r.setOrgCode(code);
		n =	jdbcTemplate.update("insert into sys_org(ID,parentId,orgCode,orgName,sortName,jianpin,orgType,managerType,seq,isDelete,groupType,createTime,createMan,companyType)"
		+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
		new Object[] { r.getId(),r.getParentId(),r.getOrgCode(),r.getOrgName(),r.getSortName()
				       ,r.getJianPin(),r.getOrgType(),r.getManagerType(),r.getSeq(),r.getIsDelete(),r.getGroupType(),r.getCreateTime(),r.getCreateMan(),r.getCompanyType()
				});
		if(r.getManagerType().equals(EnumUtil.OWNERTYPE3)){
			jdbcTemplate.update("insert into ecc_coll_convertunit (id,CONVERTTYPE,CONVERTID,ISUSABLE,COLLNAME,CREATEUSERID,CREATEUSER,CREATEUSERDEPARTMENTID,CREATEUSERDEPARTMENT,CREATEDATE) " +
					" values (?,?,?,?,?,?,?,?,?,?) ",
					new Object[] {
					uuid,EnumUtil.CONVERTTYPE1,r.getId(),EnumUtil.PARAMETERSSTATUS1,r.getOrgName(),user.getId(),user.getUserName(),org.getId(),org.getOrgName(),r.getCreateTime()
			});
		}
		if(r.getOrgType().equals("1")){
			if(user != null){
				unitInfo.setCreateUserID(user.getId());
				unitInfo.setCreateUser(user.getUserName());
			}
			if(org != null){
				unitInfo.setCreateUserDepartmentID(org.getId());
				unitInfo.setCreateUserDepartment(org.getOrgName());
			}else{
				unitInfo.setCreateUserDepartmentID("0");
				unitInfo.setCreateUserDepartment("0");
			}
			if(r.getIsDelete()=="2"){
				unitInfo.setStatus(EnumUtil.PARAMETERSSTATUS2);
			}else{
				unitInfo.setStatus(EnumUtil.PARAMETERSSTATUS1);
			}
			if(r.getParentId()=="2"){
				unitInfo.setUnitId("0");
			}else{
				unitInfo.setUnitId(r.getParentId());
			}
			jdbcTemplate.update("insert into ecc_ieu_unitinfo(ID,unitId,unitCode,unitName,shortName,jianpin,orgId,orgName,seq,status,companyType,unitType,onwertype,groupType,createUserid,createUser,createUserDepartmentId,createUserDepartment,createDate)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { r.getId(),unitInfo.getUnitId(),r.getOrgCode(),r.getOrgName(),r.getSortName()
							       ,r.getJianPin(),r.getId(),r.getOrgName(),r.getSeq(),unitInfo.getStatus(),r.getCompanyType(),r.getManagerType(),r.getManagerType(),r.getGroupType()
							       ,unitInfo.getCreateUserID(),unitInfo.getCreateUser(),unitInfo.getCreateUserDepartmentID(),unitInfo.getCreateUserDepartment(),createDate
							});
		}
		return n;
	}
	/**
	 * 实现说明： . <BR>
	 */
	public int checkOrgCode(Org org){
		String sql="select count(1) from  SYS_ORG where orgcode='"+org.getOrgCode()+"' ";
		if(!org.getId().equals("")){
			sql += " and id not in '"+org.getId()+"'";
		}
		int count=jdbcTemplate.queryForInt(sql);
		return count;
		
	}
	
	/**
	 * 实现说明： . <BR>
	 */
	public int checkJianPin(Org org){
		String sql="select count(1) from  SYS_ORG where jianpin='"+org.getJianPin()+"' ";
		if(!org.getId().equals("")){
			sql += " and id not in '"+org.getId()+"'";
		}
		int count=jdbcTemplate.queryForInt(sql);
		return count;
	}
	/**
	 * 实现说明： . <BR>
	 */
	@Override
    public Integer editOrg(Org o, HttpServletRequest request){
		Org orgs=o;
    	int i = 0;
    	i = jdbcTemplate.update(  "update sys_org set orgName=?,sortName=?,jianpin=?,orgType=?,seq=?,ManagerType=?,createMan=?,companyType=?,groupType=? where id=?",
				new Object[] { orgs.getOrgName(),orgs.getSortName(),orgs.getJianPin(),orgs.getOrgType(),orgs.getSeq(),orgs.getManagerType(),orgs.getCreateMan(),orgs.getCompanyType(),o.getGroupType(),orgs.getId()
                    });  
    	String nameSql = "update ecc_coll_convertunit set COLLNAME='"+orgs.getOrgName()+"' where CONVERTTYPE='1' and ISUSABLE='1' and CONVERTID='"+orgs.getId()+"'";
    	super.execute(nameSql);
		if(o.getOrgType().equals(EnumUtil.ORGTYPE1)){
			String sql1 = "select count(*) from ecc_ieu_unitinfo where id='"+orgs.getId()+"'";
			int j = jdbcTemplate.queryForInt(sql1);
			if(j==0){
				Map<String,Object> sessionMap=(Map<String, Object>) request.getSession().getAttribute("ECCSESSIONMAP");
				Org org=(Org)sessionMap.get("ECCORG");
				User user = (User) request.getSession().getAttribute("ECCUSER");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String createDate = df.format(new Date());// new Date()为获取当前系统时间
				UnitInfo unitInfo = new UnitInfo();
				if(user != null){
					unitInfo.setCreateUserID(user.getId());
					unitInfo.setCreateUser(user.getUserName());
				}
				if(org != null){
					unitInfo.setCreateUserDepartmentID(org.getId());
					unitInfo.setCreateUserDepartment(org.getOrgName());
				}else{
					unitInfo.setCreateUserDepartmentID("0");
					unitInfo.setCreateUserDepartment("0");
				}
				String sql2 = " insert into ecc_ieu_unitinfo(ID,unitId,unitCode,unitName,shortName,jianpin,orgId,orgName,seq,status,companyType,unitType,onwertype,groupType,createUserid,createUser,createUserDepartmentId,createUserDepartment,createDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				jdbcTemplate.update(sql2, new Object[] { o.getId(),orgs.getParentId(),o.getOrgCode(),o.getOrgName(),o.getSortName()
						       ,o.getJianPin(),o.getId(),o.getOrgName(),o.getSeq(),orgs.getIsDelete(),o.getCompanyType(),orgs.getManagerType(),orgs.getManagerType(),o.getGroupType()
						       ,unitInfo.getCreateUserID(),unitInfo.getCreateUser(),unitInfo.getCreateUserDepartmentID(),unitInfo.getCreateUserDepartment(),createDate});
			}else{
				String sql="update ECC_IEU_UNITINFO set unitname=?,shortName=?,jianpin=?,companyType=?,unitType=?,onwertype=?,seq=?,groupType=? where id=? ";
				int k=jdbcTemplate.update(sql,new Object[]{orgs.getOrgName(),o.getSortName(),o.getJianPin(),orgs.getCompanyType(),orgs.getManagerType(),orgs.getManagerType(),orgs.getSeq(),o.getGroupType(),orgs.getId()});
			}
		}else{
			String sql = " update ecc_ieu_unitinfo set status='"+EnumUtil.PARAMETERSSTATUS2+"' where id='"+o.getId()+"' ";
			int k=jdbcTemplate.update(sql);
		}
    	return i;
    }
	/**
	 * 实现说明：组织机构编辑 . <BR>
	 */
	public Integer editOrg1(Org o, HttpServletRequest request) {
		Org orgs=this.findOrgById(o.getId());   //o.getId()
		
		orgs.setOrgCode(o.getOrgCode());
		orgs.setOrgName(o.getOrgName());
		orgs.setSortName(o.getSortName());
		orgs.setJianPin(o.getJianPin());
		orgs.setOrgType(o.getOrgType());
		orgs.setSeq(o.getSeq());
		orgs.setCreateTime(o.getCreateTime());
		orgs.setCreateMan(o.getCreateMan());
		orgs.setId(o.getId());
		orgs.setCompanyType(o.getCompanyType());
		int i = 0;
		if(orgs.getManagerType().equals(o.getManagerType())){
			i = jdbcTemplate.update(  "update sys_org set orgcode=?,orgName=?,sortName=?,jianpin=?,orgType=?,seq=?,createMan=?,companyType=?,groupType=? where id=?",
					new Object[] { o.getOrgCode(),orgs.getOrgName(),orgs.getSortName(),orgs.getJianPin(),orgs.getOrgType(),orgs.getSeq()
        		,orgs.getCreateMan(),orgs.getCompanyType(),o.getGroupType(),orgs.getId()
                        });  
			if(o.getOrgType().equals(EnumUtil.ORGTYPE1)){
				String sql1 = "select count(*) from ecc_ieu_unitinfo where id='"+orgs.getId()+"'";
				int j = jdbcTemplate.queryForInt(sql1);
				if(j==0){
					Map<String,Object> sessionMap=(Map<String, Object>) request.getSession().getAttribute("ECCSESSIONMAP");
					Org org=(Org)sessionMap.get("ECCORG");
					User user = (User) request.getSession().getAttribute("ECCUSER");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String createDate = df.format(new Date());// new Date()为获取当前系统时间
					UnitInfo unitInfo = new UnitInfo();
					if(user != null){
						unitInfo.setCreateUserID(user.getId());
						unitInfo.setCreateUser(user.getUserName());
					}
					if(org != null){
						unitInfo.setCreateUserDepartmentID(org.getId());
						unitInfo.setCreateUserDepartment(org.getOrgName());
					}else{
						unitInfo.setCreateUserDepartmentID("0");
						unitInfo.setCreateUserDepartment("0");
					}
					String unitsql = " select d.keyname from sys_datadic d where d.typecode='PARAMETERSSTATUS' and d.dicname='"+orgs.getIsDelete()+"'";
					List list = jdbcTemplate.queryForList(unitsql);
					Map map = (Map) list.get(0);
					String isdelete = map.get("KEYNAME").toString();
					String sql2 = " insert into ecc_ieu_unitinfo(ID,unitId,unitCode,unitName,shortName,jianpin,orgId,orgName,seq,status,companyType,unitType,onwertype,groupType,createUserid,createUser,createUserDepartmentId,createUserDepartment,createDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
					jdbcTemplate.update(sql2, new Object[] { o.getId(),orgs.getParentId(),o.getOrgCode(),o.getOrgName(),o.getSortName()
							       ,o.getJianPin(),o.getId(),o.getOrgName(),o.getSeq(),isdelete,o.getCompanyType(),orgs.getManagerType(),orgs.getManagerType(),o.getGroupType()
							       ,unitInfo.getCreateUserID(),unitInfo.getCreateUser(),unitInfo.getCreateUserDepartmentID(),unitInfo.getCreateUserDepartment(),createDate});
				}else{
					String sql="update ECC_IEU_UNITINFO set unitcode=?,unitname=?,companyType=?,seq=?,groupType=? where id=? ";
					int k=jdbcTemplate.update(sql,new Object[]{o.getOrgCode(),orgs.getOrgName(),orgs.getCompanyType(),orgs.getSeq(),o.getGroupType(),orgs.getId()});
				}
			}else{
				String sql = " update ecc_ieu_unitinfo set status='"+EnumUtil.PARAMETERSSTATUS2+"' where id='"+o.getId()+"' ";
				int k=jdbcTemplate.update(sql);
			}
		}else{
			i = jdbcTemplate.update(  "update sys_org set orgcode=?,orgName=?,sortName=?,jianpin=?,orgType=?,managerType=?,seq=?,createMan=?,companyType=? where id=?",
					new Object[] { o.getOrgCode(),orgs.getOrgName(),orgs.getSortName(),orgs.getJianPin(),orgs.getOrgType(),o.getManagerType(),orgs.getSeq()
					,orgs.getCreateMan(),orgs.getCompanyType(),orgs.getId()
			});  
			
			String sql="update ECC_IEU_UNITINFO set unitcode=?,unitname=?,companyType=?,unittype=?,groupType=?,onwertype=? where orgid=? ";
			int j=jdbcTemplate.update(sql,new Object[]{o.getOrgCode(),orgs.getOrgName(),orgs.getCompanyType(),o.getManagerType(),o.getGroupType(),o.getManagerType(),orgs.getId()});
		}
        return i;  
	}

	/**
	 * 方法说明： .通过id查找机构信息 <BR>
	 */
	@SuppressWarnings("unchecked")
	public Org findOrgById(String id) {
		Org rsorg = null;
		List<Org> list = (List) jdbcTemplate.query("select o.id,o.parentid,o.orgcode,o.orgname,o.sortname,o.sortname,o.jianpin,o.orgtype,o.managertype,o.seq,o.createman,o.createtime,o.companyType,ISDELETE,o.groupType  from sys_org o where o.id=? ",
				new Object[] { id }, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum)throws SQLException {
											Org o2 = new Org();
											try {
												ECCBeanUtil.resultSetToBean(rs, o2);
											} catch (Exception e) {
												e.printStackTrace();
											}
											return o2;
					}
				});
		if(list!=null&&list.size()==1){
			rsorg=list.get(0);
		}else{
			rsorg=null;
		}
		return rsorg;
	}
	
	/**
	 * 实现说明： 组织机构删除. <BR>
	 */
	@Override
	public int deleteOrg(Org org) {
		 int i =0;
			if(org!=null){
				 i = jdbcTemplate.update("update sys_org set isdelete='"+org.getIsDelete()+"' where id=? ",   
			                new Object[] { org.getId()});
				 jdbcTemplate.update("update ecc_ieu_unitinfo set status='"+org.getIsDelete()+"' where id=? ",   
			                new Object[] { org.getId()});
				 String upConvert = "update ecc_coll_convertunit set ISUSABLE='"+org.getIsDelete()+"' where CONVERTID='"+org.getId()+"'";
				 jdbcTemplate.update(upConvert);
			}else{ 
				return 0;
			}
			
			return i;
	}

	/**
	 * 实现说明： 获得某组织机构的直属上级组织机构. <BR>
	 */
	@Override
	public List<Map<String, Object>> getParentOrgList(Long id){
		return null;
	}

	@Override
	public int getOrgCount() {
		int count = jdbcTemplate.queryForInt("select count(*) from sys_org se where se.isdelete='"+EnumUtil.PARAMETERSSTATUS1+"'");
		return count;
	}

	@Override
	public int getChildrenOrg(String id) {
		int count = jdbcTemplate.queryForInt("select count(*) from sys_org se where se.isdelete='"+EnumUtil.PARAMETERSSTATUS1+"' and se.parentId='"+id+"'");
		return count;
	}


	public int findParentCount(String id){
		int count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM sys_org WHERE ID=(SELECT parentid FROM sys_org WHERE ID='"+id+"' AND isdelete=1) AND isdelete=2");
		return count;
	}

	/**
	 * 实现说明：查询符合条件的组织机构树 . <BR>
	 */
	@Override
	public List<Map<String, Object>> selectTreeData(String orgName,String managerType) {
		String sql = "select id,ORGNAME from sys_org where 1=1 ";
		if(orgName!=null&&!"".equals(orgName)){
			sql += " and orgName like '%"+orgName+"%' ";
		}
		if(managerType!=null&&!"".equals(managerType)){
			sql += " and managerType = '"+managerType+"' ";
		}
		sql += " order by seq"; 
		List<Map<String, Object>>  list =jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * 实现说明：组织机构调级 . <BR>
	 */
	@Override
	public void tjOrg(String sureId, String idd) {
		String sql = "update sys_org set PARENTID='"+sureId+"' where id='"+idd+"'";
		jdbcTemplate.execute(sql);
		String sql1 = "update ecc_ieu_unitinfo set unitid='"+sureId+"' where id='"+idd+"'";
		jdbcTemplate.execute(sql1);
	}

	/**
	 * 实现说明：查询可用的组织机构树. <BR>
	 */
	@Override
	public List<Map<String, Object>> findTreeData(String pid, String t) {
		List<Map<String, Object>>  list;
		   if("0".equals(pid) &&!"1".equals(t)){ 
			    list =jdbcTemplate.queryForList("SELECT id,ORGNAME,ISDELETE FROM SYS_ORG where parentid ='"+pid+"' and isDelete='"+EnumUtil.RIGHTSTAT1+"'  order by seq ");
		   }else  if("1".equals(t)){
			   list =jdbcTemplate.queryForList("SELECT id,ORGNAME,ISDELETE FROM SYS_ORG where parentid ='"+pid+"' and isDelete='"+EnumUtil.RIGHTSTAT1+"'  order by seq  ");
		   }else{
			   list =jdbcTemplate.queryForList("SELECT id,ORGNAME,ISDELETE FROM SYS_ORG where id ='"+pid+"' and isDelete='"+EnumUtil.RIGHTSTAT1+"'  order by seq  ");
		   }
			return list;
	}

	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public List<Map<String, Object>> zTreeData(Org org) {
		String sql="SELECT ID \"id\" ,t.parentid \"pId\",t.orgname \"name\" FROM sys_org T  where 1=1 and (isDelete='"+EnumUtil.PARAMETERSSTATUS2+"' or isdelete='"+EnumUtil.PARAMETERSSTATUS1+"')";
		
		if(org.getId()!=null&&!"".equals(org.getId())){
			sql+=" connect by prior t.ID=t.parentid start with  id='"+org.getId()+"'";
		}
			
		sql+=" order by seq ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public Map selectOrgList(Org org, int currentPage, int pageSize, String oid) {
		String sql = "SELECT t.id,t.orgname,t.sortname,t.jianpin FROM SYS_ORG t  where t.ISDELETE='1' ";
		if(org.getOrgName()!=null&&!"".equals(org.getOrgName())){
			sql += " and t.orgname like '%"+org.getOrgName()+"%' ";
		}
		if(org.getManagerType()!=null&&!"".equals(org.getManagerType())){
		    sql += "  and t.managertype='"+org.getManagerType()+"' ";
		}
		sql += " connect by prior t.ID=t.parentid start with  t.id='"+oid+"' order by t.seq";
		return super.queryGridist(sql, "", currentPage, pageSize);
	}
	/**
	 * 实现说明：首页点击多个年份时查询要显示的所有单位  . <BR>
	 */
	@Override
	public List selectOrgList(String oid, Org org) {
		String unittype="";
		String companyType="";
		String unitcode="";
		if(oid!=null&&oid.equals("1")){
			unittype="1";
			unitcode="orgcode";
		}else{
			unittype="9";
			unitcode="centercode";
		}
		String column="CASE WHEN SUM(S.CNMJ) > 0 THEN round(SUM(case when s.sumheatquantity<0 then 0 else s.sumheatquantity end)/SUM(s.cnmj),4) ELSE 0 end dh ";
		String sql="select sn.id from (select s."+unitcode+" id,"+column+" from scada_node_ljrl_day s where 1=1 ";
		Calendar currCal=Calendar.getInstance();  
	    int currentYear = currCal.get(Calendar.YEAR);
		sql+=" and SCADATIME >= to_date('"+currentYear+"-01-01','yyyy-MM-dd') ";
		sql+=" and SCADATIME <= to_date('"+currentYear+"-12-31','yyyy-MM-dd') ";
		sql+=" and s."+unitcode+" in (select id from sys_org start with id='"+oid+"' connect by prior id=parentid and isdelete='1') group by s."+unitcode+") sn,"
				+ "(select "+unitcode+" id from scada_node_ljrl_day where "+unitcode+" in (select id from sys_org start with id='"+oid+"' connect by prior id=parentid and isdelete='1') group by "+unitcode+") o where sn.id(+) = o.id order by nvl(dh,0) desc ";
		return super.queryForList(sql);
	}
	/**
	 * 实现说明：首页多年份查询点击详细时查询所有应显示的单位 . <BR>
	 */
	@Override
	public List selectDetailOrgList(String oid,String codeid) {
		String unittype="";
		String unitcode="";
		if(oid!=null&&oid.equals("1")){
			unittype="9";
			unitcode="centercode";
		}else{
			unittype="4";
			unitcode="nodecode";
		}
		String column="CASE WHEN SUM(S.CNMJ) > 0 THEN round(SUM(case when s.sumheatquantity<0 then 0 else s.sumheatquantity end)/SUM(s.cnmj),4) ELSE 0 end dh ";
		String sql="select sn.id from (select s."+unitcode+" id,"+column+" from scada_node_ljrl_day s where 1=1 ";
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		sql+=" and SCADATIME >= to_date('"+currentYear+"-01-01','yyyy-MM-dd') ";
		sql+=" and SCADATIME <= to_date('"+currentYear+"-12-31','yyyy-MM-dd') ";
		sql+=" and s."+unitcode+" in (select id from ecc_ieu_unitinfo start with id=(select max(id) from ecc_ieu_unitinfo where shortname='"+codeid+"' or unitname='"+codeid+"') connect by prior id=unitid and status='1') group by s."+unitcode+") sn,"
				+ "(select "+unitcode+" id from scada_node_ljrl_day where "+unitcode+" in (select id from ecc_ieu_unitinfo where unittype='"+unittype+"' start with id=(select max(id) from ecc_ieu_unitinfo where shortname='"+codeid+"' or unitname='"+codeid+"') connect by prior id=unitid and status='1') group by "+unitcode+") o where sn.id(+) = o.id order by nvl(dh,0) desc ";
		if(unittype.equals("4")){
			sql="select * from ("+sql+") where rownum<=10 ";
		}
		return super.queryForList(sql);
	}

}
