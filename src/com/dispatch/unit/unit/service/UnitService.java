package com.dispatch.unit.unit.service;

import java.util.List;
import java.util.Map;

import com.dispatch.unit.bean.UnitInfo;

public interface UnitService {

	/**
	 * 方法说明： 获取用能单位树. <BR>
	 */
	List<Map<String, Object>> AllUnitTreeData(String id,String t,UnitInfo unit);

	/**
	 * 方法说明：根据id获取用能单位详细信息 . <BR>
	 */
	UnitInfo findUnitInfoById(String id);

	/**
	 * 方法说明： 根据id获取热源详细信息. <BR>
	 */
	UnitInfo findDetailsById(String id);

	/**
	 * 方法说明： 根据id获取热力站详细信息. <BR>
	 */
	UnitInfo findHeatById(String id);

	/**
	 * 方法说明： 根据id获取管网信息. <BR>
	 */
	UnitInfo findGwById(String id);

	/**
	 * 方法说明：保存修改后的热力站信息 . <BR>
	 */
	void updateHeat(UnitInfo unitInfo);

	/**
	 * 方法说明：保存修改后的管网信息 . <BR>
	 */
	void updateGW(UnitInfo unitInfo);

	/**
	 * 方法说明：保存修改后的支路信息 . <BR>
	 */
	void updateZL(UnitInfo unitInfo);

	/**
	 * 方法说明： 获取地区信息. <BR>
	 */
	Map findArea();

	/**
	 * 方法说明：根据id获取楼栋信息 . <BR>
	 */
	UnitInfo findLdById(String id);

	/**
	 * 方法说明： 保存修改后的楼栋信息. <BR>
	 */
	void updateLD(UnitInfo unitInfo);

	/**
	 * 方法说明：查询当前登陆用户所在单位 . <BR>
	 */
	List findUnitId(String pid);

	/**
	 * 方法说明：查询符合条件的用能单位树 . <BR>
	 */
	List<Map<String, Object>> SelectUnitTreeData(String unitName,
			String unitType);

	/**
	 * 方法说明： 用能单位调级. <BR>
	 */
	void tjUnit(String sureId, String idd);

	/**
	 * 方法说明：获取可用的用能单位树 . <BR>
	 */
	List<Map<String, Object>> getUnitTreeData(String id, String t);
	
	void updateYN(UnitInfo unitInfo);

	List getUnitInfoById(String oid, String id);
	
	List getUnitInfoByName(String oid, String name);
	
	List getHotUnitInfo(String oid, String name);
	
	List getheatUnitInfo(String oid, String name);

	int checkCode(UnitInfo unitInfo);
	
	UnitInfo getUnitInfoByCode(String code);
}
