package com.dispatch.warn.dao;

import java.util.Map;

public interface WarnDao {

	Map<String, Object> listWarnData(String sTime, String eTime,
			String pointName, String status,int currentPage, int pageSize);

	Map<String, Object> warnCount();

}
