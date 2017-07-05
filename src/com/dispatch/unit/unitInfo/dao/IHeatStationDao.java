package com.dispatch.unit.unitInfo.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.dispatch.unit.bean.UnitInfo;

/**
 * 功能描述：热力站数据信息  .  <BR>
 */
public interface IHeatStationDao {
	
	/**
	 * 方法说明：通过id查询单位信息 . 
	 */
	public UnitInfo findUnitInfoById(String id);
	
	/**
	 * 方法说明： 修改热力站信息. 
	 */
	public int updateHeatStation(UnitInfo unitInfo, List<JSONObject> list);
}
