package com.dispatch.runningCon.service;

import java.util.Map;


public interface EAService {

	Map<String, Object> tongbi(String ys,String type,String eaType);

	Map<String, Object> airTongbi1(String startDate, String endDate,String type);

	Map<String, Object> qushi(String startDate, String endDate,String type,String eaType) throws Exception;

}
