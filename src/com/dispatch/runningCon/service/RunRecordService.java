package com.dispatch.runningCon.service;

import java.util.Map;

public interface RunRecordService {

	/**
	 * 查询运行日志list
	 * @param currentPage
	 * @param pageSize
	 * @param nodeId
	 * @return
	 */
	Map<String, Object> queryRunRecord(int currentPage, int pageSize,
			String sTime,String eTime,String dName);

	/**
	 * 查询日志分页信息
	 * @param currentPage 当前页
	 * @param pageSize 一页显示几条数据
	 * @param param 查询参数
	 * @return 
	 */
	Map<String, Object> queryLogList(int currentPage, int pageSize,
			Map<String, String> param);

	Map<String, Object> weatherJson(int currentPage, int pageSize,
			String param);

	Map<String, Object> avgTemp(String curDate);

	Map<String, Object> tempTrend(String startDate, String endDate) throws Exception;
}
