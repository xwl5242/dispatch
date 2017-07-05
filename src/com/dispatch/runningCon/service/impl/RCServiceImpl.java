package com.dispatch.runningCon.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.runningCon.dao.RCDao;
import com.dispatch.runningCon.service.RCService;

@Service
public class RCServiceImpl implements RCService{

	@Autowired
	private RCDao rcDao;

	@Override
	public Map<String,Object> queryRealDataByNodeId(int page,int rows,String nodeId) {
		return rcDao.queryRealDataByNodeId(page,rows,nodeId);
	}

	@Override
	public Map<String,Object> queryHistoryDataByNodeId(String collName,String nodeId, String startdate, String enddate,String minvalue,
			String maxvalue,String eqvalue, int currentPage,int pageSize) {
		return rcDao.queryHistoryDataByNodeId(collName,nodeId,startdate,enddate,minvalue,maxvalue,eqvalue,currentPage,pageSize);
	}

	@Override
	public Map<String,Object> queryRealDataLine(String nodeId,String startTime,String endTime,boolean isHis) {
		return rcDao.queryRealDataLine(nodeId,startTime,endTime,isHis);
	}

	@Override
	public List<Map<String, Object>> queryCollByNodeId(String nodeId) {
		return rcDao.queryCollByNodeId(nodeId);
	}

	@Override
	public Map<String, Object> runningCon(String nodeId) {
		return rcDao.runningCon(nodeId);
	}
}
