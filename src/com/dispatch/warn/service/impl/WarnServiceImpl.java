package com.dispatch.warn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.warn.dao.WarnDao;
import com.dispatch.warn.service.WarnService;

@Service
public class WarnServiceImpl implements WarnService {

	@Autowired
	private WarnDao warnDao;
	
}
