package com.dispatch.runningCon.dao;

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
			String nodeId);

	/**
	 * 查询日志分页信息
	 * @param currentPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	Map<String, Object> queryLogList(int currentPage, int pageSize,
			Map<String, String> param);
}
