/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  UnitInfoDaoImpl.java     <BR>
 * Author: fupenglin  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2014-12-12 上午11:13:14 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.dispatch.unit.unitInfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.org.dao.IOrgDao;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.dao.IUnitInfoDao;
import com.util.EnumUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;

/**
 * 
 * 功能描述：用能单位数据信息  .  <BR>
 * 历史版本: <Br>
 * 开发者: fupenglin  <BR>
 * 时间：2014-12-12 上午11:30:49  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：2014-12-12 上午11:30:49 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
@Repository
public class UnitInfoDaoImpl  implements IUnitInfoDao  {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static UnitInfo unitInfo = new UnitInfo ();
	@Autowired
	private IOrgDao orgdao;
	/**
	 * 删除用能单位信息
	 * 实现说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:34:25 <BR>
	 */
	@Override
	public int deleteUnitInfo(UnitInfo u) {
		return 0;
	}
	/**
	 * 
	 * 实现说明：状态"1"可用 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 下午02:49:34 <BR>
	 * 
	 */
	
	@Override
	public int enableUnitInfo(UnitInfo u) {
		 int i =0;
			if(u!=null){
				if("1".equals(u.getStatus())){
					i = jdbcTemplate.update("update ECC_IEU_UNITINFO a set  a.UPDATEUSERID= '"+u.getUpdateUserID()+"', a.UPDATEUSER= '"+u.getUpdateUser()+"', a.UPDATEUSERDEPARTMENTID='"+u.getUpdateUserDepartmentID()+"',a.UPDATEUSERDEPARTMENT='"+u.getUpdateUserDepartment()+"' ,a.UPDATEDATE='"+u.getUpdateDate()+"',a.Status= '"+EnumUtil.PARAMETERSSTATUS1+"'  where a.id " 
					        +" in (select id  from ECC_IEU_UNITINFO start with id = '"+u.getId()+"' connect by NOCYCLE prior id = unitId) ");
				}else if("2".equals(u.getStatus()))
				i = jdbcTemplate.update("update ECC_IEU_UNITINFO a set  a.UPDATEUSERID= '"+u.getUpdateUserID()+"', a.UPDATEUSER= '"+u.getUpdateUser()+"', a.UPDATEUSERDEPARTMENTID='"+u.getUpdateUserDepartmentID()+"',a.UPDATEUSERDEPARTMENT='"+u.getUpdateUserDepartment()+"' ,a.UPDATEDATE='"+u.getUpdateDate()+"',a.Status= '"+EnumUtil.PARAMETERSSTATUS1+"'  where a.id=  '" +u.getId()+"'");
			}else{ 
				return 0;
			}
			
			return i;
	}
	/**
	 * 
	 * 实现说明：状态2不可用 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 下午02:50:05 <BR>
	 * 2表示停用子，1表示停用当前
	 */
	@Override
	public int disableUnitInfo(UnitInfo u) {
		int i = 0;
		if (u != null) {
			if ("2".equals(u.getStatus())) {
				i = jdbcTemplate
						.update("update ECC_IEU_UNITINFO a set  a.UPDATEUSERID= '"
								+ u.getUpdateUserID()
								+ "', a.UPDATEUSER= '"
								+ u.getUpdateUser()
								+ "', a.UPDATEUSERDEPARTMENTID='"
								+ u.getUpdateUserDepartmentID()
								+ "',a.UPDATEUSERDEPARTMENT='"
								+ u.getUpdateUserDepartment()
								+ "' ,a.UPDATEDATE='"
								+ u.getUpdateDate()
								+ "',a.Status= '"+EnumUtil.PARAMETERSSTATUS2+"'  where a.id "
								+ " in (select id  from ECC_IEU_UNITINFO start with id = '"
								+ u.getId()
								+ "' connect by NOCYCLE prior id = unitId) ");
			} else if ("1".equals(u.getStatus()))
				i = jdbcTemplate
						.update("update ECC_IEU_UNITINFO a set  a.UPDATEUSERID= '"
								+ u.getUpdateUserID()
								+ "', a.UPDATEUSER= '"
								+ u.getUpdateUser()
								+ "', a.UPDATEUSERDEPARTMENTID='"
								+ u.getUpdateUserDepartmentID()
								+ "',a.UPDATEUSERDEPARTMENT='"
								+ u.getUpdateUserDepartment()
								+ "' ,a.UPDATEDATE='"
								+ u.getUpdateDate()
								+ "',a.Status= '"+EnumUtil.PARAMETERSSTATUS2+"'  where a.id=  '"
								+ u.getId()
								+ "'");

		} else {
			return 0;
		}
		return i;
	}
	
	public List infoList(UnitInfo u){
		
		String sql = "select id  from ECC_IEU_UNITINFO start with id = '"+u.getId()+"' connect by NOCYCLE prior id = unitId";
		return jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 
	 * 实现说明： 通过id查询用能信息. <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:34:42 <BR>
	 */
	@Override
	public UnitInfo findUnitInfoById(String id) {
		UnitInfo rsUnitInfo =null;
		rsUnitInfo= (UnitInfo)jdbcTemplate.queryForObject("select u.id,u.unitcode,u.unitname,u.shortname,u.jianpin,u.longitude,u.latitude,u.remarks,u.seq from ECC_IEU_UNITINFO u where u.id=?", new Object[] { id }, new RowMapper() {  
            @Override  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException { 
            	  try {
        				ECCBeanUtil.resultSetToBean(rs, unitInfo);
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
               return 	unitInfo; 
            }
            }  );
		return rsUnitInfo;
	}
	/**
	 * 查询用能单位信息总条数
	 * 实现说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:35:12 <BR>
	 */
	@Override
	public int getUnitInfoCount(UnitInfo ui) {
		String sql = "select COUNT(1) from (select d.* from (SELECT T.ID, "+
       " T.UNITCODE,  ny_unitname(t.Unitid) orgname, "+
       " T.UNITNAME, "+
       " T.SHORTNAME, "+
       " T.JIANPIN, "+
       " T.HEATINGTYPE, "+
       " T.ONWERTYPE,"+
       " T.SEQ, "+
       " T.STATUS, "+
       " SUMGWLENGTH, "+
       " TOTALCOUNTGW, "+
       " MJHEATAREAH, "+
       " GJHEATAREAH, "+
       " MJHEATAREA, "+
       " GJHEATAREA, "+
       " MJGJSUM, "+
       " HEATCOUNT, "+
       " CHANGEDATE,ROW_NUMBER() OVER(ORDER BY CREATEDATE DESC) AS ROW_INDEX "+
  " FROM ECC_IEU_UNITINFO T "+
  " LEFT JOIN (SELECT T1.UNITID, "+
                   " SUM(GWLENGTH) AS SUMGWLENGTH, "+
                  "  COUNT(*) AS TOTALCOUNTGW "+
             "  FROM ECC_IEU_UNITINFO T1 "+
            "  WHERE T1.UNITTYPE = '"+EnumUtil.OWNERTYPE4+"' and T1.status='"+EnumUtil.PARAMETERSSTATUS1+"' "+
             " GROUP BY T1.UNITID) A "+
   " ON A.UNITID = T.ID "+
" LEFT JOIN (SELECT E.UNITID, "+
                   " CHANGEDATE CHANGEDATE, "+
                  "  MJHEATAREAH MJHEATAREAH,"+
                  "  GJHEATAREAH GJHEATAREAH, "+
                  "  GJHEATAREA GJHEATAREA, "+
                  "  MJHEATAREA MJHEATAREA,"+
                  "  E.MJHEATAREAH + E.GJHEATAREAH + E.GJHEATAREA + "+
                      "  E.MJHEATAREA AS MJGJSUM "+
              " FROM ECC_IEU_UNITINFO_DETAILS E  "+
              " WHERE (e.unitid,e.changedate) IN (SELECT unitid,max(changedate) FROM ECC_IEU_UNITINFO_DETAILS where status='"+EnumUtil.PARAMETERSSTATUS1+"' GROUP BY unitid )) B "+
   " ON T.ID = B.UNITID "+
 " LEFT JOIN (SELECT Z.UNITID, COUNT(*) HEATCOUNT "+
              " FROM ECC_IEU_UNITINFO Z where unittype='"+EnumUtil.OWNERTYPE3+"' "+
             " GROUP BY Z.UNITID) F "+
   " ON F.UNITID = T.ID "+
" WHERE T.UNITTYPE = '"+EnumUtil.OWNERTYPE1+"' and t.ORGID in (select ID FROM sys_org m start with m.ID='"+ui.getOrgId()+"' connect by m.PARENTID=prior m.ID )";
		//编号  	
		if(ui.getUnitCode()!=null&&!"".equals(ui.getUnitCode())) sql = sql + " and unitcode like '%"+ui.getUnitCode()+"%'";
		//名称
		if(ui.getUnitName()!=null&&!"".equals(ui.getUnitName())) sql = sql + " and unitname like '%"+ui.getUnitName()+"%'";
		//简称
		if(ui.getShortName()!=null&&!"".equals(ui.getShortName())) sql = sql + " and shortname like '%"+ui.getShortName()+"%'";
		//简拼
		if(ui.getJianPin()!=null&&!"".equals(ui.getJianPin())) sql = sql + " and jianpin like '%"+ui.getJianPin()+"%'";
		//状态
	    if(ui.getStatus()!=null&&!"".equals(ui.getStatus())) sql = sql + " and Status = '"+ui.getStatus()+"'";
		sql += " order by t.seq ) d )";
	    int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	/**
	 * 查询所有用能单位信息
	 * 实现说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:35:44 <BR>
	 */
	@Override
	public List<Map<String, Object>> getUnitInfoList(UnitInfo ui, int pageIndex, int pageSize) {
		return query( ui,"ECC_IEU_UNITINFO ", pageIndex, pageSize);
	}
	private List<Map<String, Object>> query(UnitInfo ui ,String tableName, int pageNo, int pageSize) {
		if (pageNo <=0) pageNo = 0;
		if (pageSize <= 0) pageSize = 10;
		
		String coutSql="select count(1)  from "+ tableName;
		int count=	jdbcTemplate.queryForInt( coutSql);
		int _preIndex = (pageNo - 1) * pageSize + 1;
		int _endIndex = pageNo * pageSize;
		//String sql = "select d.* from ( select t.id,t.unitcode,t.unitname,t.shortname,t.jianpin,t.heatingtype,t.onwertype,t.seq,t.status,gwlengths,GWCount,mjHeatAreaH,gjHeatAreaH,mjHeatArea,gjHeatArea,mjgjsum,heatCount,row_number() over(order by createDate desc) AS ROW_INDEX from ecc_ieu_unitinfo t left join (SELECT t1.unitid,sum(gwLength) AS gwlengths,COUNT(*) as GWCount FROM ecc_ieu_unitinfo t1 WHERE t1.unitType = '4'  group by t1.unitid) a on a.unitid = t.id left join (select d.id,d.unitid,d.gjHeatArea,d.mjheatarea,d.gjheatareah,d.mjheatareah,mjgjsum from ecc_ieu_unitinfo_details d left join (select e.id,sum(e.mjHeatAreaH + e.gjHeatAreaH + e.gjHeatArea + e.mjHeatArea) as mjgjsum from ecc_ieu_unitinfo_details e group by e.id) f on d.id = f.id) b on t.id = b.unitid left join (select z.unitid, count(*) heatCount from ecc_ieu_unitinfo_zl z  group by z.unitid) f on f.unitid = t.id where t.unittype = '1' ";
		String sql="select d.* from (SELECT T.ID, "+
       " T.UNITCODE,  ny_unitname(t.Unitid) orgname, "+
       " T.UNITNAME, "+
       " T.SHORTNAME, "+
       " T.JIANPIN, "+
       " T.HEATINGTYPE, "+
       " T.ONWERTYPE,"+
       " T.SEQ, "+
       " case when T.STATUS = '"+EnumUtil.PARAMETERSSTATUS1+"' then '可用' when T.STATUS = '"+EnumUtil.PARAMETERSSTATUS2+"' then '不可用' end STATUS, "+
       " SUMGWLENGTH, "+
       " TOTALCOUNTGW, "+
       " MJHEATAREAH, "+
       " GJHEATAREAH, "+
       " MJHEATAREA, "+
       " GJHEATAREA, "+
       " MJGJSUM, "+
       " HEATCOUNT,"+ 
       "FEEDCOUNT, "+
       " CHANGEDATE,ROW_NUMBER() OVER(ORDER BY CREATEDATE DESC) AS ROW_INDEX "+
  " FROM ECC_IEU_UNITINFO T "+
  " LEFT JOIN (SELECT T1.UNITID, "+
                   " SUM(GWLENGTH) AS SUMGWLENGTH, "+
                  "  COUNT(*) AS TOTALCOUNTGW "+
             "  FROM ECC_IEU_UNITINFO T1 "+
            "  WHERE T1.UNITTYPE = '"+EnumUtil.OWNERTYPE4+"' and T1.status='"+EnumUtil.PARAMETERSSTATUS1+"' "+
             " GROUP BY T1.UNITID) A "+
   " ON A.UNITID = T.ID "+
" LEFT JOIN (SELECT E.UNITID, "+
                   " CHANGEDATE CHANGEDATE, "+
                  "  MJHEATAREAH MJHEATAREAH,"+
                  "  GJHEATAREAH GJHEATAREAH, "+
                  "  GJHEATAREA GJHEATAREA, "+
                  "  MJHEATAREA MJHEATAREA,"+
                  "  E.MJHEATAREAH + E.GJHEATAREAH + E.GJHEATAREA + "+
                      "  E.MJHEATAREA AS MJGJSUM "+
              " FROM ECC_IEU_UNITINFO_DETAILS E  "+
              " WHERE (e.unitid,e.changedate) IN (SELECT unitid,max(changedate) FROM ECC_IEU_UNITINFO_DETAILS where status='"+EnumUtil.PARAMETERSSTATUS1+"' GROUP BY unitid )) B "+
   " ON T.ID = B.UNITID "+
 " LEFT JOIN (SELECT Z.UNITID, COUNT(*) HEATCOUNT "+
              " FROM ECC_IEU_UNITINFO Z where unittype='"+EnumUtil.OWNERTYPE3+"' "+
             " GROUP BY Z.UNITID) F "+
   " ON F.UNITID = T.ID "+
 " LEFT JOIN (SELECT y.UNITID, COUNT(*) FEEDCOUNT "+
           " FROM ECC_IEU_UNITINFO y where unittype='"+EnumUtil.OWNERTYPE2+"' "+
          " GROUP BY y.UNITID) G "+
" ON G.UNITID = T.ID "+
" WHERE T.UNITTYPE = '"+EnumUtil.OWNERTYPE1+"' and t.ORGID in (select ID FROM sys_org m start with m.ID='"+ui.getOrgId()+"' connect by m.PARENTID=prior m.ID ) ";
				
		//编号  
		if(ui.getUnitCode()!=null&&!"".equals(ui.getUnitCode())) sql = sql + " and unitcode like '%"+ui.getUnitCode()+"%'";
		//名称
		if(ui.getUnitName()!=null&&!"".equals(ui.getUnitName())) sql = sql + " and unitname like '%"+ui.getUnitName()+"%'";
		//简称
		if(ui.getShortName()!=null&&!"".equals(ui.getShortName())) sql = sql + " and shortname like '%"+ui.getShortName()+"%'";
		//简拼
		if(ui.getJianPin()!=null&&!"".equals(ui.getJianPin())) sql = sql + " and jianpin like '%"+ui.getJianPin()+"%'";
		//状态
	    if(ui.getStatus()!=null&&!"".equals(ui.getStatus())) sql = sql + " and Status = '"+ui.getStatus()+"'";
		sql = sql + " order by t.seq ) d " + "WHERE d.ROW_INDEX >=" + _preIndex + " AND d.ROW_INDEX <=" + _endIndex;
		return 	jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * 
	 * 实现说明：保存用能单位信息 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:36:09 <BR>
	 */
	@Override
	public int saveUnitInfo(UnitInfo ui) {
		int n =0;
		  String uuid=UUIDGenerator.getUUID();
		  ui.setId(uuid);
		if(ui!=null){ 
			n =	jdbcTemplate.update("insert into ECC_IEU_UNITINFO(ID,unitCode,unitName,shortName,orgid,orgname,jianPin,unitId,unitType,seq,remarks,status,createUserid,createUser,createUserDepartmentId,createUserDepartment,createDate)"
			+ " values(?,NO_CODE('ECC','"+ui.getJianPin()+"','UNITCODE','ECC_IEU_UNITINFO'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
			new Object[] { ui.getId(),ui.getUnitName(),ui.getShortName(),ui.getOrgId(),ui.getOrgName(),ui.getJianPin(),
					ui.getUnitId(),ui.getUnitType(),ui.getSeq(),ui.getRemarks(),ui.getStatus()
					,ui.getCreateUserID(),ui.getCreateUser(),ui.getCreateUserDepartmentID(),ui.getCreateUserDepartment(),ui.getCreateDate()
					  });
		}else{
			return 0;
		}
		return n;
	}
	/**
	 * 修改用能单位信息
	 * 实现说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:36:24 <BR>
	 */
	@Override
	public int updateUnitInfo(UnitInfo ui) {
		int i = jdbcTemplate.update(  "update ECC_IEU_UNITINFO set  latitude=?,longitude=?,Seq=?,Remarks=?,updateUserId=?,updateUser=?,updateUserDepartmentId=?,updateUserDepartment=?,updateDate=? where ID=?",
					new Object[] {ui.getLatitude(),ui.getLongitude(),
        		ui.getSeq(),ui.getRemarks(),ui.getUpdateUserID(),
        		ui.getUpdateUser(),ui.getUpdateUserDepartmentID(),ui.getUpdateUserDepartment(),
        		ui.getUpdateDate(),ui.getId()
        		     });  
       
        return i; 
        
        
	}
	
	/**
	 * 分页查询所有组织机构信息
	 * 实现说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:35:44 <BR>
	 */
	@Override
	public List getOrgInfoList(Org org, int pageIndex, int pagesize,HttpServletRequest request) {
		if (pageIndex <= 0) pageIndex = 1;
		if (pagesize <= 0) pagesize = 10;
		
		String coutSql="select count(1)  from SYS_ORG where isdelete='"+EnumUtil.PARAMETERSSTATUS1+"'";
		int count=	jdbcTemplate.queryForInt( coutSql);
		int _preIndex = (pageIndex - 1) * pagesize + 1;
		int _endIndex = pageIndex * pagesize;
		Org o2=(Org) request.getSession().getAttribute("ECCORG");
		String sql="select * from ( SELECT k.*, ROW_NUMBER() OVER(ORDER BY CREATETIME DESC) AS ROW_INDEX "+
				"  FROM (SELECT O.* "+
				"          FROM SYS_ORG O "+
				"         WHERE O.ORGTYPE = '"+EnumUtil.ORGTYPE1+"' and o.isdelete='"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"              "+
				"           AND O.MANAGERTYPE = '"+EnumUtil.MANAGETYPE1+"' "+
				"         START WITH id = '"+o2.getId()+"' "+
				"        CONNECT BY PRIOR ID = PARENTID) k "+
				" WHERE ID NOT IN (SELECT ORGID FROM ECC_IEU_UNITINFO WHERE unittype='"+EnumUtil.OWNERTYPE1+"') ";
		//编号  
		if(org.getOrgCode()!=null && !"".equals(org.getOrgCode())) sql = sql + " and orgcode like '%"+org.getOrgCode()+"%'";
		//名称
		if(org.getOrgName()!=null && !"".equals(org.getOrgName())) sql = sql + " and orgname like '%"+org.getOrgName()+"%'";
		//简称
		if(org.getSortName()!=null && !"".equals(org.getSortName()))  sql = sql + " and sortname like '%"+org.getSortName()+"%'";
		//简拼
		if(org.getJianPin()!=null && !"".equals(org.getJianPin())) sql = sql + " and jianpin like '%"+org.getJianPin()+"%'";
		//状态
	    if(org.getOrgType()!=null && !"".equals(org.getOrgType())) sql = sql + " and orgtype = '"+org.getOrgType()+"'";
//	    sql = sql + " and t.isdelete=1  order by t.createtime desc) d WHERE d.ROW_INDEX >=" + _preIndex + " AND d.ROW_INDEX <=" + _endIndex;
	    sql = sql + "  ) d WHERE d.ROW_INDEX >=" + _preIndex + " AND d.ROW_INDEX <=" + _endIndex + " ";
		return 	jdbcTemplate.queryForList(sql);
	}
	
	@Override
	public int getOrgInfoCount(Org org) {
		
		String sql = "select count(1) from ( select t.*,row_number() over(order by createtime desc) AS ROW_INDEX from " 
				 +" (select c.* from (select * from " 
				 +" (select * from SYS_ORG o start with o.ID in "
		         +" (select b.id from SYS_ORG b ) and o.parentid = '0'"
		         +" CONNECT BY PRIOR  o.ID = o.PARENTID) so "
		         +"  where so.orgtype='"+EnumUtil.ORGTYPE1+"'and so.managertype='"+EnumUtil.MANAGETYPE1+"') c  where  not exists (select t.orgid from ECC_IEU_UNITINFO t where t.orgid = c.id)) " 
		         +" t where 1=1  ";
		//编号  
		if(org.getOrgCode()!=null && !"".equals(org.getOrgCode())) sql = sql + " and orgcode like '%"+org.getOrgCode()+"%'";
		//名称
		if(org.getOrgName()!=null && !"".equals(org.getOrgName())) sql = sql + " and orgname like '%"+org.getOrgName()+"%'";
		//简称
		if(org.getSortName()!=null && !"".equals(org.getSortName()))  sql = sql + " and sortname like '%"+org.getSortName()+"%'";
		//简拼
		if(org.getJianPin()!=null && !"".equals(org.getJianPin())) sql = sql + " and jianpin like '%"+org.getJianPin()+"%'";
		//状态
	    if(org.getOrgType()!=null && !"".equals(org.getOrgType())) sql = sql + " and orgtype = '"+org.getOrgType()+"'";
	    sql = sql +" and t.isdelete=1 order by t.createtime desc) d";
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	
	/**
	 * 查询所有组织机构信息
	 * 实现说明： . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2014-12-12 上午11:35:44 <BR>
	 */
	@Override
	public List<Org> getOrgInfoList(String ids) {
		
		String[] idss = ids.split(",");
		String orgIds = "";
		for(int i=0;i<idss.length;i++){
			orgIds = orgIds+"'"+idss[i]+"',";
		}
		String sql = "select t.id,t.parentid,t.orgcode,t.orgname,t.sortname,t.jianpin,t.orgtype,t.managertype,t.seq,t.isdelete,t.createtime,t.createman from SYS_ORG t where t.id  = ("+orgIds.substring(0, orgIds.length()-1)+")";
		
		final List<Org> orgList = new ArrayList<Org>();
		jdbcTemplate.query(sql,new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				orgList.add(getOrg(rs));
			}
		});
		return orgList;
	}
	/**
	 * 
	 * 方法说明：查询机构 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl <BR>
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @return: Org
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-20 下午05:24:55 <BR>
	 */
	public static Org getOrg(ResultSet rs) throws SQLException{
		Org org = new Org();
		org.setId(rs.getString("id"));
		org.setParentId(rs.getString("parentid"));
		org.setOrgCode(rs.getString("orgcode"));
		org.setOrgName(rs.getString("orgname"));
		org.setSortName(rs.getString("sortname"));
		org.setJianPin(rs.getString("jianpin"));
		org.setOrgType(rs.getString("orgtype"));
		org.setManagerType(rs.getString("managertype"));
		org.setSeq(rs.getString("seq"));
		org.setIsDelete(rs.getString("isdelete"));
		org.setCreateTime(rs.getString("createtime"));
		org.setCreateMan(rs.getString("createman"));
		return org;
	}
	/**
	 * 
	 * 实现说明：更新用能unitid . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-13 下午06:20:30 <BR>
	 */
	@Override
	public void updateAllUnitid() {
		String sql = "update ecc_ieu_unitinfo u set u.unitid = decode((select iu.id from ecc_ieu_unitinfo iu"
            +" where iu.orgid =(select o.parentid from sys_org o where o.id = u.orgid)),null,'0',"
            +" (select iu.id from ecc_ieu_unitinfo iu where iu.orgid = (select o.parentid from sys_org o where o.id = u.orgid)))";
		jdbcTemplate.execute(sql);
	}
	/**
	 * 
	 * 实现说明：查询启用状态是否存在子集 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-1-31 下午09:11:52 <BR>
	 */
	@Override
	public boolean hasChild(String ids) {
		String sql = "SELECT ID FROM ECC_IEU_UNITINFO M START WITH M.Unitid = '"+ids+"' CONNECT BY M.UNITID = PRIOR M.ID ";

		 List list = jdbcTemplate.queryForList(sql);
		 if(list !=null && list.size()>0){
			 return true;
		 }else{
			 return false;
		 }
		
	}
	/**
	 * 
	 * 实现说明：查询停用状态是否存在子集. <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: fupenglin <BR>
	 * @Datetime：2015-2-1 下午04:36:39 <BR>
	 */
	@Override
	public boolean disableHasChild(String ids) {
		String sql = "SELECT t.id from ecc_ieu_unitinfo t where t.status='"+EnumUtil.PARAMETERSSTATUS2+"' and t.unitid in ('"+ids+"')";
			 List list = jdbcTemplate.queryForList(sql);
			 if(list !=null && list.size()>0){
				 return true;
			 }else{
				 return false;
			 }
			
	}
	/**
	 * 
	 * 实现说明：首页右下角柱状图 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年12月5日 下午3:53:41 <BR>
	 */
	public List getOrgHeatPieData(UnitInfo unit,Org org){
		String column="round(decode(sum(ncnmj),0,0,sum(sumheatquantity)/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),4) ";
		if(unit.getEnergyType()==null){
			unit.setEnergyType("sumheatquantity");
			column="round(decode(sum(ncnmj),0,0,sum(sumheatquantity)/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),4) ";
		}
		if(unit.getEnergyType().equals("sumheatquantity")){
			column="round(decode(sum(ncnmj),0,0,sum(sumheatquantity)/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),4) ";
		}else if(unit.getEnergyType().equals("water")){
			column="CASE WHEN SUM(SN.CNMJ) > 0 THEN round(SUM(water)*10000/SUM(sn.cnmj),4) ELSE 0 end";
		}else{
			column="CASE WHEN avg(SN.CNMJ) > 0 THEN round(SUM(power)*10000/avg(sn.cnmj),4) ELSE 0 end";
		}
		String sql="";
		String unitcode="";
		String oid=org.getUnitParentId();
		if(oid!=null&&oid.equals("1")){
			unitcode="orgcode";
			sql+=" SELECT id orgcode,dh,eu.shortname ORGNAME from (select "+column+" dh,orgcode from "
					+ "(select "+unitcode+" orgcode,scadatime,sum(sn.cnmj) cnmj,case when avg(tr.avgtemp) is null then 0 else avg(tr.avgtemp) end avgtemp,"
					+ "SUM(CASE WHEN "+unit.getEnergyType()+"<0 THEN 0 ELSE "+unit.getEnergyType()+" END) "+unit.getEnergyType()+","
					+ "sum(e.cnmj) ncnmj"+
					"   FROM SCADA_NODE_LJRL_DAY_INPUT SN,temp_realvalue_day tr,ecc_ieu_unitinfo e where sn.nodecode=e.id ";
		}else{
			unitcode="centercode";
			sql+=" SELECT id orgcode,dh,eu.shortname ORGNAME from (select "+column+" dh,orgcode from "
					+ "(select "+unitcode+" orgcode,scadatime,sum(sn.cnmj) cnmj,case when avg(tr.avgtemp) is null then 0 else avg(tr.avgtemp) end avgtemp,"
					+ "SUM(CASE WHEN "+unit.getEnergyType()+"<0 THEN 0 ELSE "+unit.getEnergyType()+" END) "+unit.getEnergyType()+","
					+ "sum(e.cnmj) ncnmj"+
					"   FROM SCADA_NODE_LJRL_DAY_INPUT SN,temp_realvalue_day tr,ecc_ieu_unitinfo e where sn.nodecode=e.id ";
		}
		if(unit.getYear()!=null&&!"".equals(unit.getYear())){
			sql+=" and SCADATIME >= to_date('"+unit.getYear()+"-01-01','yyyy-MM-dd') ";
			sql+=" and SCADATIME <= to_date('"+unit.getYear()+"-12-31','yyyy-MM-dd') ";
		}else{
			 Calendar currCal=Calendar.getInstance();  
		     int currentYear = currCal.get(Calendar.YEAR);
			sql+=" and SCADATIME >= to_date('"+currentYear+"-01-01','yyyy-MM-dd') ";
			sql+=" and SCADATIME <= to_date('"+currentYear+"-12-31','yyyy-MM-dd') ";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
			sql+=" and orgcode='"+unit.getId()+"'";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
			sql+=" and centercode='"+unit.getId()+"'";
		}
			sql+=" AND "+unitcode+" IN (SELECT ID "+
			"                       FROM SYS_ORG SO "+
			"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
			"                     CONNECT BY SO.PARENTID = PRIOR SO.ID)"+
			"   and tr.realdate(+)=sn.scadatime and tr.stationcode(+)='511' ";
			sql+="  GROUP BY "+unitcode+",scadatime,nodecode ) sn group by orgcode ) sn,ecc_ieu_unitinfo eu where sn.orgcode=eu.id  "+
			"  ORDER BY dh DESC ";
		
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 
	 * 实现说明：点击首页柱状图详细 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年11月24日 下午4:49:37 <BR>
	 */
	public List getOrgHeatPieDetailData(UnitInfo unit,Org org){
		String column="round(decode(sum(ncnmj),0,0,sum(sumheatquantity)/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),4) ";
		if(unit.getEnergyType()==null){
			unit.setEnergyType("sumheatquantity");
			column="round(decode(sum(ncnmj),0,0,sum(sumheatquantity)/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),4) ";
		}
		if(unit.getEnergyType().equals("sumheatquantity")){
			column="round(decode(sum(ncnmj),0,0,sum(sumheatquantity)/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),4) ";
		}else if(unit.getEnergyType().equals("water")){
			column="CASE WHEN SUM(SN.CNMJ) > 0 THEN round(SUM(water)*10000/SUM(sn.cnmj),4) ELSE 0 end";
		}else{
			column="CASE WHEN avg(SN.CNMJ) > 0 THEN round(SUM(power)*10000/avg(sn.cnmj),4) ELSE 0 end";
		}
		String sql="";
		String unitcode="";
		String oid=org.getUnitParentId();
		if(oid!=null&&oid.equals("1")){
			unitcode="centercode";
			sql+=" SELECT id orgcode,dh,eu.shortname ORGNAME from (select "+column+" dh,orgcode from "
					+ "(select "+unitcode+" orgcode,scadatime,sum(sn.cnmj) cnmj,case when avg(tr.avgtemp) is null then 0 else avg(tr.avgtemp) end avgtemp,"
					+ "SUM(CASE WHEN "+unit.getEnergyType()+"<0 THEN 0 ELSE "+unit.getEnergyType()+" END) "+unit.getEnergyType()+","
					+ "sum(e.cnmj) ncnmj"+
					"   FROM SCADA_NODE_LJRL_DAY_INPUT SN,temp_realvalue_day tr,ecc_ieu_unitinfo e where sn.nodecode=e.id ";
		}else{
			unitcode="nodecode";
			sql+=" SELECT id orgcode,dh,eu.shortname ORGNAME from (select "+column+" dh,orgcode from "
					+ "(select "+unitcode+" orgcode,scadatime,sum(sn.cnmj) cnmj,case when avg(tr.avgtemp) is null then 0 else avg(tr.avgtemp) end avgtemp,"
					+ "SUM(CASE WHEN "+unit.getEnergyType()+"<0 THEN 0 ELSE "+unit.getEnergyType()+" END) "+unit.getEnergyType()+","
					+ "sum(e.cnmj) ncnmj"+
					"   FROM SCADA_NODE_LJRL_DAY_INPUT SN,temp_realvalue_day tr,ecc_ieu_unitinfo e where sn.nodecode=e.id ";
		}
		if(unit.getYear()!=null&&!"".equals(unit.getYear())){
			sql+=" and SCADATIME >= to_date('"+unit.getYear()+"-01-01','yyyy-MM-dd') ";
			sql+=" and SCADATIME <= to_date('"+unit.getYear()+"-12-31','yyyy-MM-dd') ";
		}else{
			Calendar currCal=Calendar.getInstance();  
			int currentYear = currCal.get(Calendar.YEAR);
			sql+=" and SCADATIME >= to_date('"+currentYear+"-01-01','yyyy-MM-dd') ";
			sql+=" and SCADATIME <= to_date('"+currentYear+"-12-31','yyyy-MM-dd') ";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
			sql+=" and orgcode='"+unit.getId()+"'";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
			sql+=" and centercode='"+unit.getId()+"'";
		}
		sql+=" AND "+unitcode+" IN (SELECT ID "+
				"                       FROM ecc_ieu_unitinfo SO "+
				"                      START WITH SO.ID = (select max(id) from ecc_ieu_unitinfo where shortname='"+unit.getUnitName()+"' or unitname='"+unit.getUnitName()+"') "+
				"                     CONNECT BY SO.UNITID = PRIOR SO.ID and status='1')"
				+ " and tr.realdate(+)=sn.scadatime and tr.stationcode(+)='511' ";
		
		sql+="  GROUP BY "+unitcode+",nodecode,scadatime ) sn group by orgcode ) sn,ecc_ieu_unitinfo eu where sn.orgcode=eu.id "+
				"  ORDER BY dh DESC ";
		
		return jdbcTemplate.queryForList(sql);
	}
	/**
	 * 
	 * 实现说明：首页左下角折线图 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年12月5日 下午3:54:11 <BR>
	 */
	public List getHeatDataList(UnitInfo unit,Org org){
		if(unit.getEnergyType()==null){
			unit.setEnergyType("sumheatquantity");
		}
			
		String sql="SELECT DATETIME  datenum ,round(EnergyType/10000,2) heat FROM (SELECT TO_CHAR(SCADATIME, 'yyyy-MM') DATETIME, "+
				"       SUM(case when "+unit.getEnergyType()+"<0 then 0 else "+unit.getEnergyType()+" end) EnergyType "+
				"  FROM SCADA_NODE_LJRL_DAY_INPUT "+
				" WHERE  1=1 ";
		if(unit.getYear()!=null&&!"".equals(unit.getYear())){
			sql+=" and SCADATIME >= to_date('"+unit.getYear()+"-01-01','yyyy-MM-dd') ";
			sql+=" and SCADATIME <= to_date('"+unit.getYear()+"-12-31','yyyy-MM-dd') ";
		}else{
			 Calendar currCal=Calendar.getInstance();  
		     int currentYear = currCal.get(Calendar.YEAR);
			sql+=" and SCADATIME >= to_date('"+currentYear+"-01-01','yyyy-MM-dd') ";
			sql+=" and SCADATIME <= to_date('"+currentYear+"-12-31','yyyy-MM-dd') ";
		}
				if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
					sql+=" and orgcode='"+unit.getId()+"'";
				}
				if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
					sql+=" and centercode='"+unit.getId()+"'";
				}
				String condition="";
				if(org.getUnitParentId()!=null&&org.getUnitParentId().equals("1")){
					condition="orgcode";
				}else{
					condition="centercode";
				}
				sql+=" AND "+condition+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID)";
				
				sql+=" GROUP BY TO_CHAR(SCADATIME, 'yyyy-MM') "+
				" ORDER BY TO_CHAR(SCADATIME, 'yyyy-MM')) ";
		return jdbcTemplate.queryForList(sql);
	}
	
	public HashMap findEnergyByType(String type,UnitInfo unit,Org org){
		 Calendar currCal=Calendar.getInstance();  
	     int currentYear = currCal.get(Calendar.YEAR);
		String sql="SELECT round(CASE WHEN ROUND(SUM(NVL( CASE WHEN SUMHEATQUANTITY<0 THEN 0 ELSE sumheatquantity END , 0)) / 10000, 2)=0 THEN NULL ELSE  ROUND(SUM(NVL("+type+", 0)) / 10000, 2)/ROUND(SUM(NVL( CASE WHEN SUMHEATQUANTITY<0 THEN 0 ELSE sumheatquantity END , 0)) / 10000, 2) END,2)  perheat ,round(sum(nvl("+type+",0))/10000,2) "+type+" FROM SCADA_FEED_LJRL_DAY_INPUT WHERE scadatime>to_date('"+currentYear+"-01-01','yyyy-MM-dd') ";
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
			sql+=" and orgcode='"+unit.getId()+"'";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
			sql+=" and centercode='"+unit.getId()+"'";
		}
		String condition="";
		if(org.getManagerType().equals("1")){
			condition="orgcode";
		}else if(org.getManagerType().equals("9")){
			condition="centercode";
		}
		sql+=" AND "+condition+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID)";
		
		List list= jdbcTemplate.queryForList(sql);
		return (HashMap) list.get(0);
	}
	/**
	 * 
	 * 实现说明：当前年热力站耗热量（耗水量、耗电量） . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年12月5日 下午4:36:47 <BR>
	 */
	public HashMap findEnergyNodeByType(String type,UnitInfo unit,Org org){
		Calendar currCal=Calendar.getInstance();  
	     int currentYear = currCal.get(Calendar.YEAR);
		String sql="SELECT ROUND(SUM(CASE WHEN "+type+"<0 THEN 0 ELSE "+type+" END) / 10000, 2) heat, ";
		if(type.equals("sumheatquantity")){
			sql+=" round(decode(sum(ncnmj),0,0,sum("+type+")/sum(ncnmj))*(121 / (ceil(max(SCADATIME) - min(SCADATIME))+1))*decode((18-avg(avgtemp)),0,0,17.4/(18-avg(avgtemp))),2) dh ";
		}else if(type.equals("water")){
			sql+="round(CASE WHEN SUM(cnmj)>0 THEN ROUND(SUM(CASE WHEN "+type+"<0 THEN 0 ELSE "+type+" END), 2)*10000/SUM(cnmj) ELSE 0 END,2)";
		}else{
			sql+="round(CASE WHEN avg(cnmj)>0 THEN ROUND(SUM(CASE WHEN "+type+"<0 THEN 0 ELSE "+type+" END), 2)/avg(cnmj) ELSE 0 END,2)";
		}
				sql+=" from (select sum(e.cnmj) ncnmj,sum(n.cnmj) cnmj,n.scadatime,sum(CASE WHEN "+type+"<0 THEN 0 ELSE "+type+" END) "+type+","
						+ " case when avg(tr.avgtemp) is null then 0 else avg(tr.avgtemp) end avgtemp "+
				"  FROM  SCADA_NODE_LJRL_DAY_INPUT n,ecc_ieu_unitinfo e,temp_realvalue_day tr "+
				" WHERE n.nodecode=e.id and tr.realdate(+)=n.scadatime and tr.stationcode(+)='511' and SCADATIME > TO_DATE('"+currentYear+"-01-01', 'yyyy-MM-dd') ";
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
			sql+=" and orgcode='"+unit.getId()+"'";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
			sql+=" and centercode='"+unit.getId()+"'";
		}
		String condition="";
		if(org.getManagerType().equals("1")){
			condition="orgcode";
		}else if(org.getManagerType().equals("9")){
			condition="centercode";
		}
		sql+=" AND "+condition+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID)"+
				" group by n.scadatime)";
		List list= jdbcTemplate.queryForList(sql);
		return (HashMap) list.get(0);
	}
	public HashMap findTotalEnergyNodeByType(String type,UnitInfo unit,Org org){
		String dh="";
		String sql="";
		String condition="orgcode";
		String unittype="1";
		String total="";
		if(unit.getUnitType()==null||unit.getUnitType().trim().length()==0){
			if(org.getManagerType().equals("1")){
				condition="orgcode";
				unittype="1";
			}else if(org.getManagerType().equals("9")){
				condition="CENTERID";
				unittype="9";
			}
		}
		if(type.equals("sumheatquantity")){
			dh=" es.columntype = '1' and es.checktype = '1' ";
			total="/10000";
		}else if(type.equals("water")){
			dh=" es.columntype = '2' and es.checktype = '1' ";
			total="/100000000";
		}else{
			total="/10000";
			dh=" es.columntype = '3' and es.checktype = '1' ";
		}
		
		sql+="select round(sum(zb)"+total+",0) total from (select checknum*cnmj zb,b.orgcode from (select checknum, es.collunit "+
				"  from ecc_stanard_stanardinfo es  "+
				" where "+dh+" "+
				"   and es.collunit in "+
				"       (select id from ecc_ieu_unitinfo t where t.unittype = '"+unittype+"' START WITH t.ID = '"+org.getUnitParentId()+"' "+
			"                     CONNECT BY t.unitid = PRIOR t.ID)) a, "+
				"       ( select "+condition+" orgcode, sum(eiu.cnmj) cnmj "+
				"         from bas_node bn, ecc_ieu_unitinfo eiu "+
				"        where "+condition+" in "+
				"              (select id from ecc_ieu_unitinfo t where t.unittype = '"+unittype+"' START WITH t.ID = '"+org.getUnitParentId()+"' "+
			"                     CONNECT BY t.unitid = PRIOR t.ID) "+
				"          and eiu.id = bn.nodecode "+
				"        group by "+condition+") b where b.orgcode=a.collunit) where 1=1 ";
		if(unit.getId()!=null&&!"".equals(unit.getId())){
			sql+=" and orgcode='"+unit.getId()+"'";
		}
		List list= jdbcTemplate.queryForList(sql);
		return (HashMap) list.get(0);
	}
	/**
	 * 
	 * 实现说明：首页自产热量 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年12月7日 下午5:35:57 <BR>
	 */
	public HashMap findHeatInfoByYear(Org org,UnitInfo unit){
		Calendar currCal=Calendar.getInstance();  
	     int currentYear = currCal.get(Calendar.YEAR);
		String condition="";
		String condition1="";
		
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
			condition+=" and orgcode='"+unit.getId()+"'";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
			condition+=" and centercode='"+unit.getId()+"'";
		}
		if(org.getManagerType().equals("1")){
			condition1+=" and orgcode";
		}else if(org.getManagerType().equals("9")){
			condition1+=" and centercode";
		}
		
		String sql="SELECT ROUND(A.HEAT  * 100 / B.HEAT, 0) PERHEAT,round(a.dh,4) dh, "+
				"       ROUND(A.HEAT / 10000, 2) HEAT　FROM (SELECT SUM(case when SUMHEATQUANTITY<0 then 0 else SUMHEATQUANTITY end) HEAT, "+
				"                                                  SUM(case when SUMHEATQUANTITY<0 then 0 else SUMHEATQUANTITY end) / "+
				"                                                  SUM(CNMJ) DH "+
				"                                             FROM SCADA_feed_LJRL_DAY_INPUT "+
				"                                            WHERE SCADATIME >= "+
				"                                                  TO_DATE('"+currentYear+"-01-01', "+
				"                                                          'yyyy-MM-dd') "+
				"                                               "+condition+" "+condition1+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID) ) A, "+
				"       (SELECT SUM(case when SUMHEATQUANTITY<0 then 0 else SUMHEATQUANTITY end) HEAT, "+
				"               SUM(case when SUMHEATQUANTITY<0 then 0 else SUMHEATQUANTITY end) / SUM(CNMJ) DH "+
				"          FROM SCADA_feed_LJRL_DAY_INPUT "+
				"         WHERE SCADATIME >= TO_DATE('"+(currentYear-1)+"-01-01', 'yyyy-MM-dd') "+
				"           AND SCADATIME < TO_DATE('"+currentYear+"-01-01', 'yyyy-MM-dd') "+
				"             "+condition+" "+condition1+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID)) B ";
		List list= jdbcTemplate.queryForList(sql);
		return (HashMap) list.get(0);

	}
	/**
	 * 
	 * 实现说明：首页能耗总量 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年12月7日 下午5:35:17 <BR>
	 */
	public HashMap findPowerInfoByYear(Org org,UnitInfo unit){
		Calendar currCal=Calendar.getInstance();  
	     int currentYear = currCal.get(Calendar.YEAR);
		String condition="";
		String condition1="";
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("1")){
			condition+=" and orgcode='"+unit.getId()+"'";
		}
		if(unit.getUnitType()!=null&&unit.getUnitType().equals("9")){
			condition+=" and centercode='"+unit.getId()+"'";
		}
		if(org.getManagerType().equals("1")){
			condition1+=" and orgcode";
		}else if(org.getManagerType().equals("9")){
			condition1+=" and centercode";
		}
		
		String sql="SELECT ROUND(( A.HEAT) * 100 / B.HEAT, 0) PERHEAT,round(a.dh,4) dh, "+
				"       ROUND(A.HEAT / 10000, 2) HEAT　FROM (SELECT sum(nvl(sumheatquantity, 0)) * 0.03412 + "+
				"               sum(case when power<0 then 0 else power end) * 1.229 / 10000 + "+
				"               sum(case when water<0 then 0 else water end) * 0.0857 + sum(case when coal<0 then 0 else coal end) * 0.7143 + "+
				"               sum(case when gas<0 then 0 else gas end) * 12.4014 / 10000 + "+
				"               sum(case when diesel<0 then 0 else diesel end) * 1.4571 heat, "+
				"               (sum(case when sumheatquantity<0 then 0 else sumheatquantity end) * 0.03412 + "+
				"               sum(case when power<0 then 0 else power end) * 1.229 / 10000 + "+
				"               sum(case when water<0 then 0 else water end) * 0.0857 + sum(case when coal<0 then 0 else coal end) * 0.7143 + "+
				"               sum(case when gas<0 then 0 else gas end) * 12.4014 / 10000 + "+
				"               sum(case when diesel<0 then 0 else diesel end) * 1.4571)  / SUM(CNMJ) DH "+
				"                                             FROM SCADA_FEED_LJRL_DAY_INPUT "+
				"                                            WHERE SCADATIME >= "+
				"                                                  TO_DATE('"+currentYear+"-01-01', "+
				"                                                          'yyyy-MM-dd') "+condition+
				"                                               "+condition1+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID) ) A, "+
				"       (SELECT  sum(case when sumheatquantity<0 then 0 else sumheatquantity end) * 0.03412 + "+
				"               sum(case when power<0 then 0 else power end) * 1.229 / 10000 + "+
				"               sum(case when water<0 then 0 else water end) * 0.0857 + sum(case when coal<0 then 0 else coal end) * 0.7143 + "+
				"               sum(case when gas<0 then 0 else gas end) * 12.4014 / 10000 + "+
				"               sum(case when diesel<0 then 0 else diesel end) * 1.4571  HEAT, "+
				"               (sum(case when sumheatquantity<0 then 0 else sumheatquantity end) * 0.03412 + "+
				"               sum(case when power<0 then 0 else power end) * 1.229 / 10000 + "+
				"               sum(case when water<0 then 0 else water end) * 0.0857 + sum(case when coal<0 then 0 else coal end) * 0.7143 + "+
				"               sum(case when gas<0 then 0 else gas end) * 12.4014 / 10000 + "+
				"               sum(case when diesel<0 then 0 else diesel end) * 1.4571)  / SUM(CNMJ) DH "+
				"          FROM SCADA_FEED_LJRL_DAY_INPUT "+
				"         WHERE SCADATIME >= TO_DATE('"+(currentYear-1)+"-01-01', 'yyyy-MM-dd') "+
				"           AND SCADATIME < TO_DATE('"+currentYear+"-01-01', 'yyyy-MM-dd') "+condition+
				"             "+condition1+" IN (SELECT ID "+
				"                       FROM SYS_ORG SO "+
				"                      START WITH SO.ID = '"+org.getUnitParentId()+"' "+
				"                     CONNECT BY SO.PARENTID = PRIOR SO.ID)) B ";
		List list= jdbcTemplate.queryForList(sql);
		return (HashMap) list.get(0);
		
	}
	/**
	 * 
	 * 实现说明：查询权限下热源的个数 . <BR>
	 * @see com.ecc.ieu.unitInfo.dao.impl.UnitInfoDaoImpl
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年12月7日 下午5:41:07 <BR>
	 */
	public Map getFeedCount(String oid){
		String sql="select nvl(count(1),0) as feedcount from ecc_ieu_unitinfo t where t.unitid in ("+getIdsSql(oid)+") and t.unittype = '3' and t.status='1'";
		return jdbcTemplate.queryForMap(sql);
	}
	private String getIdsSql(String oid){
		return "select t.id from ecc_ieu_unitinfo t start with t.id = '"+oid+"' connect by prior t.id = t.unitid and t.status = '1'";
	}
	public Map getSyshomeCountInfo(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		Map map = new HashMap();
		map.putAll(getUnitCount(queryMap.get("oid")+""));
		map.putAll(getHeatCount(queryMap.get("oid")+""));
		map.putAll(getFeedCount(queryMap.get("oid")+""));
		map.putAll(getBuildCount(queryMap.get("oid")+""));
		map.putAll(getAreaCount(queryMap.get("oid")+""));
		map.putAll(getSumheatquantity(queryMap));
		map.putAll(getPower(queryMap));
		map.putAll(getWater(queryMap));
		map.putAll(getCoal(queryMap));
		map.putAll(getGas(queryMap));
		map.putAll(getCarbon(queryMap));
		String sql = "select t.id from sys_org t where t.parentid='"+queryMap.get("oid")+"' and t.orgtype='"+EnumUtil.OPTTYPE1+"' and t.isdelete='"+EnumUtil.PARAMETERSSTATUS1+"'";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		List returnList = null;
		if(list!=null&&!list.isEmpty()){
			returnList = new ArrayList();
			for (Map<String, Object> m : list) {
				Map returnMap = new HashMap();
				String oid= m.get("ID").toString();
				returnMap.put("orgName", orgdao.findOrgById(oid).getOrgName());
				queryMap.put("oid1", oid);
				queryMap.put("orgType", orgdao.findOrgById(oid).getOrgType());
				returnMap.putAll(getUnderInfo(queryMap));
				returnList.add(returnMap);
			}
		}
		map.put("list", returnList);
		map.put("chartList", getSumheatquantityChart(queryMap));
		return map;
	}
	public Map getCaiNuanJi(Map queryMap){
		Map map = new HashMap();
		String sql1=" select * from (select j.gnjstartdate,j.gnjenddate from ecc_ieu_cainuanji j where j.unitid='"+queryMap.get("oid")+"'  and j.status='2' order by j.gnjstartdate,j.gnjenddate desc) where rownum=1";
		List lt = jdbcTemplate.queryForList(sql1);
		if(lt!=null&&lt.size()>0){
			HashMap hashMap = (HashMap) lt.get(0);
			map.put("STARTTIME",hashMap.get("GNJSTARTDATE"));
			map.put("ENDTIME",hashMap.get("GNJENDDATE"));
		}
		return map;
	}
	
	//用能单位个数
	private Map getUnitCount(String oid){
		String sql="select nvl(count(1),0) as unitcount from ecc_ieu_unitinfo t where t.unitid in ("+getIdsSql(oid)+") and t.unittype = '1' and t.status='1'";
		return jdbcTemplate.queryForMap(sql);
	}
	//热力站个数
	private Map getHeatCount(String oid){
		String sql="select nvl(count(1),0) as heatcount from ecc_ieu_unitinfo t where t.unitid in ("+getIdsSql(oid)+") and t.unittype = '4' and t.status='1' ";
		return jdbcTemplate.queryForMap(sql);
	}
	
	//楼栋个数
	private Map getBuildCount(String oid){
		String sql="select nvl(count(1),0) as buildcount from ecc_ieu_unitinfo t where t.unitid in ("+getIdsSql(oid)+") and t.unittype = '7' and t.status='1'";
		return jdbcTemplate.queryForMap(sql);
	}
	//供热面积
	private Map getAreaCount(String oid){
		String sql="select round(nvl(sum(nvl(cnmj,0)),0),2) area "+
				"  from scada_node_ljrl_day s, "+
				"       ecc_ieu_unitinfo t, "+
				"       (select nodecode，max(scadatime) scadatime "+
				"          from scada_node_ljrl_day "+
				"         group by nodecode) sn "+
				" where s.nodecode = t.id "+
				"   and s.nodecode in ("+getIdsSql(oid)+") "+
				"   and s.nodecode = sn.nodecode "+
				"   and s.scadatime = sn.scadatime ";
		return jdbcTemplate.queryForMap(sql);
	}
	private String getScadatimeSql(Map queryMap){
		if(queryMap.get("STARTTIME")!=null&&queryMap.get("ENDTIME")!=null){
			return "  and scadatime>=to_date('"+queryMap.get("STARTTIME")+"', 'yyyy-mm-dd') and scadatime<=to_date('"+queryMap.get("ENDTIME")+"', 'yyyy-mm-dd') ";
		}else{
			return "";
		}
	}
	private Map getSumheatquantity(Map queryMap){
		String sql="select round(sum(nvl(sumheatquantity,0))/10000,2) sumheatquantity "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and nvl(s.sumheatquantity,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+")";
		return jdbcTemplate.queryForMap(sql);
	}
	private Map getPower(Map queryMap){
		String sql="select round(nvl(ns.power,0) + nvl(fs.power,0), 2) power "+
				"  from (select sum(nvl(power,0)) power "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and nvl(s.power,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+")) ns, "+
				"       (select sum(nvl(power,0)) power "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id  and t.grouptype='1' "+
				"           and nvl(s.power,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+")) fs ";
		
		return jdbcTemplate.queryForMap(sql);
	}
	private Map getWater(Map queryMap){
		String sql="select round(nvl(ns.water,0) + nvl(fs.water,0), 2) water "+
				"  from (select sum(nvl(water,0)) water "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and nvl(water,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+")) ns, "+
				"       (select sum(nvl(water,0)) water "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id  and t.grouptype='1' "+
				"           and nvl(water,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+")) fs ";
		
		return jdbcTemplate.queryForMap(sql);
	}
	private Map getCoal(Map queryMap){
		String sql="select round(sum(nvl(coal,0)), 2) coal "+
				"  from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				" where s.feedcode = t.id  and t.grouptype='1' "+
				"   and nvl(coal,0) >= 0 "+getScadatimeSql(queryMap)+
				"   and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") ";
		
		return jdbcTemplate.queryForMap(sql);
	}
	private Map getGas(Map queryMap){
		String sql="select round(sum(nvl(s.gas,0)), 2) gas "+
				"  from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				" where s.feedcode = t.id  and t.grouptype='1' "+
				"   and nvl(gas,0) >= 0 "+getScadatimeSql(queryMap)+
				"   and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") ";
		return jdbcTemplate.queryForMap(sql);
	}
	private Map getCarbon(Map queryMap){
		String sql = "select round(sum(nvl(fcoal, 0) * get_weight('COAL') + "+
				"                 nvl(fgas, 0) * get_weight('GAS') + nvl(fpower, 0) * 0.604 + "+
				"                 nvl(npower, 0) * 0.604), "+
				"             2) total "+
				"  from (select sum(nvl(n.power,0)) npower "+
				"          from scada_node_ljrl_day n "+
				"         where n.nodecode in "+
				"               ("+getIdsSql(queryMap.get("oid")+"")+") "+getScadatimeSql(queryMap)+") n, "+
				"       (select sum(nvl(f.coal,0)) fcoal, sum(nvl(f.gas,0)) fgas, sum(nvl(f.power,0)) fpower "+
				"          from scada_feed_ljrl_day f,ecc_ieu_unitinfo t "+
				"         where f.feedcode=t.id and t.unittype='3' and t.grouptype='1' "+
				"         and f.feedcode in "+
				"               ("+getIdsSql(queryMap.get("oid")+"")+") "+getScadatimeSql(queryMap)+") f ";
		return jdbcTemplate.queryForMap(sql);
	}
	private Map getUnderInfo(Map queryMap){
		String sql = "select round(nvl(n.sumheatquantity, 0) + nvl(f.sumheatquantity, 0), 2) sumheatquantity, "+
				"       round(nvl(n.power, 0) + nvl(f.power, 0), 2) power, "+
				"       round(nvl(n.water, 0) + nvl(f.water, 0), 2) water, "+
				"       round(nvl(n.gas, 0) + nvl(f.gas, 0), 2) gas, "+
				"       round(nvl(n.coal, 0) + nvl(f.coal, 0), 2) coal, "+
				"       round(nvl(n.carbon, 0) + nvl(f.carbon, 0), 2) carbon "+
				"  from (select sum(nvl(sumheatquantity,0)) sumheatquantity, "+
				"               sum(nvl(power,0)) power, "+
				"               sum(nvl(water,0)) water, "+
				"               0 gas, "+
				"               0 coal, "+
				"               sum(power) * 0.604 carbon "+
				"          from SCADA_NODE_LJRL_DAY s, ecc_ieu_unitinfo e "+
				"         where nvl(sumheatquantity,0) >= 0 "+ 
				"           and s.nodecode = e.id and e.unittype='4' "+getScadatimeSql(queryMap)+
				"           and nodecode in ("+getIdsSql(queryMap.get("oid1")+"")+")) n, "+
				"       (select 0 sumheatquantity, "+
				"               sum(nvl(power,0)) power, "+
				"               sum(nvl(water,0)) water, "+
				"               sum(nvl(gas,0)) gas, "+
				"               sum(nvl(coal,0)) coal, "+
				"               sum(nvl(power,0)) * 0.604 + sum(gas) * get_weight('GAS') + "+
				"               sum(nvl(coal,0)) * get_weight('COAL') carbon "+
				"          from SCADA_feed_LJRL_DAY s, ecc_ieu_unitinfo e "+
				"         where  s.feedcode = e.id and e.unittype='3' and e.grouptype='1' "+getScadatimeSql(queryMap)+
				"           and feedcode in ("+getIdsSql(queryMap.get("oid1")+"")+")) f ";
		return jdbcTemplate.queryForMap(sql);
	}
	public List getSumheatquantityChart(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		String sql = "select round(sum(nvl(s.sumheatquantity,0))/10000,2) showcolumn, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and nvl(s.sumheatquantity,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM') order by to_char(s.scadatime, 'yyyy-MM')";
/*		String sql = "select round(nvl(ns.sumheatquantity, 0) / 10000 + "+
				"             nvl(fs.sumheatquantity, 0) / 10000, "+
				"             2) showcolumn, "+
				"       ns.scadatime "+
				"  from (select sum(s.sumheatquantity) sumheatquantity, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and s.sumheatquantity > 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM')) ns, "+
				"       (select sum(s.sumheatquantity) sumheatquantity, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id "+
				"           and s.sumheatquantity > 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM')) fs "+
				" where ns.scadatime = fs.scadatime ";
*/		return jdbcTemplate.queryForList(sql);
	}
	public List getWaterChart(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		/*String sql = "select round(nvl(sum(s.water), 0), "+
				"             2) showcolumn,to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and s.water > 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM') order by to_char(s.scadatime, 'yyyy-MM')";*/
		String sql = "select round(nvl(ns.water, 0)  + "+
				"             nvl(fs.water, 0) , "+
				"             2) showcolumn, "+
				"       case when ns.scadatime is null then fs.scadatime else ns.scadatime end scadatime "+
				"  from (select sum(nvl(s.water,0)) water, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and nvl(s.water,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM')) ns full join  "+
				"       (select sum(nvl(s.water,0)) water, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id  and t.grouptype='1' "+
				"           and nvl(s.water,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM')) fs "+
				" on ns.scadatime = fs.scadatime ";
		return jdbcTemplate.queryForList(sql);
	}
	public List getPowerChart(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		/*String sql = "select round(nvl(sum(s.power), 0), "+
				"             2) showcolumn,to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and s.power > 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM') order by to_char(s.scadatime, 'yyyy-MM')";*/
		String sql = "select round(nvl(ns.power, 0)  + "+
				"             nvl(fs.power, 0) , "+
				"             2) showcolumn, "+
				"       case when ns.scadatime is null then fs.scadatime else ns.scadatime end scadatime "+
				"  from (select sum(nvl(s.power,0)) power, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.nodecode = t.id "+
				"           and nvl(s.power,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.nodecode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM')) ns full join "+
				"       (select sum(nvl(s.power,0)) power, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id  and t.grouptype='1' "+
				"           and nvl(s.power,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM')) fs "+
				" on ns.scadatime = fs.scadatime ";
		return jdbcTemplate.queryForList(sql);
	}
	public List getCoalChart(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		String sql = "select round(sum(nvl(s.coal,0)),2) showcolumn, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id  and t.grouptype='1' "+
				"           and nvl(s.coal,0) > 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM') order by to_char(s.scadatime, 'yyyy-MM')";
		return jdbcTemplate.queryForList(sql);
	}
	public List getGasChart(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		String sql = "select round(sum(nvl(s.gas,0)),2) showcolumn, "+
				"               to_char(s.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_feed_ljrl_day s, ecc_ieu_unitinfo t "+
				"         where s.feedcode = t.id  and t.grouptype='1' "+
				"           and nvl(s.gas,0) >= 0 "+getScadatimeSql(queryMap)+
				"           and s.feedcode in ("+getIdsSql(queryMap.get("oid")+"")+") "+
				"         group by to_char(s.scadatime, 'yyyy-MM') order by to_char(s.scadatime, 'yyyy-MM')";
		return jdbcTemplate.queryForList(sql);
	}
	public List getCarbonChart(Map queryMap){
		queryMap.putAll(getCaiNuanJi(queryMap));
		String sql = "select round(nvl(fcoal, 0) * get_weight('COAL') + "+
				"                 nvl(fgas, 0) * get_weight('GAS') + nvl(fpower, 0) * 0.604 + "+
				"                 nvl(npower, 0) * 0.604, "+
				"             2) showcolumn,case when n.scadatime is null then f.scadatime else n.scadatime end scadatime "+
				"  from (select sum(nvl(n.power,0)) npower,to_char(n.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_node_ljrl_day n "+
				"         where n.nodecode in "+
				"               ("+getIdsSql(queryMap.get("oid")+"")+") "+getScadatimeSql(queryMap)+""+
				"		 group by to_char(n.scadatime, 'yyyy-MM')) n full join "+
				"       (select sum(nvl(f.coal,0)) fcoal, sum(nvl(f.gas,0)) fgas, sum(nvl(f.power,0)) fpower,to_char(f.scadatime, 'yyyy-MM') scadatime "+
				"          from scada_feed_ljrl_day f,ecc_ieu_unitinfo t "+
				"         where f.feedcode=t.id  and t.grouptype='1' and f.feedcode in "+
				"               ("+getIdsSql(queryMap.get("oid")+"")+") "+getScadatimeSql(queryMap)+""+
				"		 group by to_char(f.scadatime, 'yyyy-MM')) f "+
				"          on n.scadatime=f.scadatime  order by scadatime";
		return jdbcTemplate.queryForList(sql);
	}
}
