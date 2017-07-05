package com.dispatch.unit.unitInfo.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.dao.IHeatStationDao;
import com.dispatch.unit.unitInfo.service.HeatStationService;

@Service
public class HeatStationServiceImpl implements HeatStationService {
	
	@Autowired
	private IHeatStationDao heatStationDao;
	
	/**
	 * 实现说明：通过di查询单位信息 . 
	 */
	@Override
	public UnitInfo findUnitInfoById(String id) {
		UnitInfo unitInfo = heatStationDao.findUnitInfoById(id);
		return unitInfo;
	}
	
	/**
	 * 实现说明： 修改热力站信息. 
	 */
	@Override
	public int updateHeatStation(UnitInfo unitInfo, List<JSONObject> list) {
		int i = 0;
		if (unitInfo != null) {
			i = heatStationDao.updateHeatStation(unitInfo,list);
		} else {
			return 0;
		}
		return i;
	}

}
