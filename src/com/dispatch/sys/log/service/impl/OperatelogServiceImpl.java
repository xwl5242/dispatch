package com.dispatch.sys.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Operatelog;
import com.dispatch.sys.log.dao.OperatelogDao;
import com.dispatch.sys.log.service.OperatelogService;
@Service
public class OperatelogServiceImpl implements OperatelogService{

	@Autowired
	private OperatelogDao operatelogDao;
	
	@Override
	public void saveOperateLog(Operatelog opt) {
		operatelogDao.saveOperateLog(opt); 
	}

	public OperatelogDao getOperatelogDao() {
		return operatelogDao;
	}

	public void setOperatelogDao(OperatelogDao operatelogDao) {
		this.operatelogDao = operatelogDao;
	}
	
}
