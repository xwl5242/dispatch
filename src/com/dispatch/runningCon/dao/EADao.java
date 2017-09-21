package com.dispatch.runningCon.dao;

import java.util.List;
import java.util.Map;


public interface EADao {

	/**
	 * 同比柱状图（A座，B座，总体）
	 * @param type
	 * @param eaType
	 * @return
	 */
	List<Map<String, Object>> tongbi(String type,String eaType);

	/**
	 * 柱状图
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> airTongbi1(String startDate, String endDate,String type);

	/**
	 * 所有曲线图
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param eaType
	 * @return
	 */
	List<Map<String, Object>> qushi(String startDate, String endDate,String type,String eaType);

	/**
	 * 饼图
	 * @param startDate
	 * @param endDate
	 * @param epList
	 * @return
	 */
	List<Map<String, Object>> pricePie(String startDate, String endDate,List<Map<String,Object>> epList);

	/**
	 * 获取能源价格（水、电）
	 * @return
	 */
	List<Map<String, Object>> getEnergyPrice();

	/**
	 * 获取A座或B座面积
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> getArea(String string);

	/**
	 * cop曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> copLine(String startDate, String endDate);

	/**
	 * 度日数柱状图，同比
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> drsh(String startDate,String endDate);

	/**
	 * 度日数曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> DRSHtrend(String startDate, String endDate);

	/**
	 * 数据更新（分页查询）
	 * @param currentPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	Map<String, Object> editKV(int currentPage, int pageSize,
			Map<String, String> param);

	/**
	 * 采集点名称集合
	 * @return
	 */
	List<Map<String, Object>> pnameListJson();

	/**
	 * 数据更新（修改）
	 * @param t
	 * @param k
	 * @param v
	 * @return
	 */
	int edit(String t, String k, String v);

	/**
	 * 数据更新（批量修改）
	 * @param pname
	 * @param startTime
	 * @param endTime
	 * @param pvalue
	 * @return
	 */
	int batchSavePV(String pname, String startTime, String endTime,
			String pvalue);

	/**
	 * 平均cop曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> copLine4Avg(String startDate, String endDate);

}
