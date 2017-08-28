package com.dispatch.warn.service;

import java.util.Map;

public interface WarnService {

	Map<String, Object> listWarnData(String sTime, String eTime,
			String pointName, String status,int currentPage, int pageSize);

	Map<String, Object> warnCount();

}
