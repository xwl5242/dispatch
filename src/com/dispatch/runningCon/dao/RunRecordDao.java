package com.dispatch.runningCon.dao;

import java.util.List;
import java.util.Map;

public interface RunRecordDao {

	/**
	 * 查询运行日志list
	 * @param currentPage
	 * @param pageSize
	 * @param nodeId
	 * @return
	 */
	Map<String, Object> queryRunRecord(int currentPage, int pageSize,
			String sTime,String eTime,String dName,String kName);

	/**
	 * 查询日志分页信息
	 * @param currentPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	Map<String, Object> queryLogList(int currentPage, int pageSize,
			Map<String, String> param);

	List<Map<String, Object>> weatherJson(String param);

	List<Map<String, Object>> tempTrend(String startDate, String endDate);

	Map<String, Object> indexParams();
}
