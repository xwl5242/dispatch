package com.dispatch.unit.unit.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unit.dao.UnitDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.common.ECCBeanUtil;

@Repository
public class UnitDaoImpl extends PageListJdbcTemplate implements UnitDao {

	
	/**
	 * 实现说明： 获取用能单位树. <BR>
	 */
	@Override
	public List<Map<String, Object>> getUnitTreeData(String pid,String c,UnitInfo unit) {
		
		   String sql="";
		   if("0".equals(pid)&&!"1".equals(c)){ 
			   sql = "SELECT id,unitname,unittype,(select d.dicname from sys_datadic d where d.keyname = unittype and d.typecode = 'OWNERTYPE') unittypename FROM ECC_IEU_UNITINFO where unitId ='"+pid+"' "; 
		   }else if("1".equals(c)){
			    sql = "SELECT id,unitname,unittype,(select d.dicname from sys_datadic d where d.keyname = unittype and d.typecode = 'OWNERTYPE') unittypename FROM ECC_IEU_UNITINFO where id ='"+pid+"' ";
		   }else{
			    sql = "SELECT id,unitname,unittype,(select d.dicname from sys_datadic d where d.keyname = unittype and d.typecode = 'OWNERTYPE') unittypename FROM ECC_IEU_UNITINFO where unitId ='"+pid+"' ";
		   }
		   
		   if(unit.getUnitType()!=null&&!"".equals(unit.getUnitType())){
			   if(unit.getUnitType().contains(",")){
				   sql += " and unitType in ('"+unit.getUnitType().replace(",", "','")+"') ";
			   }else{
				   sql += " and unitType='"+unit.getUnitType()+"' ";
			   }
			  
		   }
		   if(unit.getUnitName()!=null&&!"".equals(unit.getUnitName())){
			   sql += " and unitName like '%"+unit.getUnitName()+"%' ";
		   }
		   
		   if(unit.getStatus()!=null&&!"".equals(unit.getStatus())){
			   sql += " and status='"+unit.getStatus()+"'";
		   }
		   
		   sql += " order by seq"; 
		  return super.queryForList(sql);
	}

	/**
	 * 实现说明： 获取子节点条数. <BR>
	 */
	@Override
	public int getChildrenCount(String id) {
		
		String sql ="select count(1) from  ECC_IEU_UNITINFO where unitId='"+id+"'";
		int ChildrenCount =super.queryForInt(sql);
		 
		return ChildrenCount;
	}
	/**
	 * 实现说明：根据id获取用能单位详细信息 . <BR>
	 */
	@Override
	public UnitInfo findUnitInfoById(String id) {
		
		String sqlP= "(select eid.cnmj from ECC_IEU_UNITINFO_DETAILS eid where eid.unitid = T.ID and eid.status = '1' " +
				"and rownum = 1 and eid.changedate = (select max(s.changedate) from ecc_ieu_unitinfo_details s where s.status = '1' and t.id=s.unitid)) CNMJ,";
		String sql = "SELECT   "+
				 "   T.ID,  "+
				 " T.UNITCODE,  "+
				  "  NY_UNITNAME(T.UNITID) ORGNAME,  "+
				 "   T.UNITNAME,  "+
				  "  T.SHORTNAME,  "+
				   " T.JIANPIN,  "+
				  "  T.HEATINGTYPE,  "+
				  sqlP+
				  "  T.ONWERTYPE,  "+
				  "  T.SEQ,  "+
				  "  T.UNITID,  "+
				  "  T.ADDRESS,  "+
				  "  T.AREAID,  "+
				  "  T.REMARKS,  "+
				  "  T.GROUPTYPE, "+
				  "  (SELECT R.AREANAME FROM ECC_AREA R WHERE R.ID = T.AREAID) AREANAME,  "+
				  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.STATUS AND D.TYPECODE = 'PARAMETERSSTATUS') STATUS,  "+
				  "  T.UNITTYPE,  "+
				  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.COMPANYTYPE AND D.TYPECODE = 'COMPANYTYPE') COMPANYTYPE,  "+
				  "  (  "+
				  "    SELECT nvl(DE.MJHEATAREAH,0) + nvl(DE.GJHEATAREAH,0) + nvl(DE.GJHEATAREA,0) + nvl(DE.MJHEATAREA,0)  "+
				  "    FROM ECC_IEU_UNITINFO_DETAILS DE   "+
				  "    WHERE DE.CHANGEDATE = (SELECT MAX(IUD.CHANGEDATE) FROM ECC_IEU_UNITINFO_DETAILS IUD WHERE IUD.UNITID = T.ID AND IUD.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"')  "+
				  "    AND DE.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"' AND DE.UNITID = T.ID  "+
				  "   ) MJGJSUM  "+
			"	FROM ECC_IEU_UNITINFO T "+
			"	WHERE T.ID = ?";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { id },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		if(list!=null&&list.size()==1){
			unitInfo=list.get(0);
		}
		return unitInfo;
	}

	/**
	 * 实现说明：根据id获取热源详细信息 . <BR>
	 */
	@Override
	public UnitInfo findDetailsById(String id) {
		
		String sqlP= "(select eid.cnmj from ECC_IEU_UNITINFO_DETAILS eid where eid.unitid = T.ID and eid.status = '1' " +
				"and rownum = 1 and eid.changedate = (select max(s.changedate) from ecc_ieu_unitinfo_details s where s.status = '1' and t.id=s.unitid)) CNMJ,";
		String sql = "SELECT   "+
					 "   T.ID,  "+
					 " T.UNITCODE,  "+
					  "  NY_UNITNAME(T.UNITID) ORGNAME,  "+
					 "   T.UNITNAME,  "+
					  "  T.SHORTNAME,  "+
					   " T.JIANPIN,  "+
					  "  T.HEATINGTYPE,  "+
					  "  T.ONWERTYPE,  "+
					  "  T.SEQ,  "+
					  "  T.UNITID,  "+
					  "  T.ADDRESS,  "+
					  "  T.AREAID,  "+
					  "  T.REMARKS,  "+
					  sqlP+
					  "  T.GROUPTYPE, "+
					  "  (SELECT R.AREANAME FROM ECC_AREA R WHERE R.ID = T.AREAID) AREANAME,  "+
					  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.STATUS AND D.TYPECODE = 'PARAMETERSSTATUS') STATUS,  "+
					  "  T.UNITTYPE,  "+
					  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.COMPANYTYPE AND D.TYPECODE = 'COMPANYTYPE') COMPANYTYPE,  "+
					  "  (  "+
					  "    SELECT nvl(DE.MJHEATAREAH,0) + nvl(DE.GJHEATAREAH,0) + nvl(DE.GJHEATAREA,0) + nvl(DE.MJHEATAREA,0)  "+
					  "    FROM ECC_IEU_UNITINFO_DETAILS DE   "+
					  "    WHERE DE.CHANGEDATE = (SELECT MAX(IUD.CHANGEDATE) FROM ECC_IEU_UNITINFO_DETAILS IUD WHERE IUD.UNITID = T.ID AND IUD.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"')  "+
					  "    AND DE.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"' AND DE.UNITID = T.ID  "+
					  "   ) MJGJSUM  "+
				"	FROM ECC_IEU_UNITINFO T "+
				"	WHERE T.ID = ?";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { id },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		if(list!=null&&list.size()==1){
			unitInfo=list.get(0);
		}
		return unitInfo;
	}

	/**
	 * 
	 * 实现说明： 根据id获取热力站详细信息. <BR>
	 */
	@Override
	public UnitInfo findHeatById(String id) {
		
		String sqlP= "(select eid.cnmj from ECC_IEU_UNITINFO_DETAILS eid where eid.unitid = T.ID and eid.status = '1' " +
				"and rownum = 1 and eid.changedate = (select max(s.changedate) from ecc_ieu_unitinfo_details s where s.status = '1' and t.id=s.unitid)) CNMJ,";
		String sql = "SELECT   "+
				 "   T.ID,  "+
				 "   T.UNITCODE,  "+
				  "  NY_UNITNAME(T.UNITID) ORGNAME,  "+
				 "   T.UNITNAME,  "+
				  "  T.SHORTNAME,  "+
				   " T.JIANPIN,  "+
				  "  T.HEATINGTYPE,  "+
				  "  T.MANAGETYPE, "+
				  "  T.ONWERTYPE,  "+
				  "  T.SEQ,  "+
				  "  T.UNITID,  "+
				  "  T.ADDRESS,  "+
				  "  T.AREAID,  "+
				  "  t.remarks, "+
				  "  t.unitoutid, "+
				  "  T.GROUPTYPE,  "+
				  sqlP+
				  "  (select eiu.unitname from ecc_ieu_unitinfo eiu where eiu.id=t.unitoutid) unitoutname, "+
				  "  (SELECT R.AREANAME FROM ECC_AREA R WHERE R.ID = T.AREAID) AREANAME,  "+
				  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.STATUS AND D.TYPECODE = 'PARAMETERSSTATUS') STATUS,  "+
				  "  T.UNITTYPE,  "+
				  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.COMPANYTYPE AND D.TYPECODE = 'COMPANYTYPE') COMPANYTYPE,  "+
				  "  (  "+
				  "    SELECT nvl(DE.MJHEATAREAH,0) + nvl(DE.GJHEATAREAH,0) + nvl(DE.GJHEATAREA,0) + nvl(DE.MJHEATAREA,0)  "+
				  "    FROM ECC_IEU_UNITINFO_DETAILS DE   "+
				  "    WHERE DE.CHANGEDATE = (SELECT MAX(IUD.CHANGEDATE) FROM ECC_IEU_UNITINFO_DETAILS IUD WHERE IUD.UNITID = T.ID AND IUD.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"')  "+
				  "    AND DE.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"' AND DE.UNITID = T.ID  "+
				  "   ) MJGJSUM  "+
			"	FROM ECC_IEU_UNITINFO T "+
			"	WHERE T.ID = ?";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { id },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		if(list!=null&&list.size()==1){
			unitInfo=list.get(0);
		}
		return unitInfo;
	}

	/**
	 * 
	 * 实现说明： 根据id获取管网信息. <BR>
	 */
	@Override
	public UnitInfo findGwById(String id) {
		
		String sql = "select id,unitcode,unitname,shortname,unitid,JIANPIN,SEQ,(select d.dicname from sys_datadic d where d.keyname = t.status and d.typecode = 'PARAMETERSSTATUS') status,t.unittype,remarks,to_char(gwlength,'9999999999999.99') gwlength,t.address,t.areaid,(select r.areaname from ecc_area r where r.id=t.areaid) areaname from ecc_ieu_unitinfo t where t.id=?";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { id },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		if(list!=null&&list.size()==1){
			unitInfo=list.get(0);
		}
		return unitInfo;
	}

	/**
	 * 
	 * 实现说明： 保存修改后的热力站信息. <BR>
	 */
	@Override
	public void updateHeat(UnitInfo unitInfo) {
		
		String sql = "UPDATE ECC_IEU_UNITINFO SET updateuserid='"+unitInfo.getUpdateUserID()+"',"
					+ "updateuser='"+unitInfo.getUpdateUser()+"',updateuserdepartmentid='"+unitInfo.getUpdateUserDepartmentID()+"',updateuserdepartment='"+unitInfo.getUpdateUserDepartment()+"',updatedate='"+unitInfo.getUpdateDate()+"'";
					
		if(unitInfo.getUnitType()!=null&&!"".equals(unitInfo.getUnitType())){
			sql += " ,unittype='"+unitInfo.getUnitType()+"'";
		}
		if(unitInfo.getAddress()!=null&&!"".equals(unitInfo.getAddress())){
			sql += " ,address='"+unitInfo.getAddress()+"'";
		}
		if(unitInfo.getAreaId()!=null&&!"".equals(unitInfo.getAreaId())){
			sql += " ,AreaId='"+unitInfo.getAreaId()+"'";
		}
		if(unitInfo.getUnitName()!=null&&!"".equals(unitInfo.getUnitName())){
			sql += " ,unitName='"+unitInfo.getUnitName()+"'";
		}
		if(unitInfo.getShortName()!=null&&!"".equals(unitInfo.getShortName())){
			sql += " ,shortName='"+unitInfo.getShortName()+"'";
		}
		if(unitInfo.getJianPin()!=null&&!"".equals(unitInfo.getJianPin())){
			sql += " ,jianpin='"+unitInfo.getJianPin()+"'";
		}
		if(unitInfo.getHeatingType()!=null&&!"".equals(unitInfo.getHeatingType())){
			sql += " ,heatingType='"+unitInfo.getHeatingType()+"'";
		}
		if(unitInfo.getSeq()!=null&&!"".equals(unitInfo.getSeq())){
			sql += " ,seq='"+unitInfo.getSeq()+"'";
		}
		if(unitInfo.getRemarks()!=null&&!"".equals(unitInfo.getRemarks())){
			sql += " ,remarks='"+unitInfo.getRemarks()+"'";
		}
		if(unitInfo.getOnwerType()!=null&&!"".equals(unitInfo.getOnwerType())){
			sql += " ,onwerType='"+unitInfo.getOnwerType()+"'";
		}
		if(unitInfo.getManageType()!=null&&!"".equals(unitInfo.getManageType())){
			sql += " ,MANAGETYPE='"+unitInfo.getManageType()+"'";
		}
		if(unitInfo.getUnitOutId()!=null&&!"".equals(unitInfo.getUnitOutId())){
			sql += " ,unitoutid='"+unitInfo.getUnitOutId()+"'";
		}
		if(unitInfo.getGwLength()!=null&&!"".equals(unitInfo.getGwLength())){
			sql += " ,gwLength='"+unitInfo.getGwLength()+"'";
		}
		if(unitInfo.getUnitCode()!=null&&!"".equals(unitInfo.getUnitCode())){
			sql += " ,unitcode='"+unitInfo.getUnitCode()+"' ";
		}
		if(unitInfo.getVillageId()!=null&&!"".equals(unitInfo.getVillageId())){
			sql += " ,VillageId='"+unitInfo.getVillageId()+"' ";
		}
		sql += " where id='"+unitInfo.getId()+"'";
		super.execute(sql);
		String nameSql = "update ecc_coll_convertunit set COLLNAME='"+unitInfo.getUnitName()+"' where CONVERTTYPE='1' and ISUSABLE='1' and CONVERTID='"+unitInfo.getId()+"'";
    	super.execute(nameSql);
	}

	/**
	 * 
	 * 实现说明： 保存修改后的管网信息. <BR>
	 */
	@Override
	public void updateGW(UnitInfo unitInfo) {
		
		String sql = "UPDATE ECC_IEU_UNITINFO SET updateuserid='"+unitInfo.getUpdateUserID()+"',"
				+ "updateuser='"+unitInfo.getUpdateUser()+"',updateuserdepartmentid='"+unitInfo.getUpdateUserDepartmentID()+"',updateuserdepartment='"+unitInfo.getUpdateUserDepartment()+"',updatedate='"+unitInfo.getUpdateDate()+"'";
		if(unitInfo.getUnitType()!=null&&!"".equals(unitInfo.getUnitType())){
			sql += " ,unittype='"+unitInfo.getUnitType()+"'";
		}
		if(unitInfo.getAddress()!=null&&!"".equals(unitInfo.getAddress())){
			sql += " ,address='"+unitInfo.getAddress()+"'";
		}
		if(unitInfo.getAreaId()!=null&&!"".equals(unitInfo.getAreaId())){
			sql += " ,AreaId='"+unitInfo.getAreaId()+"'";
		}
		if(unitInfo.getUnitName()!=null&&!"".equals(unitInfo.getUnitName())){
			sql += " ,unitName='"+unitInfo.getUnitName()+"'";
		}
		if(unitInfo.getShortName()!=null&&!"".equals(unitInfo.getShortName())){
			sql += " ,shortName='"+unitInfo.getShortName()+"'";
		}
		if(unitInfo.getSeq()!=null&&!"".equals(unitInfo.getSeq())){
			sql += " ,seq='"+unitInfo.getSeq()+"'";
		}
		if(unitInfo.getRemarks()!=null&&!"".equals(unitInfo.getRemarks())){
			sql += " ,remarks='"+unitInfo.getRemarks()+"'";
		}
		if(unitInfo.getGwLength()!=null&&!"".equals(unitInfo.getGwLength())){
			sql += " ,gwLength='"+unitInfo.getGwLength()+"'";
		}
		sql += " where id='"+unitInfo.getId()+"'";
		 super.execute(sql);
	}

	/**
	 * 
	 * 实现说明： 保存修改后的支路信息. <BR>
	 */
	@Override
	public void updateZL(UnitInfo unitInfo) {
		
		String sql = "UPDATE ECC_IEU_UNITINFO SET updateuserid='"+unitInfo.getUpdateUserID()+"',"
				+ "updateuser='"+unitInfo.getUpdateUser()+"',updateuserdepartmentid='"+unitInfo.getUpdateUserDepartmentID()+"',updateuserdepartment='"+unitInfo.getUpdateUserDepartment()+"',updatedate='"+unitInfo.getUpdateDate()+"'";
		if(unitInfo.getUnitType()!=null&&!"".equals(unitInfo.getUnitType())){
			sql += " ,unittype='"+unitInfo.getUnitType()+"'";
		}
		if(unitInfo.getAddress()!=null&&!"".equals(unitInfo.getAddress())){
			sql += " ,address='"+unitInfo.getAddress()+"'";
		}
		if(unitInfo.getAreaId()!=null&&!"".equals(unitInfo.getAreaId())){
			sql += " ,AreaId='"+unitInfo.getAreaId()+"'";
		}
		if(unitInfo.getUnitName()!=null&&!"".equals(unitInfo.getUnitName())){
			sql += " ,unitName='"+unitInfo.getUnitName()+"'";
		}
		if(unitInfo.getShortName()!=null&&!"".equals(unitInfo.getShortName())){
			sql += " ,shortName='"+unitInfo.getShortName()+"'";
		}
		if(unitInfo.getSeq()!=null&&!"".equals(unitInfo.getSeq())){
			sql += " ,seq='"+unitInfo.getSeq()+"'";
		}
		if(unitInfo.getRemarks()!=null&&!"".equals(unitInfo.getRemarks())){
			sql += " ,remarks='"+unitInfo.getRemarks()+"'";
		}
		sql += " where id='"+unitInfo.getId()+"'";
		super.execute(sql);
		String sql1 = "update ecc_coll_convertunit set COLLNAME='"+unitInfo.getUnitName()+"' where CONVERTTYPE='1' and ISUSABLE='1' and CONVERTID='"+unitInfo.getId()+"'";
		super.execute(sql1);
	}

	/**
	 * 实现说明： 获取地区信息. <BR>
	 */
	@Override
	public Map findArea() {
		
		String sql = "select id,areaName from ECC_AREA where isUsable='"+EnumUtil.PARAMETERSSTATUS1+"'";
		return super.queryGridist(sql,"",1,10000);
	}

	/**
	 * 实现说明：根据id获取楼栋信息 . <BR>
	 */
	@Override
	public UnitInfo findLdById(String id) {
		
		String sql = "select u.id,unitcode,unitname,shortname,u.unitid,JIANPIN,u.SEQ,(select d.dicname from sys_datadic d where d.keyname = u.status and d.typecode = 'PARAMETERSSTATUS') status,u.unittype,remarks,(select v.villageName from ECC_VILLAGE v where v.id=u.villageId) as villageId,u.villageId as villageName,u.address,u.areaid,(select r.areaname from ecc_area r where r.id=u.areaid) areaname  from ecc_ieu_unitinfo u where  u.id=?";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { id },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		if(list!=null&&list.size()==1){
			unitInfo=list.get(0);
		}
		return unitInfo;
	}

	/**
	 * 实现说明：保存修改后的楼栋信息 . <BR>
	 */
	@Override
	public void updateLD(UnitInfo unitInfo) {
		
		UnitInfo u = this.findLdById(unitInfo.getId());
		String sql = "UPDATE ECC_IEU_UNITINFO SET updateuserid='"+unitInfo.getUpdateUserID()+"',"
				+ "updateuser='"+unitInfo.getUpdateUser()+"',updateuserdepartmentid='"+unitInfo.getUpdateUserDepartmentID()+"',updateuserdepartment='"+unitInfo.getUpdateUserDepartment()+"',updatedate='"+unitInfo.getUpdateDate()+"'";
		if(unitInfo.getUnitType()!=null&&!"".equals(unitInfo.getUnitType())){
			sql += " ,unittype='"+unitInfo.getUnitType()+"'";
		}
		if(unitInfo.getAddress()!=null&&!"".equals(unitInfo.getAddress())){
			sql += " ,address='"+unitInfo.getAddress()+"'";
		}
		if(unitInfo.getAreaId()!=null&&!"".equals(unitInfo.getAreaId())){
			sql += " ,AreaId='"+unitInfo.getAreaId()+"'";
		}
		if(unitInfo.getUnitName()!=null&&!"".equals(unitInfo.getUnitName())){
			sql += " ,unitName='"+unitInfo.getUnitName()+"'";
		}
		if(unitInfo.getShortName()!=null&&!"".equals(unitInfo.getShortName())){
			sql += " ,shortName='"+unitInfo.getShortName()+"'";
		}
		if(unitInfo.getSeq()!=null&&!"".equals(unitInfo.getSeq())){
			sql += " ,seq='"+unitInfo.getSeq()+"'";
		}
		if(unitInfo.getRemarks()!=null&&!"".equals(unitInfo.getRemarks())){
			sql += " ,remarks='"+unitInfo.getRemarks()+"'";
		}
		if(!(u.getVillageName()).equals(unitInfo.getVillageId())){
			sql += " ,VillageId='"+unitInfo.getVillageId()+"' ";
		}
		sql += " where id='"+unitInfo.getId()+"'";
		super.execute(sql);
	}

	/**
	 * 实现说明：查询当前登陆用户所在单位 . <BR>
	 */
	@Override
	public List findUnitId(String pid) {
		String 	sql1 = "select id from SYS_ORG where orgtype='"+EnumUtil.ORGTYPE1+"' start with id = '"+pid+"' connect by prior  parentid = id";
		return super.queryForList(sql1);
	}

	/**
	 * 实现说明： 查询符合条件的用能单位树. <BR>
	 */
	@Override
	public List<Map<String, Object>> SelectUnitTreeData(String unitName,
			String unitType) {
		
		String sql = "select id,unitname,unittype from ecc_ieu_unitinfo where 1=1 ";
		if(unitType!=null&&!"".equals(unitType)){
			   sql += " and unitType='"+unitType+"' ";
		   }
		   if(unitName!=null&&!"".equals(unitName)){
			   sql += " and unitName like '%"+unitName+"%' ";
		   }
		   sql += " order by seq";
		List<Map<String, Object>>  list =super.queryForList(sql);
		return list;
	}

	/**
	 * 实现说明： 用能单位调级. <BR>
	 */
	@Override
	public void tjUnit(String sureId, String idd) {
		
		String sql = "update sys_org set PARENTID='"+sureId+"' where id='"+idd+"'";
		super.execute(sql);
		String sql1 = "update ecc_ieu_unitinfo set unitid='"+sureId+"' where id='"+idd+"'";
		super.execute(sql1);
	}

	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public List<Map<String, Object>> getAbleUnitTreeData(String pid,
			String c) {
		List<Map<String, Object>>  list;
			if("1".equals(c)){

			   String sql2 = "SELECT id,UNITNAME,UNITCODE,unittype FROM ECC_IEU_UNITINFO where id ='"+pid+"'  and status='"+EnumUtil.PARAMETERSSTATUS1+"'  order by seq";
			   list =super.queryForList(sql2);
		   }else{
			   String sql1 = "SELECT id,UNITNAME,UNITCODE,unittype FROM ECC_IEU_UNITINFO where unitId ='"+pid+"'  and status='"+EnumUtil.PARAMETERSSTATUS1+"'  order by seq";
			   list =super.queryForList(sql1);
		   }
			return list;
	}

	@Override
	public void updateYN(UnitInfo unitInfo) {
		String sql = "UPDATE ECC_IEU_UNITINFO SET updateuserid='"+unitInfo.getUpdateUserID()+"',"
				+ "updateuser='"+unitInfo.getUpdateUser()+"',updateuserdepartmentid='"+unitInfo.getUpdateUserDepartmentID()+"',updateuserdepartment='"+unitInfo.getUpdateUserDepartment()+"',updatedate='"+unitInfo.getUpdateDate()+"'";
		if(unitInfo.getAddress()!=null||!"".equals(unitInfo.getAddress())){
			sql += " ,address='"+unitInfo.getAddress()+"' ";
		}
		if(unitInfo.getAreaId()!=null||!"".equals(unitInfo.getAreaId())){
			sql += " ,AreaId='"+unitInfo.getAreaId()+"' ";
		}
		if(unitInfo.getHeatingType()!=null||!"".equals(unitInfo.getHeatingType())){
			sql += " ,heatingType='"+unitInfo.getHeatingType()+"' ";
		}
		if(unitInfo.getRemarks()!=null||!"".equals(unitInfo.getRemarks())){
			sql += " ,remarks='"+unitInfo.getRemarks()+"' ";
		}
		sql += " where id='"+unitInfo.getId()+"'";
		super.execute(sql);
		
	}

	@Override
	public List getUnitInfoById(String oid, String id) {
		List  list;
		if("0".equals(id)){ 
		    list =super.queryForList("SELECT id,unitname as name,SEQ FROM ecc_ieu_unitinfo where unitid ='"+id+"'  start with orgid='"+oid+"'  connect by prior id=unitid  order by seq");
	   }else{
		    list =super.queryForList("SELECT id,unitname as name,SEQ FROM ecc_ieu_unitinfo where unitid ='"+id+"'  start with orgid='"+oid+"'  connect by prior id=unitid  order by seq");
	   }
		
		return list;
	}
	
	@Override
	public List getUnitInfoByName(String oid, String name) {
		String regexp = "\'";
		name = name.replaceAll(regexp, "");
		List list =super.queryForList("SELECT id,unitname as name,SEQ FROM ecc_ieu_unitinfo where UNITNAME like '%"+name+"%'  start with orgid='"+oid+"'  connect by prior id=unitid  order by seq");
		return list;
	}
	
	@Override
	public List getUnitInfoByKey(String oid, String name) {
		String regexp = "\'";
		name = name.replaceAll(regexp, "");
		List list =super.queryForList("SELECT id,unitname as name,SEQ FROM ecc_ieu_unitinfo where UNITNAME like '%"+name+"%' and key=1 start with orgid='"+oid+"'  connect by prior id=unitid  order by seq");
		return list;
	}
	
	public List<UnitInfo> getAbleUnitTreeDataNoSql(){
		String sql="SELECT ID, "+
				"       UNITCODE, "+
				"       UNITNAME, "+
				"       SHORTNAME, "+
				"       JIANPIN, "+
				"       ORGID, "+
				"       ORGNAME, "+
				"       ONWERTYPE, "+
				"       AREAID, "+
				"       ADDRESS, "+
				"       UNITID, "+
				"       VILLAGEID, "+
				"       COMPANYTYPE, "+
				"       UNITTYPE, "+
				"       HEATINGTYPE, "+
				"       MANAGETYPE, "+
				"       GWLENGTH, "+
				"       LONGITUDE, "+
				"       LATITUDE, "+
				"       SEQ, "+
				"       REMARKS, "+
				"       STATUS "+
				"  FROM ECC_IEU_UNITINFO ";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, 
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		return list;
	}

	@Override
	public int checkCode(UnitInfo unitInfo) {
		String sql = "select count(*) from ecc_ieu_unitinfo where unitcode='"+unitInfo.getUnitCode()+"' ";
		if(unitInfo.getId()!=null&&!"".equals(unitInfo.getId())){
			sql += " and id not in '"+unitInfo.getId()+"'";
		}
		int i = super.queryForInt(sql);
		return i;
	}

	
	@Override
	public UnitInfo findUnitInfoByCode(String code) {

		String sqlP= "(select eid.cnmj from ECC_IEU_UNITINFO_DETAILS eid where eid.unitid = T.ID and eid.status = '1' " +
				"and rownum = 1 and eid.changedate = (select max(s.changedate) from ecc_ieu_unitinfo_details s where s.status = '1' and t.id=s.unitid)) CNMJ,";
		String sql = "SELECT   "+
				 "   T.ID,  "+
				 " T.UNITCODE,  "+
				  "  NY_UNITNAME(T.UNITID) ORGNAME,  "+
				 "   T.UNITNAME,  "+
				  "  T.SHORTNAME,  "+
				   " T.JIANPIN,  "+
				  "  T.HEATINGTYPE,  "+
				  sqlP+
				  "  T.ONWERTYPE,  "+
				  "  T.SEQ,  "+
				  "  T.UNITID,  "+
				  "  T.ADDRESS,  "+
				  "  T.AREAID,  "+
				  "  T.REMARKS,  "+
				  "  T.GROUPTYPE, "+
				  "  (SELECT R.AREANAME FROM ECC_AREA R WHERE R.ID = T.AREAID) AREANAME,  "+
				  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.STATUS AND D.TYPECODE = 'PARAMETERSSTATUS') STATUS,  "+
				  "  T.UNITTYPE,  "+
				  "  (SELECT D.DICNAME FROM SYS_DATADIC D WHERE D.KEYNAME = T.COMPANYTYPE AND D.TYPECODE = 'COMPANYTYPE') COMPANYTYPE,  "+
				  "  (  "+
				  "    SELECT nvl(DE.MJHEATAREAH,0) + nvl(DE.GJHEATAREAH,0) + nvl(DE.GJHEATAREA,0) + nvl(DE.MJHEATAREA,0)  "+
				  "    FROM ECC_IEU_UNITINFO_DETAILS DE   "+
				  "    WHERE DE.CHANGEDATE = (SELECT MAX(IUD.CHANGEDATE) FROM ECC_IEU_UNITINFO_DETAILS IUD WHERE IUD.UNITID = T.ID AND IUD.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"')  "+
				  "    AND DE.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"' AND DE.UNITID = T.ID  "+
				  "   ) MJGJSUM  "+
			"	FROM ECC_IEU_UNITINFO T "+
			"	WHERE T.UNITCODE = ?";
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { code },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						UnitInfo info = new UnitInfo();
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							logger.error(e.toString(), e);
						}
						return info;
					}
				});
		if(list!=null&&list.size()==1){
			unitInfo=list.get(0);
		}
		return unitInfo;
	}

	@Override
	public List getHotUnitInfo(String oid, String name) {
		String regexp = "\'";
		name = name.replaceAll(regexp, "");
		List list =super.queryForList("SELECT id,unitname as name,SEQ FROM ECC_IEU_UNITINFO EU  WHERE eu.id IN (select so.ID FROM ecc_ieu_unitinfo so where"+
						              " so.status='1' start with so.orgid='"+oid+"' connect BY prior so.id= so.unitid)");
		return list;
	}

	@Override
	public List getHeatUnitInfo(String oid, String name) {
		List list =super.queryForList("SELECT id,unitname as name FROM ECC_HOT_UNIT_BEAS base");
          return list;
	}

}
