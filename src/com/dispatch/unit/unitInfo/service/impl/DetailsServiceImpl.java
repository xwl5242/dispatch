package com.dispatch.unit.unitInfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.dao.IDetailsDao;
import com.dispatch.unit.unitInfo.service.DetailsService;

/**
 * 功能描述：热源服务信息 . <BR>
 */
@Service
public class DetailsServiceImpl implements DetailsService {
	
	@Autowired
	private IDetailsDao detailsDao;

	@Override
	public int deleteDetails(UnitInfo ui) {
		int i = 0;
		if (ui != null) {
			i = detailsDao.deleteDetails(ui);
		} else {
			return 0;
		}

		return i;
	}
	
	public int checkHeatStation(UnitInfo ui){
		int i = 0;
		if (ui != null) {
			i = detailsDao.checkHeatStation(ui);
		} else {
			return 0;
		}
		return i;
	}

	/**
	 * 通过id查询热源信息
	 */
	@Override
	public UnitInfo findDetailsById(String id) {
		UnitInfo unitInfo = null;
		if (!id.isEmpty()) {
			unitInfo = detailsDao.findDetailsById(id);
		}
		return unitInfo;
	}
	
	/**
	 * 实现说明：保存机构信息到热源 managerType="2" . <BR>
	 */
	@Override
	public int saveDetails(UnitInfo ui) {
		int i = 0;
		if (ui != null) {
			i = detailsDao.saveDetails(ui);
		} else {
			return 0;
		}
		return i;
	}
	
	/**
	 * 实现说明： 获取直管单位id. <BR>
	 */
	public UnitInfo getParentId(UnitInfo ui){
		
		return detailsDao.getParentId(ui);
	}
	
	/**
	 * 实现说明 ：通过unitid查询热源信息 . <BR>
	 */
	@Override
	public Map findDetailsByUnitId(String id, int currentPage, int pageSize) {
		Map map = detailsDao.findDetailsByUnitId(id,currentPage, pageSize);
		return map;
	}
	
	/**
	 * 实现说明：批量保存热源 . <BR>
	 */
	@Override
	public int saveDetailsBatch(List<JSONObject> list,String unitId,User user, Org org) {
		int i = 0;
		if (list.size() != 0) {
			i = detailsDao.saveDetailsBatch(list,unitId,user,org);
		} else {
			return 0;
		}
		return i;
	}
	
	/**
	 * 实现说明：删除热源子信息 . <BR>
	 */
	@Override
	public int deleteChildInfo(List<JSONObject> list) {
		int i = 0;
		if (list != null) {
			i = detailsDao.deleteChildInfo(list);
		} else {
			return 0;
		}
		return i;
	}
	
	@Override
	public int checkBydate(String changeDate,String unitid,String id) {
		return detailsDao.checkBydate(changeDate,unitid,id);
	}

}
