package com.dispatch.runningCon.dao;

import java.util.List;
import java.util.Map;


public interface EADao {

	List<Map<String, Object>> tongbi(String type);

	List<Map<String, Object>> airTongbi1(String startDate, String endDate);

	List<Map<String, Object>> qushi(String startDate, String endDate,String type);

}
