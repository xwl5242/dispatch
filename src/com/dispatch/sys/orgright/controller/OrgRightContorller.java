package com.dispatch.sys.orgright.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.orgright.service.OrgRightService;
import com.dispatch.sys.userright.service.UserRightService;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;

@Controller
public class OrgRightContorller extends BaseController{
	@Autowired 
	private OrgRightService orgRightService;
	@Autowired
	private UserRightService userRightService;
	
	/**
	 * 方法说明：获取组织机构树形列表 . <BR>
	 */
	@Ecclog(value="组织机构树形列表",type="2",key="sys_orgright") 
	@RequestMapping("/orgright/getOrgTreeGrid.do")
	public void getOrgTreeGrid(HttpServletRequest request, HttpServletResponse response){
		String id=super.checkOrgType();
		Org org=getOrg();
		org.setId(id);
		Map map=orgRightService.getOrgTreeGrid(org);
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	/**
	 * 方法说明： 组织保存选中权限. <BR>
	 */
	@Ecclog(value="组织机构授权",type="1",key="sys_orgright") 
	@RequestMapping("/orgright/checkOrgRight.do")
	public void checkOrgRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rightIds") String rightIds,
			@RequestParam (defaultValue="",value="orgId") String orgId){
		boolean bool = false;
		bool=orgRightService.checkOrgRight(rightIds,orgId);
		super.returnnBaseJson(bool, bool==true?"保存成功":"保存失败!", response);
	}
	 
	/** 
	 * 方法说明： 根据orgid获取 所拥有权限. <BR>
	 */
	@Ecclog(value="获取组织权限",type="2",key="sys_orgright") 
	@RequestMapping("/orgright/getRightByOrgId.do")
	public void getRightByOrgId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rightIds") String rightIds,
			@RequestParam (defaultValue="",value="orgId") String orgId){
		Map map = new HashMap();
		try {
			List list=orgRightService.getRightByOrgId(orgId);
			List list1=userRightService.getAllId();
			map.put("list", list);
			map.put("list1", list1);
			JsonResProcessor js=new JsonResProcessor();
			js.returnResInfo(map, response); 
		} catch (Exception e) { 
			
			e.printStackTrace();
		}   
	}

	public OrgRightService getOrgRightService() {
		return orgRightService;
	}

	public void setOrgRightService(OrgRightService orgRightService) {
		this.orgRightService = orgRightService;
	}
	
}
