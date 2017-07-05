package com.dispatch.unit.unitInfo.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.dispatch.unit.bean.UnitInfo;

/**
 * 功能描述：热力站服务信息  .  <BR>
 */
public interface HeatStationService {
	
	/**
	 * 方法说明：通过id查询单位信息 . <BR>
	 */
	public UnitInfo findUnitInfoById(String id);
	
	/**
	 * 方法说明：修改热力站信息 . <BR>
	 */
	public int updateHeatStation(UnitInfo unitInfo, List<JSONObject> list);
}
