package com.dispatch.unit.unit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dispatch.sys.bean.EmpLoyee;
import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unit.service.UnitService;
import com.dispatch.unit.unitInfo.service.HeatStationService;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;

@Controller
public class UnitController extends BaseController {

	@Resource
	private UnitService unitService;
	@Resource
	private HeatStationService heatStationService;

	/**
	 * 方法说明： 获取用能单位树. <BR>
	 */
	@Ecclog(value = "获取用能单位树", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/AllUnitTreeData.do")
	public void AllUnitTreeData(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "0", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitName") String unitName,
			@RequestParam(defaultValue = "", value = "unitType") String unitType,
			@RequestParam(defaultValue = "", value = "status") String status) {

		Org org = (Org) request.getSession().getAttribute("ECCORG");
		String t = "1";
		if ("0".equals(id)) {
			id = super.checkOrgType();
			t = "0";
		}
		UnitInfo unit = new UnitInfo();
		unit.setUnitType(unitType);
		unit.setUnitName(unitName);
		unit.setStatus(status);
		List<Map<String, Object>> map = unitService.AllUnitTreeData(id, t, unit);
		super.returnListJson(map, response);
	}

	/**
	 * 方法说明： 根据id获取用能单位详细信息. <BR>
	 */
	@Ecclog(value = "根据id获取用能单位详细信息", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/findUnitInfoById.do")
	public void findUnitInfoById(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "id") String id,
			HttpServletResponse response) {
		UnitInfo unitInfo = unitService.findUnitInfoById(id);
		super.returnObjectJson(unitInfo, response);
	}

	/**
	 * 方法说明： 根据id获取热源详细信息. <BR>
	 */
	@Ecclog(value = "根据id获取热源详细信息", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/findDetailsById.do")
	public void findDetailsById(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "detailsId") String id,
			HttpServletResponse response) {
		UnitInfo unitInfo = unitService.findDetailsById(id);
		super.returnObjectJson(unitInfo, response);
	}

	/**
	 * 方法说明： 根据id获取热力站详细信息. <BR>
	 */
	@Ecclog(value = "根据id获取热力站详细信息", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/findHeatById.do")
	public void findHeatById(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "heatId") String id,
			HttpServletResponse response) {
		UnitInfo unitInfo = unitService.findHeatById(id);
		super.returnObjectJson(unitInfo, response);
	}

	/**
	 * 方法说明：根据id获取管网信息 . <BR>
	 */
	@Ecclog(value = "根据id获取管网信息", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/findGwById.do")
	public void findGwById(HttpServletRequest request,
			@RequestParam(defaultValue = "", value = "gwId") String id,
			HttpServletResponse response) {
		UnitInfo unitInfo = unitService.findGwById(id);
		super.returnObjectJson(unitInfo, response);
	}

	/**
	 * 方法说明：根据id获取楼栋信息 . <BR>
	 */
	@Ecclog(value = "根据id获取楼栋信息", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/findLdById.do")
	public void findLdById(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "ldId") String id,
			HttpServletResponse response) {
		UnitInfo unitInfo = unitService.findLdById(id);
		super.returnObjectJson(unitInfo, response);
	}
	

	/**
	 * 方法说明：保存修改后的热力站信息 . <BR>
	 */
	@Ecclog(value = "保存修改后的热力站信息", type = "1", key = "ecc_unitinfo")
	@RequestMapping("/unit/updateHeat.do")
	public void updateHeat(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitCode") String unitCode,// 父id
			@RequestParam(defaultValue = "", value = "unitName") String unitName,
			@RequestParam(defaultValue = "", value = "shortName") String shortName,
			@RequestParam(defaultValue = "", value = "jianPin") String jianpin,
			@RequestParam(defaultValue = "", value = "heatingType") String heatingType,
			@RequestParam(defaultValue = "", value = "seq") String seq,
			@RequestParam(defaultValue = "", value = "remarks") String remarks,
			@RequestParam(defaultValue = "", value = "manageType") String manageType,
			@RequestParam(defaultValue = "", value = "status") String status,
			@RequestParam(defaultValue = "", value = "onwerType") String onwerType,
			@RequestParam(defaultValue = "", value = "areaId") String areaId,
			@RequestParam(defaultValue = "", value = "address") String address,
			@RequestParam(defaultValue = "", value = "unitType") String unitType,
			@RequestParam(defaultValue = "", value = "unitOutId") String unitOutId,
			@RequestParam(defaultValue = "", value = "gwLength") String gwLength,
			@RequestParam(defaultValue = "", value = "villageName") String villageId)
			throws Exception {
		Map map = new HashMap();

		try {

			User u2 = (User) request.getSession().getAttribute("ECCUSER");
			Org o2 = (Org) request.getSession().getAttribute("ECCORG");
			EmpLoyee e2 = (EmpLoyee) request.getSession()
					.getAttribute("ECCEMP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setId(id);
			unitInfo.setUnitName(unitName);
			unitInfo.setUnitCode(unitCode);
			unitInfo.setShortName(shortName);
			unitInfo.setJianPin(jianpin);
			unitInfo.setHeatingType(heatingType);
			unitInfo.setSeq(seq);
			unitInfo.setUnitOutId(unitOutId);
			unitInfo.setAreaId(areaId);
			unitInfo.setUnitType(unitType);
			unitInfo.setAddress(address);
			unitInfo.setRemarks(remarks);
			unitInfo.setGwLength(gwLength);
			unitInfo.setVillageId(villageId);
			unitInfo.setManageType(manageType);
			unitInfo.setOnwerType(onwerType);
			unitInfo.setStatus(status);// 启用
			unitInfo.setUpdateUserID(u2.getId());
			unitInfo.setUpdateUser(u2.getUserName());
			unitInfo.setUpdateUserDepartmentID(o2.getId());
			unitInfo.setUpdateUserDepartment(o2.getOrgName());
			unitInfo.setUpdateDate(df.format(new Date()));
			int j = this.checkCode(id, unitCode);
			if (j > 0) {
				map.put("messager", "编号重复，请重新输入!");
				map.put("flag", false);
			} else {
				unitService.updateHeat(unitInfo);
				map.put("messager", "修改成功!");
				map.put("flag", true);
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.clear();
			map.put("messager", "修改失败!");
			map.put("flag", false);
		}
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);

	}

	/**
	 * 方法说明：保存修改后的管网信息 . <BR>
	 */
	@Ecclog(value = "保存修改后的管网信息", type = "1", key = "ecc_unitinfo")
	@RequestMapping("/unit/updateGW.do")
	public void updateGW(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitCode") String unitCode,// 父id
			@RequestParam(defaultValue = "", value = "unitName") String unitName,
			@RequestParam(defaultValue = "", value = "shortName") String shortName,
			@RequestParam(defaultValue = "", value = "seq") String seq,
			@RequestParam(defaultValue = "", value = "remarks") String remarks,
			@RequestParam(defaultValue = "", value = "gwLength") String gwLength,
			@RequestParam(defaultValue = "", value = "status") String status,
			@RequestParam(defaultValue = "", value = "areaId") String areaId,
			@RequestParam(defaultValue = "", value = "address") String address,
			@RequestParam(defaultValue = "", value = "unitType") String unitType)
			throws Exception {
		Map map = new HashMap();

		try {

			User u2 = (User) request.getSession().getAttribute("ECCUSER");
			Org o2 = (Org) request.getSession().getAttribute("ECCORG");
			EmpLoyee e2 = (EmpLoyee) request.getSession()
					.getAttribute("ECCEMP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setId(id);
			unitInfo.setUnitName(unitName);
			unitInfo.setUnitCode(unitCode);
			unitInfo.setShortName(shortName);
			unitInfo.setSeq(seq);
			unitInfo.setAreaId(areaId);
			unitInfo.setUnitType(unitType);
			unitInfo.setAddress(address);
			unitInfo.setRemarks(remarks);
			unitInfo.setStatus(status);// 启用
			unitInfo.setGwLength(gwLength);
			unitInfo.setUpdateUserID(u2.getId());
			unitInfo.setUpdateUser(u2.getUserName());
			unitInfo.setUpdateUserDepartmentID(o2.getId());
			unitInfo.setUpdateUserDepartment(o2.getOrgName());
			unitInfo.setUpdateDate(df.format(new Date()));
			int j = this.checkCode(id, unitCode);
			if (j > 0) {
				map.put("messager", "编号重复，请重新输入!");
				map.put("flag", false);
			} else {
				unitService.updateGW(unitInfo);
				map.put("messager", "修改成功!");
				map.put("flag", true);
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.clear();
			map.put("messager", "修改失败!");
			map.put("flag", false);
		}
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);

	}

	/**
	 * 方法说明： 保存修改后的支路信息. <BR>
	 */
	@Ecclog(value = "保存修改后的支路信息", type = "1", key = "ecc_unitinfo")
	@RequestMapping("/unit/updateZL.do")
	public void updateZL(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitCode") String unitCode,// 父id
			@RequestParam(defaultValue = "", value = "unitName") String unitName,
			@RequestParam(defaultValue = "", value = "shortName") String shortName,
			@RequestParam(defaultValue = "", value = "seq") String seq,
			@RequestParam(defaultValue = "", value = "remarks") String remarks,
			@RequestParam(defaultValue = "", value = "status") String status,
			@RequestParam(defaultValue = "", value = "areaId") String areaId,
			@RequestParam(defaultValue = "", value = "address") String address,
			@RequestParam(defaultValue = "", value = "unitType") String unitType)
			throws Exception {
		Map map = new HashMap();

		try {

			User u2 = (User) request.getSession().getAttribute("ECCUSER");
			Org o2 = (Org) request.getSession().getAttribute("ECCORG");
			EmpLoyee e2 = (EmpLoyee) request.getSession().getAttribute("ECCEMP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setId(id);
			unitInfo.setUnitName(unitName);
			unitInfo.setUnitCode(unitCode);
			unitInfo.setShortName(shortName);
			unitInfo.setSeq(seq);
			unitInfo.setAreaId(areaId);
			unitInfo.setUnitType(unitType);
			unitInfo.setAddress(address);
			unitInfo.setRemarks(remarks);
			unitInfo.setStatus(status);// 启用
			unitInfo.setUpdateUserID(u2.getId());
			unitInfo.setUpdateUser(u2.getUserName());
			unitInfo.setUpdateUserDepartmentID(o2.getId());
			unitInfo.setUpdateUserDepartment(o2.getOrgName());
			unitInfo.setUpdateDate(df.format(new Date()));
			int j = this.checkCode(id, unitCode);
			if (j > 0) {
				map.put("messager", "编号重复，请重新输入!");
				map.put("flag", false);
			} else {
				unitService.updateZL(unitInfo);
				map.put("messager", "修改成功!");
				map.put("flag", true);
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.clear();
			map.put("messager", "修改失败!");
			map.put("flag", false);
		}
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);

	}

	/**
	 * 方法说明： 保存修改后的楼栋信息. <BR>
	 */
	@Ecclog(value = "保存修改后的楼栋信息", type = "1", key = "ecc_unitinfo")
	@RequestMapping("/unit/updateLD.do")
	public void updateLD(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitCode") String unitCode,// 父id
			@RequestParam(defaultValue = "", value = "unitName") String unitName,
			@RequestParam(defaultValue = "", value = "shortName") String shortName,
			@RequestParam(defaultValue = "", value = "seq") String seq,
			@RequestParam(defaultValue = "", value = "remarks") String remarks,
			@RequestParam(defaultValue = "", value = "status") String status,
			@RequestParam(defaultValue = "", value = "areaId") String areaId,
			@RequestParam(defaultValue = "", value = "villageName") String villageId,
			@RequestParam(defaultValue = "", value = "address") String address,
			@RequestParam(defaultValue = "", value = "unitType") String unitType)
			throws Exception {
		Map map = new HashMap();

		try {

			User u2 = (User) request.getSession().getAttribute("ECCUSER");
			Org o2 = (Org) request.getSession().getAttribute("ECCORG");
			EmpLoyee e2 = (EmpLoyee) request.getSession()
					.getAttribute("ECCEMP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setId(id);
			unitInfo.setUnitName(unitName);
			unitInfo.setUnitCode(unitCode);
			unitInfo.setShortName(shortName);
			unitInfo.setSeq(seq);
			unitInfo.setAreaId(areaId);
			unitInfo.setUnitType(unitType);
			unitInfo.setAddress(address);
			unitInfo.setVillageId(villageId);
			unitInfo.setRemarks(remarks);
			unitInfo.setStatus(status);// 启用
			unitInfo.setUpdateUserID(u2.getId());
			unitInfo.setUpdateUser(u2.getUserName());
			unitInfo.setUpdateUserDepartmentID(o2.getId());
			unitInfo.setUpdateUserDepartment(o2.getOrgName());
			unitInfo.setUpdateDate(df.format(new Date()));
			int j = this.checkCode(id, unitCode);
			if (j > 0) {
				map.put("messager", "编号重复，请重新输入!");
				map.put("flag", false);
			} else {
				unitService.updateLD(unitInfo);
				map.put("messager", "修改成功!");
				map.put("flag", true);
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.clear();
			map.put("messager", "修改失败!");
			map.put("flag", false);
		}
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);

	}

	/**
	 * 方法说明：获取地区信息 . <BR>
	 */
	@Ecclog(value = "获取地区信息", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/findArea.do")
	public void findArea(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = unitService.findArea();
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 方法说明：查询符合条件的用能单位树 . <BR>
	 */
	@Ecclog(value = "查询符合条件的用能单位树", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/SelectUnitTreeData.do")
	public void SelectUnitTreeData(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "0", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitName") String unitName,
			@RequestParam(defaultValue = "", value = "unitType") String unitType) {
		List<Map<String, Object>> map = unitService.SelectUnitTreeData(
				unitName, unitType);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 方法说明：用能单位调级 . <BR>
	 */
	@Ecclog(value = "用能单位调级", type = "1", key = "ecc_unitinfo")
	@RequestMapping("/unit/tjUnit.do")
	public void tjUnit(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "sureId") String sureId,
			@RequestParam(defaultValue = "", value = "ids") String id)
			throws Exception {
		Map map = new HashMap();
		unitService.tjUnit(sureId, id);
		map.put("messager", "调级成功!");
		map.put("flag", true);
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);
	}

	/**
	 * 方法说明： 获取可用的用能单位树. <BR>
	 */
	@Ecclog(value = "获取可用的用能单位树", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/getUnitTreeData.do")
	public void getUnitTreeData(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "0", value = "id") String id) {

		String t = "1";
		if ("0".equals(id)) {
			id = super.checkOrgType();
			t = "0";
		}
		List<Map<String, Object>> map = unitService.getUnitTreeData(id, t);
		super.returnListJson(map, response);
	}

	@Ecclog(value = "保存修改后的用能信息", type = "1", key = "ecc_unitinfo")
	@RequestMapping("/unit/updateYN.do")
	public void updateYN(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "areaId") String areaId,
			@RequestParam(defaultValue = "", value = "villageId") String villageId,
			@RequestParam(defaultValue = "", value = "address") String address,
			@RequestParam(defaultValue = "", value = "remarks") String remarks,
			@RequestParam(defaultValue = "", value = "heatingType") String heatingType)
			throws Exception {
		Map map = new HashMap();

		try {

			User u2 = (User) request.getSession().getAttribute("ECCUSER");
			Org o2 = (Org) request.getSession().getAttribute("ECCORG");
			EmpLoyee e2 = (EmpLoyee) request.getSession()
					.getAttribute("ECCEMP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			UnitInfo unitInfo = new UnitInfo();
			unitInfo.setId(id);
			unitInfo.setAreaId(areaId);
			unitInfo.setAddress(address);
			unitInfo.setRemarks(remarks);
			unitInfo.setHeatingType(heatingType);
			unitInfo.setVillageId(villageId);
			unitInfo.setUpdateUserID(u2.getId());
			unitInfo.setUpdateUser(u2.getUserName());
			unitInfo.setUpdateUserDepartmentID(o2.getId());
			unitInfo.setUpdateUserDepartment(o2.getOrgName());
			unitInfo.setUpdateDate(df.format(new Date()));
			unitService.updateYN(unitInfo);
			map.put("messager", "修改成功!");
			map.put("flag", true);

		} catch (Exception e) {
			logger.error(e.toString(), e);
			map.clear();
			map.put("messager", "修改失败!");
			map.put("flag", false);
		}
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);

	}

	/**
	 * 方法说明： . <BR>
	 */
	@Ecclog(value = "", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/getUnitInfoById.do")
	public void getUnitInfoById(HttpServletRequest request,
			@RequestParam(defaultValue = "", value = "pid") String id,
			HttpServletResponse response) {
		List list = unitService.getUnitInfoById(super.checkOrgType(), id);
		super.returnListJson(list, response);
	}
	
	@Ecclog(value = "", type = "2", key = "ecc_unitinfo")
	@RequestMapping("/unit/getUnitTreeDataNoSql.do")
	public int checkCode(
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "unitCode") String unitCode) {
		UnitInfo unitInfo = new UnitInfo();
		unitInfo.setId(id);
		unitInfo.setUnitCode(unitCode);
		int i = unitService.checkCode(unitInfo);
		return i;
	}
}
