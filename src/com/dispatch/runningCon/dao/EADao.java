package com.dispatch.runningCon.dao;

import java.util.List;
import java.util.Map;


public interface EADao {

	List<Map<String, Object>> tongbi(String type,String eaType);

	List<Map<String, Object>> airTongbi1(String startDate, String endDate,String type);

	List<Map<String, Object>> qushi(String startDate, String endDate,String type,String eaType);

	List<Map<String, Object>> pricePie(String startDate, String endDate,List<Map<String,Object>> epList);

	List<Map<String, Object>> getEnergyPrice();

}
