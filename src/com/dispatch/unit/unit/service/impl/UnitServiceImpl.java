package com.dispatch.unit.unit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Org;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unit.dao.UnitDao;
import com.dispatch.unit.unit.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {

	@Resource
	private UnitDao unitDao;

	/**
	 * 实现说明：获取用能单位树 . <BR>
	 */
	@Override
	public List<Map<String, Object>> AllUnitTreeData(String pid,String t,UnitInfo unit) {
		
		List<Map<String, Object>> list = unitDao.getUnitTreeData(pid, "0",
				unit);
		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		if ("0".equals(t)) {
			List<Map<String, Object>> listc = unitDao.getUnitTreeData(pid, "1",
					unit);
			for (Map<String, Object> map : listc) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", map.get("ID"));
				m.put("text", map.get("UNITNAME"));
				String num = unitDao.getChildrenCount((String) map.get("ID"))+ "";
				m.put("state", "0".equals(num) ? "open": "closed");
				m.put("unitType", map.get("UNITTYPE"));
				m.put("unitTypeName", map.get("UNITTYPENAME"));
				returnlist.add(m);
			}
			return returnlist;
		} else {
			for (Map<String, Object> map : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", map.get("ID"));
				m.put("text", map.get("UNITNAME"));
				String num = unitDao.getChildrenCount((String) map.get("ID"))+ "";
				m.put("state", "0".equals(num) ? "open": "closed");
				m.put("unitType", map.get("UNITTYPE"));
				m.put("unitTypeName", map.get("UNITTYPENAME"));
				returnlist.add(m);
			}
			return returnlist;
		}
	}

	/**
	 * 实现说明：根据id获取用能单位详细信息 . <BR>
	 */
	@Override
	public UnitInfo findUnitInfoById(String id) {
		return unitDao.findUnitInfoById(id);
	}

	/**
	 * 实现说明：根据id获取热源详细信息 . <BR>
	 */
	@Override
	public UnitInfo findDetailsById(String id) {
		return unitDao.findDetailsById(id);
	}

	/**
	 * 实现说明： 根据id获取热力站详细信息. <BR>
	 */
	@Override
	public UnitInfo findHeatById(String id) {
		return unitDao.findHeatById(id);
	}

	/**
	 * 实现说明：根据id获取管网信息 . <BR>
	 */
	@Override
	public UnitInfo findGwById(String id) {
		return unitDao.findGwById(id);
	}

	/**
	 * 实现说明： 保存修改后的热力站信息. <BR>
	 */
	@Override
	public void updateHeat(UnitInfo unitInfo) {
		unitDao.updateHeat(unitInfo);
	}

	/**
	 * 实现说明： 保存修改后的管网信息. <BR>
	 */
	@Override
	public void updateGW(UnitInfo unitInfo) {
		unitDao.updateGW(unitInfo);
	}

	/**
	 * 实现说明： 保存修改后的支路信息. <BR>
	 */
	@Override
	public void updateZL(UnitInfo unitInfo) {
		unitDao.updateZL(unitInfo);
	}

	/**
	 * 实现说明： 获取地区信息. <BR>
	 */
	@Override
	public Map findArea() {
		return unitDao.findArea();
	}

	/**
	 * 实现说明： 根据id获取楼栋信息. <BR>
	 */
	@Override
	public UnitInfo findLdById(String id) {
		return unitDao.findLdById(id);
	}

	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public void updateLD(UnitInfo unitInfo) {
		unitDao.updateLD(unitInfo);
	}

	/**
	 * 实现说明： 查询当前登陆用户所在单位. <BR>
	 */
	@Override
	public List findUnitId(String pid) {
		return unitDao.findUnitId(pid);
	}

	/**
	 * 实现说明： 查询符合条件的用能单位树. <BR>
	 */
	@Override
	public List<Map<String, Object>> SelectUnitTreeData(String unitName,
			String unitType) {
		return unitDao.SelectUnitTreeData(unitName,unitType);
	}

	/**
	 * 实现说明： 用能单位调级. <BR>
	 */
	@Override
	public void tjUnit(String sureId, String idd) {
		unitDao.tjUnit(sureId,idd);
	}

	/**
	 * 实现说明：获取可用的用能单位树 . <BR>
	 */
	@Override
	public List<Map<String, Object>> getUnitTreeData(String pid, String t) {
		List<Map<String, Object>> list = unitDao.getAbleUnitTreeData(pid,"0");
		List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
		if("0".equals(t)){
			List<Map<String, Object>> listc = unitDao.getAbleUnitTreeData(pid,"1");
		 for (Map<String, Object> map : listc) {
				Map<String, Object> m=new HashMap<String, Object>(); 
					m.put("id",map.get("ID"));
					m.put("text", map.get("UNITNAME"));
				    String num = unitDao.getChildrenCount((String) map.get("ID")) + "";
		            m.put("state", "0".equals(num) ? "open" : "closed");
		            m.put("unitType", map.get("UNITTYPE"));
		            returnlist.add(m);
			} 
				return returnlist;
		}else{
			for (Map<String, Object> map : list) {
				Map<String, Object> m=new HashMap<String, Object>(); 
					m.put("id",map.get("ID"));
					m.put("text", map.get("UNITNAME"));
				    String num = unitDao.getChildrenCount((String) map.get("ID")) + "";
		            m.put("state", "0".equals(num) ? "open" : "closed");
		            m.put("unitType", map.get("UNITTYPE"));
		            returnlist.add(m);
			} 
				return returnlist;
		}
	}

	@Override
	public void updateYN(UnitInfo unitInfo) {
		unitDao.updateYN(unitInfo);
		
	}

	@Override
	public List getUnitInfoById(String oid, String id) {
		return unitDao.getUnitInfoById(oid,id);
	}
	
	@Override
	public List getUnitInfoByName(String oid, String name) {
		return unitDao.getUnitInfoByName(oid,name);
	}

	@Override
	public int checkCode(UnitInfo unitInfo) {
		return unitDao.checkCode(unitInfo);
	}

	@Override
	public UnitInfo getUnitInfoByCode(String code) {
		return unitDao.findUnitInfoByCode(code);
	}

	@Override
	public List getHotUnitInfo(String oid, String name) {
		return unitDao.getHotUnitInfo(oid, name);
	}

	@Override
	public List getheatUnitInfo(String oid, String name) {
		return unitDao.getHeatUnitInfo(oid, name);
	}

}
