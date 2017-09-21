package com.dispatch.runningCon.service;

import java.util.Map;

import com.dispatch.runningCon.bean.RepairRecord;

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

	/**
	 * 天气参数table数据
	 * @param currentPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	Map<String, Object> weatherJson(int currentPage, int pageSize,
			String param);

	/**
	 * 平均气温曲线
	 * @param curDate
	 * @return
	 */
	Map<String, Object> avgTemp(String curDate);

	/**
	 * 气温曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> tempTrend(String startDate, String endDate) throws Exception;

	/**
	 * 首页top上 气象参数信息
	 * @return
	 */
	Map<String, Object> indexParams();

	/**
	 * 维修记录分页信息
	 * @param currentPage
	 * @param pageSize
	 * @param sTime
	 * @param eTime
	 * @param dName
	 * @return
	 */
	Map<String, Object> repairRecords(int currentPage, int pageSize,
			String sTime, String eTime, String dName);

	/**
	 * 新增维修记录
	 * @param rr
	 * @return
	 */
	Map<String, Object> addRR(RepairRecord rr);

	/**
	 * 修改维修记录
	 * @param rr
	 * @return
	 */
	Map<String, Object> editRR(RepairRecord rr);

	/**
	 * 删除维修记录
	 * @param ids
	 * @return
	 */
	Map<String, Object> removeRR(String ids);
}
