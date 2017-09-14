package com.dispatch.runningCon.service;

import java.util.List;
import java.util.Map;


public interface EAService {

	Map<String, Object> tongbi(String ys,String type,String eaType);

	Map<String, Object> airTongbi1(String startDate, String endDate,String type);

	Map<String, Object> qushi(String startDate, String endDate,String type,String eaType) throws Exception;

	List<Map<String,String>> pricePie(String startDate, String endDate) throws Exception;

	Map<String, Object> copLine(String startDate, String endDate) throws Exception;

	Map<String, Object> drsh(String ys,String startDate,String endDate)throws Exception ;

	Map<String, Object> DRSHtrend(String ys, String startDate, String endDate)throws Exception;

	Map<String, Object> editKV(int currentPage, int pageSize,
			Map<String, String> param);

	List<Map<String, Object>> pnameListJson();

	int edit(String t, String k, String v);

	int batchSavePV(String pname, String startTime, String endTime,
			String pvalue);

}
