package com.dispatch.unit.unitInfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.dao.IHeatStationDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.SpringUtil;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;

/**
 * 功能描述：热力站数据信息  . 
 */
@Repository
public class HeatStationDaoImpl extends PageListJdbcTemplate implements IHeatStationDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static UnitInfo info = new UnitInfo();
	
	/**
	 * 实现说明：通过id查询单位信息 . <BR>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UnitInfo findUnitInfoById(String id) {
		String sql="SELECT T.ID,t.unitid,DETAILSID,"+
				"       T.UNITCODE, ny_orgname(T.ORGID) orgname,"+
				"       T.UNITNAME, "+
				"       T.SHORTNAME, "+
				"       T.JIANPIN, "+
				"       T.HEATINGTYPE, "+
				"       T.ONWERTYPE, "+
				"       T.SEQ, "+
				"       T.STATUS, "+
				"       T.MANAGETYPE, "+
				"       GWLENGTHS, "+
				"       GWCOUNT, "+
				"       GJHEATAREA, "+
				"       MJHEATAREA, "+
				"       GJHEATAREAH, "+
				"       MJHEATAREAH, "+
				"       HEATNUMH, "+
				"       HEATNUM, "+
				"       GJHEATNUMH, "+
				"       GJHEATNUM, "+
				"       MJGJSUM, "+
				"       T.LONGITUDE, "+
				"       T.LATITUDE,t.remarks "+
				"  FROM ECC_IEU_UNITINFO T "+
				"  LEFT JOIN (SELECT T1.UNITID, "+
				"                    SUM(GWLENGTH) AS GWLENGTHS, "+
				"                    COUNT(*) AS GWCOUNT "+
				"               FROM ECC_IEU_UNITINFO T1 "+
				"              WHERE T1.UNITTYPE = '"+EnumUtil.OWNERTYPE5+"' and status='"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"              GROUP BY T1.UNITID) A "+
				"    ON A.UNITID = T.ID "+
				"  LEFT JOIN (SELECT E.UNITID,id DETAILSID , "+
				"                    (E.GJHEATNUMH) GJHEATNUMH, "+
				"                    (E.HEATNUMH) HEATNUMH, "+
				"                    (E.HEATNUM) HEATNUM, "+
				"                    (E.GJHEATNUM) GJHEATNUM, "+
				"                    (MJHEATAREA) MJHEATAREA, "+
				"                    (GJHEATAREA) GJHEATAREA, "+
				"                    (MJHEATAREAH) MJHEATAREAH, "+
				"                    (GJHEATAREAH) GJHEATAREAH, "+
				"                    (E.MJHEATAREA + E.GJHEATAREA + E.MJHEATAREAH + "+
				"                        E.GJHEATAREAH) AS MJGJSUM "+
				"               FROM ECC_IEU_UNITINFO_DETAILS E  "+
				"               WHERE (E.UNITID, E.CHANGEDATE) IN "+
				"                           (SELECT UNITID, MAX(CHANGEDATE) "+
				"                              FROM ECC_IEU_UNITINFO_DETAILS  WHERE  status='"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"                             GROUP BY UNITID) AND  status='"+EnumUtil.PARAMETERSSTATUS1+"') B "+
				"    ON T.ID = B.UNITID "+
				" WHERE (T.ONWERTYPE = '"+EnumUtil.OWNERTYPE1+"' OR T.ONWERTYPE = '"+EnumUtil.OWNERTYPE2+"') "+
				"   AND T.UNITTYPE = '"+EnumUtil.OWNERTYPE3+"'  "+
				"   AND T.ID = ? ";
		
		UnitInfo unitInfo =null;
		List<UnitInfo> list = (List) super.query(sql, new Object[] { id },
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						try {
							ECCBeanUtil.resultSetToBean(rs, info);
						} catch (Exception e) {
							e.printStackTrace();
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
	 * 实现说明：修改热力站信息 . <BR>
	 */
	@Override
	public int updateHeatStation(UnitInfo ui, List<JSONObject> list) {
		int n = 0;
		if(ui!=null){ 
			String sql = "UPDATE ECC_IEU_UNITINFO SET MANAGETYPE='"+ui.getManageType()+"',HEATINGTYPE='"+ui.getHeatingType()+"',updateuserid='"+ui.getUpdateUserID()+"',"
					+ "updateuser='"+ui.getUpdateUser()+"',updateuserdepartmentid='"+ui.getUpdateUserDepartmentID()+"',updateuserdepartment='"+ui.getUpdateUserDepartment()+"',updatedate='"+ui.getUpdateDate()
					+ "' where id='"+ui.getId()+"'";
			jdbcTemplate.execute(sql);
			String sqlDetail = "UPDATE ECC_IEU_UNITINFO_DETAILS SET MJHEATAREA=?,GJHEATAREA=?,MJHEATAREAH=?,GJHEATAREAH=?,GJHEATNUM=?,GJHEATNUMH=?,"
					+ "HEATNUMH=?,HEATNUM=?,updateuserid=?,updateuser=?,updateuserdepartmentid=?,"
					+ "updateuserdepartment=?,updatedate=? where id=?";
			n =	jdbcTemplate.update(sqlDetail,new Object[] { ui.getMjHeatarea(),ui.getGjheatarea(),ui.getMjHeatareaH(),ui.getGjheatareaH(),ui.getGjHeatNum(),ui.getGjHeatNumH(),ui.getHeatnumH(),ui.getHeatnum(),ui.getUpdateUserID(),ui.getUpdateUser(),ui.getUpdateUserDepartmentID(),ui.getUpdateUserDepartment(),ui.getUpdateDate(),ui.getDetailsId()});
			if(list != null){
				for(int i=0;i<list.size();i++){
					String uuidzl=UUIDGenerator.getUUID();
					if(list.get(i).get("ID")==null || "".equals(list.get(i).get("ID"))){//新增数据
						String sqlzl = "insert into ECC_IEU_UNITINFO_ZL";
						String sql1 = "(ID,UNITID,CREATEUSERID,CREATEUSER,CREATEUSERDEPARTMENTID,CREATEUSERDEPARTMENT,CREATEDATE ";
						String sql2 = "('"+uuidzl+"','"+ui.getId()+"','"+ui.getCreateUserID()+"','"+ui.getCreateUser()+"','"+ui.getCreateUserDepartmentID()+"','"+ui.getCreateUserDepartment()+"','"+ui.getCreateDate()+"'";
						if(list.get(i).get("ZLNAME")!=null && !"".equals(list.get(i).get("ZLNAME"))){
							sql1 += ",ZLNAME"; sql2 += ",'"+list.get(i).get("ZLNAME")+"'";
						}
						if(list.get(i).get("SEQ")!=null && !"".equals(list.get(i).get("SEQ"))){
							sql1 += ",SEQ"; sql2 += ","+list.get(i).get("SEQ");
						} 
						if(list.get(i).get("STATUS")!=null && !"".equals(list.get(i).get("STATUS"))){
							sql1 += ",STATUS"; sql2 += ",'"+list.get(i).get("STATUS")+"'";
						}
						sqlzl+= sql1+" ) VALUES "+sql2+")";
						jdbcTemplate.execute(sqlzl);
					}else{
						String sqlzl = "UPDATE ECC_IEU_UNITINFO_ZL SET UPDATEUSERID='"+ui.getUpdateUserID()+"',UPDATEUSER='"+ui.getUpdateUser()
								+"',UPDATEUSERDEPARTMENTID='"+ui.getUpdateUserDepartmentID()+"',UPDATEUSERDEPARTMENT='"+ui.getUpdateUserDepartment()+"',UPDATEDATE='"+ui.getUpdateDate()+"'";
						if(list.get(i).get("ZLNAME")!=null && !"".equals(list.get(i).get("ZLNAME"))){
							sqlzl += ",ZLNAME='"+list.get(i).get("ZLNAME")+"'"; 
						}
						if(list.get(i).get("SEQ")!=null && !"".equals(list.get(i).get("SEQ"))){
							sqlzl += ",SEQ="+list.get(i).get("SEQ");
						} 
						if(list.get(i).get("STATUS")!=null && !"".equals(list.get(i).get("STATUS"))){
							sqlzl += ",STATUS='"+list.get(i).get("STATUS")+"'"; 
						}
						sqlzl+= " where ID='"+list.get(i).get("ID")+"'";
						jdbcTemplate.execute(sqlzl);
					}
				}
			}
		}else{
			return 0;
		}
		return n;
	}

}
