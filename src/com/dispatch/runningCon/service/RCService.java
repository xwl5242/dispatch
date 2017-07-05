package com.dispatch.runningCon.service;

import java.util.List;
import java.util.Map;

public interface RCService {

	Map<String,Object> queryRealDataByNodeId(int page,int rows,String nodeId);

	Map<String,Object> queryHistoryDataByNodeId(String collName,String nodeId, String startdate, String enddate,String minvalue,
			String maxvalue,String eqvalue, int currentPage,int pageSize);

	Map<String,Object> queryRealDataLine(String nodeId,String startTime,String endTime,boolean isHis);

	List<Map<String, Object>> queryCollByNodeId(String nodeId);

	Map<String, Object> runningCon(String nodeId);

}
