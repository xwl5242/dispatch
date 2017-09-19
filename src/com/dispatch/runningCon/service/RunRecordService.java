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
			String sTime,String eTime,String dName,String kName);

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

	Map<String, Object> indexParams();

	Map<String, Object> repairRecords(int currentPage, int pageSize,
			String sTime, String eTime, String dName);

	Map<String, Object> addRR(int currentPage, int pageSize, String sysName,
			String dCode, String dName, String faultTime, String faultDesc,
			String repairTime, String repairMan, String confirmTime,
			String remark);

	Map<String, Object> editRR(int currentPage, int pageSize, String sysName,
			String dCode, String dName, String faultTime, String faultDesc,
			String repairTime, String repairMan, String confirmTime,
			String remark, String id);

	Map<String, Object> removeRR(String ids);
}
