package com.dispatch.runningCon.dao;

import java.util.List;
import java.util.Map;


public interface EADao {

	List<Map<String, Object>> tongbi(String type,String eaType);

	List<Map<String, Object>> airTongbi1(String startDate, String endDate,String type);

	List<Map<String, Object>> qushi(String startDate, String endDate,String type,String eaType);

	List<Map<String, Object>> pricePie(String startDate, String endDate,List<Map<String,Object>> epList);

	List<Map<String, Object>> getEnergyPrice();

	List<Map<String, Object>> getArea(String string);

	List<Map<String, Object>> copLine(String startDate, String endDate);

	List<Map<String, Object>> drsh(String startDate,String endDate);

	List<Map<String, Object>> DRSHtrend(String startDate, String endDate);

	Map<String, Object> editKV(int currentPage, int pageSize,
			Map<String, String> param);

	List<Map<String, Object>> pnameListJson();

	int edit(String t, String k, String v);

	int batchSavePV(String pname, String startTime, String endTime,
			String pvalue);

}
