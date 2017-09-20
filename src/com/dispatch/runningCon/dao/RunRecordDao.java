package com.dispatch.runningCon.dao;

import java.util.List;
import java.util.Map;

import com.dispatch.runningCon.bean.RepairRecord;

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

	/**
	 * 天气json
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> weatherJson(String param);

	/**
	 * 平均气温曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> tempTrend(String startDate, String endDate);

	/**
	 * 首页top气象曲线
	 * @return
	 */
	Map<String, Object> indexParams();

	/**
	 * 新增维修记录
	 * @param rr
	 * @return
	 */
	Map<String, Object> insertRR(RepairRecord rr);

	/**
	 * 修改维修记录
	 * @param rr
	 * @return
	 */
	Map<String, Object> updateRR(RepairRecord rr);

	/**
	 * 删除维修记录
	 * @param in
	 * @return
	 */
	Map<String, Object> deleteRR(String in);

	/**
	 * 分页查询维修记录
	 * @param currentPage
	 * @param pageSize
	 * @param sTime
	 * @param eTime
	 * @param dName
	 * @return
	 */
	Map<String, Object> queryRRs(int currentPage, int pageSize, String sTime,
			String eTime, String dName);
}
