package com.dispatch.unit.unitInfo.service;

import com.dispatch.unit.bean.UnitInfo;

/**
 * 功能描述：用能单位服务层  .  <BR>
 */
public interface UnitInfoService {

	/**
	 * 通过id查询用能单位信息
	 */
	 public UnitInfo findUnitInfoById(String id);
	 
}
