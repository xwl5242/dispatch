package com.dispatch.runningCon.service;

import java.util.List;
import java.util.Map;


public interface EAService {

	/**
	 * 同比柱状图（A座，B座，总体）
	 * @param ys
	 * @param type
	 * @param eaType
	 * @return
	 */
	Map<String, Object> tongbi(String ys,String type,String eaType);

	/**
	 * 柱状图
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	Map<String, Object> airTongbi1(String startDate, String endDate,String type);

	/**
	 * 所有曲线图
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param eaType
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> qushi(String startDate, String endDate,String type,String eaType) throws Exception;

	/**
	 * 饼图
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<Map<String,String>> pricePie(String startDate, String endDate) throws Exception;

	/**
	 * cop曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> copLine(String startDate, String endDate) throws Exception;

	/**
	 * 度日数柱状图，同比
	 * @param ys
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> drsh(String ys,String startDate,String endDate)throws Exception ;

	/**
	 * 度日数曲线
	 * @param ys
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> DRSHtrend(String ys, String startDate, String endDate)throws Exception;

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
	 * @throws Exception
	 */
	Map<String, Object> copLine4Avg(String startDate, String endDate) throws Exception;

	/**
	 * 室内外温度曲线
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> roomAndoutTempLine(String startDate, String endDate) throws Exception;

}
