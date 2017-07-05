package com.dispatch.unit.unitInfo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dispatch.sys.log.Ecclog;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.service.DetailsService;
import com.dispatch.unit.unitInfo.service.UnitInfoService;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;

/**
 * 功能描述：用能单位功能信息  .  <BR>
 */
@SuppressWarnings("static-access")
@Controller
public class UnitInfoController extends BaseController{
	@Autowired
	private UnitInfoService unitInfoService;
	@Autowired
	private DetailsService detailsService;
	
	/**
	 * 方法说明：通过id查询用能信息 . <BR>
	 */
	@Ecclog(value="通过id查询用能信息",type="2",key="ieu_unitInfo")
	@RequestMapping("/unitInfo/findUnitInfoById.do")
	public void findUnitInfoById(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id) {
		UnitInfo unitInfo   = unitInfoService.findUnitInfoById(id);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(unitInfo, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
