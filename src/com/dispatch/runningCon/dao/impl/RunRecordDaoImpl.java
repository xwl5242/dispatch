package com.dispatch.runningCon.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dispatch.runningCon.dao.RunRecordDao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class RunRecordDaoImpl extends PageListJdbcTemplate implements RunRecordDao {

	@Override
	public Map<String, Object> queryRunRecord(int currentPage, int pageSize,
			String sTime,String eTime,String dName,String kName) {
		
		String sql = 
		 "SELECT       "
		+"	DNAME,     "
		+"	AB,        "
		+"	\"TYPE\" AT, "
		+"	ST,        "
		+"	ET         "
		+"FROM         "
		+"	DI_S_E_TIME"
		+" WHERE       "
		+"	1 = 1      ";
		if(null!=dName&&!"".equals(dName)){
			sql += " and AB = '"+dName.substring(0,1)+"' and \"TYPE\" = '"+dName.substring(1,2)+"'";
		}
		if(null!=sTime&&!"".equals(sTime)){
			sql+=" and TO_CHAR(ET,'yyyy-mm-dd hh24:mi:ss')>='"+sTime+"' ";
		}
		if(null!=sTime&&!"".equals(sTime)){
			sql+=" and TO_CHAR(ET,'yyyy-mm-dd hh24:mi:ss')<='"+eTime+"' ";
		}
		if(null!=kName&&!"".equals(kName)){
			sql+=" and DNAME LIKE '%"+kName+"%' ";
		}
		sql+=" ORDER BY AB,DNAME ASC";
		return super.queryGridist(sql, "", currentPage, pageSize);                   
	}
	
	/**
	 * 查询日志分页信息
	 */
	@Override
	public Map<String, Object> queryLogList(int currentPage, int pageSize,
			Map<String, String> param) {
		String sql = "select EVENTTYPE,EVENTKIND,EVENTTIME,PID,EVENTCONTENT,LOGONUSER,PCNAME from LOGFILE WHERE 1=1";
		if(StringUtils.isNotEmpty(param.get("eventType"))){
			sql += " and EVENTTYPE = '"+param.get("eventType")+"'";
		}
		if(StringUtils.isNotEmpty(param.get("startEventTime"))){
			sql += " and EVENTTIME >= '"+param.get("startEventTime")+"'";
		}
		if(StringUtils.isNotEmpty(param.get("endEventTime"))){
			sql += " and EVENTTIME <= '"+param.get("endEventTime")+"'";
		}
		if(StringUtils.isNotEmpty(param.get("logOnUser"))){
			sql += " and LOGONUSER like '%"+param.get("logOnUser")+"%'";
		}
		if(StringUtils.isNotEmpty(param.get("pcName"))){
			sql += " and PCNAME like '%"+param.get("pcName")+"%'";
		}
		return super.queryGridist(sql, "", currentPage, pageSize);
	}

	@Override
	public List<Map<String, Object>> weatherJson(String curDate) {
		
		String sql = "SELECT                                   "
		+"	C.HH,                                              "
		+"	MAX(DECODE(C.KN,'fs',C.KV,0)) fs,                  "
		+"	MAX(DECODE(C.KN,'fx',C.KV,0)) fx,                  "
		+"	MAX(DECODE(C.KN,'sd',C.KV,0)) sd,                  "
		+"	MAX(DECODE(C.KN,'wd',C.KV,0)) wd,                  "
		+"	MAX(DECODE(C.KN,'pm',C.KV,0)) pm                   "
		+"FROM                                                 "
		+"  (                                                  "
		+"    SELECT                                           "
		+"      B.HH,                                          "
		+"      SUBSTR (B.\"K\",LENGTH(B.\"K\")-1) KN,         "
		+"      ROUND (B.V,2) KV                               "
		+"    FROM                                             "
		+"      (                                              "
		+"        SELECT                                       "
		+"          TO_CHAR (A.\"T\",'hh24') HH,                  "
		+"          A.\"K\",                                   "
		+"          AVG(A.V) V                                 "
		+"        FROM                                         "
		+"          (                                          "
		+"            SELECT                                   "
		+"              \"T\",\"K\",\"TYPE\",                     "
		+"              MAX(V) V                               "
		+"            FROM                                     "
		+"              AI_KV                                  "
		+"            WHERE \"TYPE\"='tq' AND \"D\" = '"+curDate+"' "
		+"            GROUP BY                                 "
		+"              \"T\",\"K\",\"TYPE\"                      "
		+"          ) A                                        "
		+"        GROUP BY                                     "
		+"          TO_CHAR(A.\"T\",'hh24'),                      "
		+"          A.\"K\"                                    "
		+"      ) B                                            "
		+"  ) C                                                "
		+"GROUP BY C.HH                                        "
		+"ORDER BY C.HH                                        ";
		return super.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> tempTrend(String startDate, String endDate) {
		String sql = "SELECT                            "
		+"  \"D\" YD,                                   "
		+"  ROUND (MAX(A.V), 2) MAXT,                   "
		+"  ROUND (MIN(A.V), 2) MINT,                   "
		+"  ROUND (AVG(A.V), 2) AVGT                    "
		+" FROM                                         "
		+"  (                                           "
		+"    SELECT                                    "
		+"      \"D\",\"K\",\"TYPE\",                   "
		+"      MAX(to_number(V)) V                                "
		+"    FROM                                      "
		+"      AI_KV                                   "
		+"    WHERE \"TYPE\" = 'tq' AND \"D\" >= '"+startDate+"'" 
		+"		AND \"D\" <= '"+endDate+"'              "
		+ "AND INSTR(\"K\", 'wd', 1, 1) = LENGTH(\"K\") - 1 "
		+"    GROUP BY                                  "
		+"      \"D\",\"K\",\"TYPE\"                    "
		+"  ) A                                         "
		+"GROUP BY                                      "
		+"  \"D\"                                       "
		+"ORDER BY YD                                   ";
		return super.queryForList(sql);
	}

}
