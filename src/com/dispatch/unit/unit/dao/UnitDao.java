package com.dispatch.unit.unit.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.dispatch.sys.bean.Org;
import com.dispatch.unit.bean.UnitInfo;

public interface UnitDao {

	/**
	 * 方法说明： 获取用能单位树. <BR>
	 */
	List<Map<String, Object>> getUnitTreeData(String pid,String c,UnitInfo unit);

	/**
	 * 方法说明：  获取子节点条数. <BR>
	 */
	int getChildrenCount(String id);

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
	 * 方法说明： . <BR>
	 */
	void updateGW(UnitInfo unitInfo);

	/**
	 * 方法说明： 保存修改后的支路信息. <BR>
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
	 * 方法说明：用能单位调级 . <BR>
	 */
	void tjUnit(String sureId, String idd);

	/**
	 * 方法说明： 获取可用的用能单位树. <BR>
	 */
	List<Map<String, Object>> getAbleUnitTreeData(String pid, String string);

	void updateYN(UnitInfo unitInfo);

	List getUnitInfoById(String oid, String id);
	
	List getUnitInfoByName(String oid, String name);
	
	List getUnitInfoByKey(String oid, String name);
	
	/**
	 * 方法说明： . <BR>
	 */
	public List<UnitInfo> getAbleUnitTreeDataNoSql();

	int checkCode(UnitInfo unitInfo);

	UnitInfo findUnitInfoByCode(String code);
	
	List getHotUnitInfo(String oid, String name);
	
	List getHeatUnitInfo(String oid, String name);
	
}
