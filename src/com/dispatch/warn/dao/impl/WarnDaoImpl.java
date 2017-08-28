package com.dispatch.warn.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dispatch.warn.dao.WarnDao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class WarnDaoImpl extends PageListJdbcTemplate implements WarnDao {

	@Override
	public Map<String, Object> listWarnData(String sTime, String eTime,
			String pointName, String status,int currentPage, int pageSize) {
		String sql ="SELECT * FROM( SELECT r.*, '1' status, "
				+ "SUBSTR ( \"AlarmContent\", INSTR (\"AlarmContent\", '\\', 1, 1) + 1, "
				+ "( INSTR (\"AlarmContent\", ' ', 1, 1) - INSTR (\"AlarmContent\", '\\', 1, 1)) ) pointName "
				+ "FROM ALARM_REAL r UNION ALL SELECT H .*, '2' status, SUBSTR ( \"AlarmContent\", "
				+ "INSTR (\"AlarmContent\", '\\', 1, 1) + 1, ( INSTR (\"AlarmContent\", ' ', 1, 1) - "
				+ "INSTR (\"AlarmContent\", '\\', 1, 1) ) ) pointName FROM ALARM_HISTORY H ) A WHERE 1 = 1 ";
		if(null!=status&&!"".equals(status)){
			sql += " and A.status = '"+status+"'";
		}
		if(null!=sTime&&!"".equals(sTime)){
			sql += " and A.\"AlarmStartTime\">='"+sTime+"'";
		}
		if(null!=eTime&&!"".equals(eTime)){
			sql += " and A.\"AlarmEndTime\" <= '"+eTime+"'";
		}
		if(null!=pointName&&!"".equals(pointName)){
			sql += " and A.pointName LIKE '%"+pointName+"%'";
		}
		sql+=" ORDER BY 	A .status ASC";
		return super.queryGridist(sql, "", currentPage, pageSize);
	}

	@Override
	public Map<String, Object> warnCount() {
		String sql = "select NVL(count(1),0) warnCount from ALARM_REAL";
		return super.queryForMap(sql);
	}

}
