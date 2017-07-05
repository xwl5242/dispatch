package com.dispatch.unit.unitInfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.dao.IDetailsDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.SpringUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;

/**
 * 功能描述：热源数据信息  .  <BR>
 */
@Repository
public class DetailsDaoImpl extends PageListJdbcTemplate implements IDetailsDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static UnitInfo unitInfo = new UnitInfo ();
	
	/**
	 * 实现说明： 可用1. <BR>
	 */
	public int enableDetails(UnitInfo u) {
		int i = 0;
		if (u != null) {

			if ("1".equals(u.getStatus())) {
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
								+ "',a.Status= '"
								+ EnumUtil.PARAMETERSSTATUS1
								+ "'  where a.id "
								+ " in (select id  from ECC_IEU_UNITINFO start with id = '"
								+ u.getId()
								+ "' connect by  NOCYCLE prior id = unitId) ");
			} else if ("2".equals(u.getStatus()))
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
								+ "',a.Status= '"
								+ EnumUtil.PARAMETERSSTATUS1
								+ "'  where a.id=  '" + u.getId() + "'");
		} else {
			return 0;
		}

		return i;
	}
	
	/**
	 * 实现说明：不可用2 . <BR>
	 * 2把子集一并修改，1只修改当前
	 */
	public int disableDetails(UnitInfo u) {
		 int i =0;
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
								+ "',a.Status= '"
								+ EnumUtil.PARAMETERSSTATUS2
								+ "'  where a.id "
								+ " in (select id  from ECC_IEU_UNITINFO start with id = '"
								+ u.getId()
								+ "' connect by NOCYCLE prior id = unitId) ");

				i = jdbcTemplate
						.update("update Ecc_Ieu_Unitinfo a set  a.UPDATEUSERID= '"
								+ u.getUpdateUserID()
								+ "', a.UPDATEUSER= '"
								+ u.getUpdateUser()
								+ "', a.UPDATEUSERDEPARTMENTID='"
								+ u.getUpdateUserDepartmentID()
								+ "',a.UPDATEUSERDEPARTMENT='"
								+ u.getUpdateUserDepartment()
								+ "' ,a.UPDATEDATE='"
								+ u.getUpdateDate()
								+ "',a.Status='"
								+ EnumUtil.PARAMETERSSTATUS2
								+ "'  where a.id "
								+ " in (select id  from Ecc_Ieu_Unitinfo start with UNITID = '"
								+ u.getId()
								+ "' connect by NOCYCLE prior id = unitId) ");
			} else if ("1".equals(u.getStatus()))
				i = jdbcTemplate
						.update("update ECC_IEU_UNITINFO a SET a.UPDATEUSERID='"
								+ u.getUpdateUserID()
								+ "',a.UPDATEUSER='"
								+ u.getUpdateUser()
								+ "',a.UPDATEUSERDEPARTMENTID='"
								+ u.getUpdateUserDepartmentID()
								+ "',a.UPDATEUSERDEPARTMENT='"
								+ u.getUpdateUserDepartment()
								+ "',a.UPDATEDATE='"
								+ u.getUpdateDate()
								+ "',a.Status='"
								+ EnumUtil.PARAMETERSSTATUS2
								+ "' where a.id='" + u.getId() + "'");

		} else {
			return 0;
		}
		return i;
	}
	
	public int checkHeatStation(UnitInfo u){
		String sql="SELECT SUM(IDS) "+
				"  FROM (SELECT COUNT(1) IDS "+
				"          FROM ECC_IEU_UNITINFO T "+
				"         WHERE T.UNITID = '"+u.getId()+"' and T.UNITTYPE = '"+EnumUtil.OWNERTYPE5+"' ) ";
		return jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 实现说明：删除热源信息 . <BR>
	 */
	@Override
	public int deleteDetails(UnitInfo u) {
		return 0;
	}
	
	/**
	 * 实现说明：通过id查询热源 . <BR>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UnitInfo findDetailsById(String id) {
		UnitInfo rsUnitInfo =null;
		String sql ="SELECT T.ID, "+
				"       T.UNITCODE,  ny_unitname(t.Unitid) orgname, "+
				"       T.UNITNAME, "+
				"       T.SHORTNAME, "+
				"       T.JIANPIN, "+
				"       T.HEATINGTYPE, "+
				"       T.ONWERTYPE, "+
				"       T.SEQ, "+
				"       T.UNITTYPE, "+
				"       T.STATUS,t.longitude,t.latitude, "+
				"       SUMGWLENGTH gwLengths, "+
				"       TOTALCOUNTGW gwCount, "+
				"       MJHEATAREAH, "+
				"       GJHEATAREAH, "+
				"       MJHEATAREA, "+
				"       GJHEATAREA, "+
				"       TOTALHEATAREA mjgjSum, "+
				"       HEATCOUNT, "+
				"       CHANGEDATE "+
				"  FROM ECC_IEU_UNITINFO T "+
				"  LEFT JOIN (SELECT T1.UNITID, "+
				"                    SUM(GWLENGTH) AS SUMGWLENGTH, "+
				"                    COUNT(*) AS TOTALCOUNTGW "+
				"               FROM ECC_IEU_UNITINFO T1 "+
				"              WHERE T1.UNITTYPE = '"+EnumUtil.OWNERTYPE4+"' and T1.status='"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"              GROUP BY T1.UNITID) A "+
				"    ON A.UNITID = T.ID "+
				"LEFT JOIN (SELECT E.UNITID, "+
				"                    CHANGEDATE CHANGEDATE, "+
				"                    MJHEATAREAH MJHEATAREAH, "+
				"                    GJHEATAREAH GJHEATAREAH, "+
				"                    GJHEATAREA GJHEATAREA, "+
				"                    MJHEATAREA MJHEATAREA, "+
				"                    E.MJHEATAREAH + E.GJHEATAREAH + E.GJHEATAREA + "+
				"                        E.MJHEATAREA AS TOTALHEATAREA "+
				"               FROM ECC_IEU_UNITINFO_DETAILS E  "+
				"               WHERE (e.unitid,e.changedate) IN (SELECT unitid,max(changedate) FROM ECC_IEU_UNITINFO_DETAILS GROUP BY unitid )) B "+
				"    ON T.ID = B.UNITID "+
				"  LEFT JOIN (SELECT Z.UNITID, COUNT(*) HEATCOUNT "+
				"               FROM ECC_IEU_UNITINFO Z where unittype='"+EnumUtil.OWNERTYPE3+"' "+
				"              GROUP BY Z.UNITID) F "+
				"    ON F.UNITID = T.ID "+
				" WHERE T.UNITTYPE = '"+EnumUtil.OWNERTYPE2+"' "+
				"   AND T.ID = ? ";
		
		rsUnitInfo= (UnitInfo)jdbcTemplate.queryForObject(sql,new Object[] { id }, new RowMapper() {
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
	 * 实现说明：查询热源条数 . <BR>
	 */   
	public int getDetailsCount(UnitInfo ui) {
		String sql = "select count(1) from (SELECT T.ID, "+
				"       T.UNITCODE,  ny_unitname(t.Unitid) orgname,"+
				"       T.UNITNAME, "+
				"       T.SHORTNAME, "+
				"       T.JIANPIN, "+
				"       T.HEATINGTYPE, "+
				"       T.ONWERTYPE, "+
				"       T.SEQ, "+
				"       T.STATUS, "+
				"       SUMGWLENGTH, "+
				"       TOTALCOUNTGW, "+
				"       MJHEATAREAH, "+
				"       GJHEATAREAH, "+
				"       MJHEATAREA, "+
				"       GJHEATAREA, "+
				"       TOTALHEATAREA, "+
				"       HEATCOUNT, "+
				"       CHANGEDATE "+
				"  FROM ECC_IEU_UNITINFO T "+
				"  LEFT JOIN (SELECT T1.UNITID, "+
				"                    SUM(GWLENGTH) AS SUMGWLENGTH, "+
				"                    COUNT(*) AS TOTALCOUNTGW "+
				"               FROM ECC_IEU_UNITINFO T1 "+
				"              WHERE T1.UNITTYPE = '"+EnumUtil.OWNERTYPE4+"' and T1.status='"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"              GROUP BY T1.UNITID) A "+
				"    ON A.UNITID = T.ID "+
				"LEFT JOIN (SELECT E.UNITID, "+
				"                    CHANGEDATE CHANGEDATE, "+
				"                    MJHEATAREAH MJHEATAREAH, "+
				"                    GJHEATAREAH GJHEATAREAH, "+
				"                    GJHEATAREA GJHEATAREA, "+
				"                    MJHEATAREA MJHEATAREA, "+
				"                    E.MJHEATAREAH + E.GJHEATAREAH + E.GJHEATAREA + "+
				"                        E.MJHEATAREA AS TOTALHEATAREA "+
				"               FROM ECC_IEU_UNITINFO_DETAILS E  "+
				"               WHERE (e.unitid,e.changedate) IN (SELECT unitid,max(changedate) FROM ECC_IEU_UNITINFO_DETAILS where status='"+EnumUtil.PARAMETERSSTATUS1+"' GROUP BY unitid )) B "+
				"    ON T.ID = B.UNITID "+
				"  LEFT JOIN (SELECT Z.UNITID, COUNT(*) HEATCOUNT "+
				"               FROM ECC_IEU_UNITINFO Z where unittype='"+EnumUtil.OWNERTYPE3+"' "+
				"              GROUP BY Z.UNITID) F "+
				"    ON F.UNITID = T.ID "+
				" WHERE T.UNITTYPE = '"+EnumUtil.OWNERTYPE2+"' and t.ORGID in (select ID FROM sys_org m start with m.ID='"+ui.getOrgId()+"' connect by m.PARENTID=prior m.ID )";
		if(ui.getUnitCode()!=null&&!"".equals(ui.getUnitCode())) sql = sql + " and t.unitcode like '%"+ui.getUnitCode()+"%'";
		if(ui.getUnitName()!=null&&!"".equals(ui.getUnitName())) sql = sql + " and t.unitname like '%"+ui.getUnitName()+"%'";
		if(ui.getShortName()!=null&&!"".equals(ui.getShortName())) sql = sql + " and t.shortname like '%"+ui.getShortName()+"%'";
		if(ui.getJianPin()!=null&&!"".equals(ui.getJianPin())) sql = sql + " and t.jianpin like '%"+ui.getJianPin()+"%'";
		if(ui.getHeatingType()!=null&&!"".equals(ui.getHeatingType())) sql = sql + " and t.heatingtype like '%"+ui.getHeatingType()+"%'";
		if(ui.getUnitType()!=null && !"".equals(ui.getUnitType())) sql = sql + " and t.unittype like '%"+ui.getUnitType()+"%'";
	    if(ui.getStatus()!=null && !"".equals(ui.getStatus())) sql = sql + " and t.Status = '"+ui.getStatus()+"'";
	 
		sql += "order by seq)";
	    int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	
	/**
	 * 实现说明：保存热源信息 . <BR>
	 */
	@Override
	public int saveDetails(UnitInfo ui) {
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
	
	public UnitInfo getParentId(UnitInfo ui){
		String id="";
		boolean flag=false;
		String sql="SELECT a.id,a.orgname,a.parentid,A.MANAGERTYPE,CASE WHEN b.id IS NOT NULL THEN 1 ELSE 0 END hasieu "+
				"  FROM (SELECT * "+
				"          FROM SYS_ORG T "+
				"        CONNECT BY PRIOR T.PARENTID = T.ID "+
				"         START WITH ID = '"+ui.getOrgId()+"') a, "+
				"       (SELECT * FROM   ecc_ieu_unitinfo WHERE unittype=1) b "+
				" WHERE ISDELETE = '"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"   AND ORGTYPE = '"+EnumUtil.ORGTYPE1+"' "+
				"   AND b.orgid (+)=a.id ";

		
		List list=jdbcTemplate.queryForList(sql);
		if(list!=null&&list.size()>0){
			String parentid="";
			for(int i=0;i<list.size();i++){
				HashMap map=(HashMap)list.get(i);
				if((map.get("ID")+"").equals(ui.getOrgId())){
					parentid=map.get("PARENTID")+"";
					list.remove(i);
					break;
				}
			}
			    int i=0;
				while(!flag){
					
					HashMap map=(HashMap)list.get(i);
					if((map.get("ID")+"").equals(parentid)){
						parentid=map.get("PARENTID")+"";
						if((map.get("MANAGERTYPE")+"").equals("1")&&(map.get("HASIEU")+"").equals("1")){
							flag=true;
							id=map.get("ID")+"";
							break;
						}
						list.remove(i);
						if(list.size()==0){
							id = "1";
							break;
						}
						i=0;
					}else{
						if(i==list.size()){
							flag=true;
						}
						i++;
					}
					
				}
			
			
		}
		sql="select * from  ecc_ieu_unitinfo where unittype=1 and orgid='"+id+"'";
		List listUi=jdbcTemplate.queryForList(sql);
		if(listUi!=null&&listUi.size()==1){
			HashMap map=(HashMap)listUi.get(0);
			ui.setUnitId(""+map.get("ID"));
		}else{
			ui.setUnitId("0");
		}
		return ui;
		
	}
	
	/**
	 * 方法说明：查询机构信息 . <BR>
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
	 * 实现说明：通过单位id查询单位详细信息 . <BR>
	 */
	@Override
	public Map findDetailsByUnitId(String id, int currentPage, int pageSize) {
		
		String sql = "select * from ecc_ieu_unitinfo_details t where t.unitid='"+id+"' order by t.CHANGEDATE desc";
		
		return super.queryGridist(sql, "", currentPage, pageSize);
	}
	
	/**
	 * 实现说明：批量保存单位详细信息 . <BR>
	 */
	@Override
	public int saveDetailsBatch(List<JSONObject> list,String unitId,User user, Org org) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		for(int i=0;i<list.size();i++){
			String uuid=UUIDGenerator.getUUID();
			if(list.get(i).get("ID")==null || "".equals(list.get(i).get("ID"))){//新增数据
				String sql = "insert into ecc_ieu_unitinfo_details";
				String sql1 = "(ID,UNITID,CREATEUSERID,CREATEUSER,CREATEUSERDEPARTMENTID,CREATEUSERDEPARTMENT,CREATEDATE ";
				String sql2 = "('"+uuid+"','"+unitId+"','"+user.getId()+"','"+user.getUserName()+"','"+org.getId()+"','"+org.getOrgName()+"','"+df.format(new Date())+"'";
				if(list.get(i).get("STATUS")!=null && !"".equals(list.get(i).get("STATUS"))){
					sql1 += ",STATUS"; sql2 += ",'"+list.get(i).get("STATUS")+"'";
				}
				//采暖面积
				if(list.get(i).get("CNMJ")!=null && !"".equals(list.get(i).get("CNMJ"))){
					sql1 += ",CNMJ"; sql2 += ",'"+list.get(i).get("CNMJ")+"'";
				}
				if(list.get(i).get("CHANGEDATE")!=null && !"".equals(list.get(i).get("CHANGEDATE"))){
					sql1 += ",CHANGEDATE"; sql2 += ",'"+list.get(i).get("CHANGEDATE")+"'";
				}
				if(list.get(i).get("MJHEATAREA")!=null && !"".equals(list.get(i).get("MJHEATAREA"))){
					sql1 += ",MJHEATAREA"; sql2 += ","+list.get(i).get("MJHEATAREA");
				} 
				if(list.get(i).get("GJHEATAREA")!=null && !"".equals(list.get(i).get("GJHEATAREA"))){
					sql1 += ",GJHEATAREA"; sql2 += ","+list.get(i).get("GJHEATAREA");
				} 
				if(list.get(i).get("MJHEATAREAH")!=null && !"".equals(list.get(i).get("MJHEATAREAH"))){
					sql1 += ",MJHEATAREAH"; sql2 += ","+list.get(i).get("MJHEATAREAH");
				} 
				if(list.get(i).get("GJHEATAREAH")!=null && !"".equals(list.get(i).get("GJHEATAREAH"))){
					sql1 += ",GJHEATAREAH"; sql2 += ","+list.get(i).get("GJHEATAREAH");
				} 
				if(list.get(i).get("REMARKS")!=null && !"".equals(list.get(i).get("REMARKS"))){
					sql1 += ",REMARKS"; sql2 += ",'"+list.get(i).get("REMARKS")+"'";
				} 
				sql+= sql1+" ) VALUES "+sql2+")";
				
				jdbcTemplate.execute(sql);
				if(list.get(i).get("STATUS")!=null && "1".equals(list.get(i).get("STATUS"))){
					if(list.get(i).get("CNMJ")!=null && !"".equals(list.get(i).get("CNMJ"))){
						String ssql="update ecc_ieu_unitinfo set cnmj='"+list.get(i).get("CNMJ")+"' where id='"+unitId+"'";
						jdbcTemplate.execute(ssql);
					}
				}else{
					String ssql="update ecc_ieu_unitinfo set cnmj='' where id='"+unitId+"'";
					jdbcTemplate.execute(ssql);
				}
			}else{
				String sql = "UPDATE ecc_ieu_unitinfo_details SET UPDATEUSERID='"+user.getId()+"',UPDATEUSER='"+user.getUserName()//CHANGEDATE
						+"',UPDATEUSERDEPARTMENTID='"+org.getId()+"',UPDATEUSERDEPARTMENT='"+org.getOrgName()+"',UPDATEDATE='"+df.format(new Date())+"'";
				if(list.get(i).get("CHANGEDATE")!=null && !"".equals(list.get(i).get("CHANGEDATE"))){
					sql += ",CHANGEDATE='"+list.get(i).get("CHANGEDATE")+"'";
				} 
				//采暖面积
				if(list.get(i).get("CNMJ")!=null && !"".equals(list.get(i).get("CNMJ"))){
					sql += ",CNMJ='"+list.get(i).get("CNMJ")+"'";
				}
				if(list.get(i).get("MJHEATAREA")!=null && !"".equals(list.get(i).get("MJHEATAREA"))){
					sql += ",MJHEATAREA="+list.get(i).get("MJHEATAREA");
				} 
				if(list.get(i).get("GJHEATAREA")!=null && !"".equals(list.get(i).get("GJHEATAREA"))){
					sql += ",GJHEATAREA="+list.get(i).get("GJHEATAREA");
				} 
				if(list.get(i).get("MJHEATAREAH")!=null && !"".equals(list.get(i).get("MJHEATAREAH"))){
					sql += ",MJHEATAREAH="+list.get(i).get("MJHEATAREAH");
				} 
				if(list.get(i).get("GJHEATAREAH")!=null && !"".equals(list.get(i).get("GJHEATAREAH"))){
					sql += ",GJHEATAREAH="+list.get(i).get("GJHEATAREAH");
				} 
				if(list.get(i).get("REMARKS")!=null && !"".equals(list.get(i).get("REMARKS"))){
					sql += ",REMARKS='"+list.get(i).get("REMARKS")+"'";
				} else{
					sql += ",REMARKS=''";//如果什么都不写，备注会自动将对象转换成null插入数据库，如果有值则插入值
				}
				if(list.get(i).get("STATUS")!=null && !"".equals(list.get(i).get("STATUS"))){
					sql += ",STATUS='"+list.get(i).get("STATUS")+"'";
				}
				sql+= " where ID='"+list.get(i).get("ID")+"'";
				jdbcTemplate.execute(sql);
				if(list.get(i).get("STATUS")!=null && "1".equals(list.get(i).get("STATUS"))){
					if(list.get(i).get("CNMJ")!=null && !"".equals(list.get(i).get("CNMJ"))){
						String ssql="update ecc_ieu_unitinfo set cnmj='"+list.get(i).get("CNMJ")+"' where id='"+unitId+"'";
						jdbcTemplate.execute(ssql);
					}
				}else{
					String ssql="update ecc_ieu_unitinfo set cnmj='' where id='"+unitId+"'";
					jdbcTemplate.execute(ssql);
				}
			}
		}
		return 0;
	}
	
	/**
	 * 实现说明：删除子信息 . <BR>
	 */
	@Override
	public int deleteChildInfo(List<JSONObject> list) {
		
		for(int i=0;i<list.size();i++){
				String sql = "delete from ecc_ieu_unitinfo_details ";
				sql+="  where id= '"+list.get(i).get("ID")+"'";
				jdbcTemplate.execute(sql);
			
		}
		return 0;
	}
	
	@Override
	public int checkBydate(String changeDate,String unitid,String id) {
		String idd = "";
		String idCheck="";
		if(id==null){
			id="";
		}
		if(id!=null){
			idd = id.replaceAll(",", "','");
		}
		for(int g=0;g<id.split(",").length;g++){
			if(id.split(",")[g]!=null){
				idCheck=id.split(",")[g];
			}
		}
		String sql = "select id from ECC_IEU_UNITINFO_DETAILS d where d.status='"+EnumUtil.PARAMETERSSTATUS1+"' and d.changeDate='"+changeDate+"' and d.UNITID = '"+unitid+"' ";
		if(!idCheck.equals("")&&idCheck!=null){
			sql+=" and id not in ('"+idd+"')";
		}
	   List list = jdbcTemplate.queryForList(sql);
	   return list.size();
	}
	
}
