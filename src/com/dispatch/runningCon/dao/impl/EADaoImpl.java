package com.dispatch.runningCon.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dispatch.runningCon.dao.EADao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class EADaoImpl extends PageListJdbcTemplate implements EADao {

	@Override
	public List<Map<String, Object>> tongbi(String type,String eaType) {
		String sql = "SELECT SUBSTR(D.YD, 1, 4) AS Y, D.AB, ";
		if("DH".equals(eaType)){
			sql += "ROUND(SUM (D .DL)/(select AREA from unitarea where unitcode = 'AB'),2) ";
		}else if("NH".equals(eaType)){
			sql += "SUM(D.DL) ";
		}
		sql += " AS DL FROM (SELECT B.YD, B.AB, B.POINTNAME, "
				+ "ROUND(B.TOTAL - NVL(C.TTVV, 0), 2) AS DL FROM (SELECT A.YD, A.AB, A.POINTNAME, MAX(A.TOTAL) AS TOTAL "
				+ "FROM (SELECT TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') AS YD, MAX(KV.POINTVALUE) AS TOTAL, KV.POINTNAME, KV.AITYPE, "
				+ "KV.AB FROM AIKV KV GROUP BY TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd'), KV.POINTNAME, KV.AITYPE, KV.AB HAVING "
				+ "KV.AITYPE IN ("+type+") ) A GROUP BY A.YD, A.AB, A.POINTNAME ) B LEFT JOIN (SELECT TO_CHAR(\"TIME\", "
				+ "'yyyy-MM-dd') AS TT, POINTNAME, MAX(POINTVALUE) AS TTVV FROM AIKV GROUP BY POINTNAME, TO_CHAR(\"TIME\", "
				+ "'yyyy-MM-dd') ) C ON TO_DATE(C.TT, 'yyyy-MM-dd') = TO_DATE(B.YD, 'yyyy-MM-dd') - 1 AND B.POINTNAME = "
				+ "C.POINTNAME ) D GROUP BY SUBSTR(D.YD, 1, 4), D.AB";
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> airTongbi1(String startDate, String endDate,String type) {
		String sql = "SELECT D.AITYPE, D.AB, SUM(D.DL) AS DL FROM (SELECT B.AITYPE, B.YD, B.AB, B.POINTNAME, "
				+ "ROUND(B.TOTAL - NVL(C.TTVV, 0), 2) AS DL FROM (SELECT TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') AS YD, "
				+ "MAX(KV.POINTVALUE) AS TOTAL, KV.POINTNAME, KV.AITYPE, KV.AB FROM AIKV KV GROUP BY "
				+ "TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd'), KV.POINTNAME, KV.AITYPE, KV.AB HAVING KV.AITYPE IN ("+type+") ";
				if(null!=startDate&&!"".equals(startDate)){
					sql += " AND TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') >= '"+startDate+"'";
				}
				if(null!=endDate&&!"".equals(endDate)){
					sql += " AND TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') <= '"+endDate+"'";
				}
				sql += ") B LEFT JOIN (SELECT TO_CHAR(\"TIME\", 'yyyy-MM-dd') AS TT, POINTNAME, MAX(POINTVALUE) AS TTVV "
				+ "FROM AIKV GROUP BY POINTNAME, TO_CHAR(\"TIME\", 'yyyy-MM-dd') ) C ON B.POINTNAME = C.POINTNAME "
				+ "AND TO_DATE(C.TT, 'yyyy-MM-dd') = TO_DATE(B.YD, 'yyyy-MM-dd') - 1 ) D GROUP BY D.AB, D.AITYPE";
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> qushi(String startDate, String endDate,String type,String eaType) {
		String sql = "SELECT D.YD, D.AB, ";
		if("DH".equals(eaType)){
			sql += "ROUND(SUM (D .DL)/(select AREA from unitarea where unitcode = 'AB'),2) ";
		}else if("NH".equals(eaType)){
			sql += "SUM(D.DL) ";
		}
		sql	 += " AS DL FROM (SELECT B.YD, B.AB, B.POINTNAME, "
				+ "ROUND(B.TOTAL - NVL(C.TTVV, 0), 2) AS DL FROM (SELECT A.YD, A.AB, A.POINTNAME, MAX(A.TOTAL) AS TOTAL "
				+ "FROM (SELECT TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') AS YD, MAX(KV.POINTVALUE) AS TOTAL, KV.POINTNAME, KV.AITYPE, "
				+ "KV.AB FROM AIKV KV GROUP BY TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd'), KV.POINTNAME, KV.AITYPE, KV.AB HAVING "
				+ "KV.AITYPE IN ("+type+") ";
				if(null!=startDate&&!"".equals(startDate)){
					sql += " AND TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') >= '"+startDate+"'";
				}
				if(null!=endDate&&!"".equals(endDate)){
					sql += " AND TO_CHAR(KV.\"TIME\", 'yyyy-MM-dd') <= '"+endDate+"'";
				}
				sql += ") A GROUP BY A.YD, A.AB, A.POINTNAME ) B LEFT JOIN (SELECT TO_CHAR(\"TIME\", 'yyyy-MM-dd') AS TT, "
				+ "POINTNAME, MAX(POINTVALUE) AS TTVV FROM AIKV GROUP BY POINTNAME, TO_CHAR(\"TIME\", 'yyyy-MM-dd') ) C "
				+ "ON TO_DATE(C.TT, 'yyyy-MM-dd') = TO_DATE(B.YD, 'yyyy-MM-dd') - 1 AND B.POINTNAME = C.POINTNAME ) D "
				+ "GROUP BY D.YD, D.AB";
		return super.queryForList(sql);
	}

}
