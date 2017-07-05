package com.dispatch.runningCon.dao.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dispatch.runningCon.dao.RunRecordDao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class RunRecordDaoImpl extends PageListJdbcTemplate implements RunRecordDao {

	@Override
	public Map<String, Object> queryRunRecord(int currentPage, int pageSize,
			String nodeId) {
		String sql = "SELECT B.KGLPRE DNAME, B.\"TIME\", B.REMARK,( CASE WHEN B.BPYX = '1' OR B.GPYX = '1' THEN '1' ELSE '0' END)"
				+ " YXSTATUS, ( CASE WHEN B.BPGZ = '1' AND B.GPGZ = '1' THEN '1' ELSE '0' END ) GZSTATUS FROM ( SELECT A .KGLPRE,"
				+ " A .\"TIME\", A.REMARK, MAX ( DECODE ( A .YX, 'bpgz', A .POINTVALUE, '0' ) ) BPGZ, MAX ( DECODE ( A .YX, 'bpyx',"
				+ " A .POINTVALUE, '0' ) ) BPYX, MAX ( DECODE ( A .YX, 'gpgz', A .POINTVALUE, '0' ) ) GPGZ, MAX ( DECODE ( A .YX, "
				+ "'gpyx', A .POINTVALUE, '0' ) ) GPYX FROM ( SELECT SUBSTR ( K .POINTNAME, LENGTH (K .POINTNAME) - 3 ) yx, SUBSTR "
				+ "( K .POINTNAME, INSTR (K .POINTNAME, '\\', 1, 3) + 1, ( INSTR (K .POINTNAME, '\\', 1, 4) - INSTR (K .POINTNAME, "
				+ "'\\', 1, 3) - 1 ) ) kglpre, SUBSTR(K.POINTNAME,0,(LENGTH (K .POINTNAME)-LENGTH(SUBSTR(K.POINTNAME,INSTR "
				+ "(K .POINTNAME, '\\', 1, 4))))) REMARK, K .* FROM KGL1KV K ) A GROUP BY A .KGLPRE, A .\"TIME\", A.REMARK ) B";
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

}
