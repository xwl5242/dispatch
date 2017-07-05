package com.dispatch.runningCon.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.runningCon.dao.RunRecordDao;
import com.dispatch.runningCon.service.RunRecordService;

@Service
public class RunRecordServiceImpl implements RunRecordService {

	@Autowired
	private RunRecordDao rrDao;

	/**
	 * 查询运行日志list
	 */
	@Override
	public Map<String, Object> queryRunRecord(int currentPage, int pageSize,
			String nodeId) {
		return rrDao.queryRunRecord(currentPage,pageSize,nodeId);
	}
	
	/**
	 * 查询日志分页信息
	 */
	@Override
	public Map<String, Object> queryLogList(int currentPage, int pageSize,
			Map<String, String> param) {
		return rrDao.queryLogList(currentPage,pageSize,param);
	}
}
