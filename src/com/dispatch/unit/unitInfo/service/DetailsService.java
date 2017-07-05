package com.dispatch.unit.unitInfo.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.unit.bean.UnitInfo;

/**
 * 功能描述：热源服务信息  .  <BR>
 */
public interface DetailsService {

	/**
	 * 方法说明：保存热源信息 . <BR>
	 */
	public int saveDetails(UnitInfo ui);
	
	/**
	 * 方法说明：获取直管单位id . <BR>
	 */
	public UnitInfo getParentId(UnitInfo ui);
	
	/**
	 * 方法说明： 删除热源信息. <BR>
	 */
	public int deleteDetails(UnitInfo ui);
	
	/**
	 * 通过id查询热源信息
	 */
	 public UnitInfo findDetailsById(String id);
	 
	 /**
	  * 方法说明： 通过单位id查询单位详细信息. <BR>
	  */
	 public Map findDetailsByUnitId(String id, int currentPage, int pageSize);
	 
	 /**
	  * 方法说明：批量保存单位详细信息 . <BR>
	  */
	 public int saveDetailsBatch(List<JSONObject> list, String unitId,User user, Org org);
	 
	 /**
	  * 方法说明：删除子信息 . <BR>
	  */
	 public int deleteChildInfo(List<JSONObject> list);

	public int checkBydate(String changeDate, String unitId, String id);
}
