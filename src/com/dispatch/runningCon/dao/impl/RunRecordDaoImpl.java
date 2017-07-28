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
			String sTime,String eTime,String dName) {
		String sql = "SELECT                                                         "
		+"	B.KGLPRE DNAME,B.\"T\",B.\"TYPE\" AT,B.AB,                                  "
		+"	(                                                                        "
		+"		CASE                                                                 "
		+"		WHEN B.BPYX = '1'                                                    "
		+"		OR B.GPYX = '1' THEN                                                 "
		+"      '1'                                                                  "
		+"    ELSE                                                                   "
		+"      '0'                                                                  "
		+"    END                                                                    "
		+"  ) YXSTATUS,                                                              "
		+"  (                                                                        "
		+"    CASE                                                                   "
		+"    WHEN B.BPGZ = '1'                                                      "
		+"    AND B.GPGZ = '1' THEN                                                  "
		+"      '1'                                                                  "
		+"    ELSE                                                                   "
		+"      '0'                                                                  "
		+"    END                                                                    "
		+"  ) GZSTATUS                                                               "
		+"FROM                                                                       "
		+"  (                                                                        "
		+"    SELECT                                                                 "
		+"      A.KGLPRE,A.\"T\",A.\"TYPE\",A.AB,                                    "
		+"      MAX (                                                                "
		+"        DECODE (A .YX, 'bpgz', A .V, '0')                                  "
		+"      ) BPGZ,                                                              "
		+"      MAX (                                                                "
		+"        DECODE (A .YX, 'bpyx', A .V, '0')                                  "
		+"      ) BPYX,                                                              "
		+"      MAX (                                                                "
		+"        DECODE (A .YX, 'gpgz', A .V, '0')                                  "
		+"      ) GPGZ,                                                              "
		+"      MAX (                                                                "
		+"        DECODE (A .YX, 'gpyx', A .V, '0')                                  "
		+"      ) GPYX                                                               "
		+"    FROM                                                                   "
		+"      (                                                                    "
		+"        SELECT                                                             "
		+"          (                                                                "
		+"            CASE SUBSTR (\"K\",INSTR (\"K\", 'fj', 1, 1) + 2,1)            "
		+"            WHEN 'b' THEN                                                  "
		+"              'bp' || SUBSTR (\"K\", LENGTH(\"K\") - 1)                    "
		+"            WHEN 'g' THEN                                                  "
		+"              'gp' || SUBSTR (\"K\", LENGTH(\"K\") - 1)                    "
		+"            END                                                            "
		+"          ) yx,                                                            "
		+"          SUBSTR (\"K\", 0, INSTR(\"K\", '_' ,- 1) - 1) kglpre,            "
		+"          \"T\",\"K\",V,\"TYPE\",AB                                        "
		+"        FROM                                                               "
		+"          DI_KV                                                            "
		+"      ) A                                                                  "
		+"    WHERE 1=1                                                              ";
		if(null!=sTime&&!"".equals(sTime)){
			sql+=" and TO_CHAR(A.\"T\",'yyyy-MM-dd hh24:mm:ss')>='"+sTime+"'";
		}
		if(null!=sTime&&!"".equals(sTime)){
			sql+=" and TO_CHAR(A.\"T\",'yyyy-MM-dd hh24:mm:ss')<='"+eTime+"'";
		}
		if(null!=dName&&!"".equals(dName)){
			sql += "and A.AB = '"+dName.substring(0,1)+"' and A.\"TYPE\" = '"+dName.substring(1,2)+"'";
		}
		sql += "    GROUP BY                                                         "
		+"      A.KGLPRE,                                                            "
		+"      A.\"T\",                                                             "
		+"      A.\"TYPE\",                                                          "
		+"      A.AB                                                                 "
		+"  ) B                                                                      "
		+"ORDER BY                                                                   "
		+"  B.\"T\" DESC		                                                     ";
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
