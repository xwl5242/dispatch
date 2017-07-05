package com.dispatch.unit.unitInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.dao.IUnitInfoDao;
import com.dispatch.unit.unitInfo.service.UnitInfoService;

/**
 * 功能描述：用能单位服务信息 . <BR>
 */
@Service
public class UnitInfoServiceImpl implements UnitInfoService {
	@Autowired
	private IUnitInfoDao unitInfoDao;

	/**
	 * 实现说明：通过Id查询用能单位信息 . <BR>
	 */
	@Override
	public UnitInfo findUnitInfoById(String id) {
		UnitInfo unitInfo = null;
		if (!id.isEmpty()) {
			unitInfo = unitInfoDao.findUnitInfoById(id);
		}
		return unitInfo;
	}

}
