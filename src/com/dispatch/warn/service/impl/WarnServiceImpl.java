package com.dispatch.warn.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.warn.dao.WarnDao;
import com.dispatch.warn.service.WarnService;

@Service
public class WarnServiceImpl implements WarnService {

	@Autowired
	private WarnDao warnDao;

	@Override
	public Map<String, Object> listWarnData(String sTime, String eTime,
			String pointName, String status,int currentPage, int pageSize) {
		return warnDao.listWarnData(sTime,eTime,pointName,status,currentPage, pageSize);
	}

	@Override
	public Map<String, Object> warnCount() {
		return warnDao.warnCount();
	}
	
}
