package com.dispatch.sys.roleright.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.roleright.service.RoleRightService;
import com.dispatch.sys.userright.service.UserRightService;
import com.util.common.JsonResProcessor;

@Controller
public class RoleRightContorller {
	@Autowired
	private RoleRightService roleRightService;
	@Autowired
	private UserRightService userRightService;
	
	/**
	 * 方法说明： 保存授权. <BR>
	 */
	@Ecclog(value="保存角色授权",type="1",key="sys_role") 
	@RequestMapping("/roleright/checkRoleRight.do")
	public void checkRoleRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rightIds") String rightIds,
			@RequestParam (defaultValue="",value="roleId") String roleId){
		Map map = new HashMap();
		map.put("messager", "保存成功!");
		map.put("flag", true); 
		
		try {
			roleRightService.checkRoleRight(rightIds,roleId);
			JsonResProcessor js=new JsonResProcessor();
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
	 
	/**
	 * 方法说明： 根据角色id获取  权限. <BR>
	 */
	@RequestMapping("/roleright/getRightByRoleId.do")
	public void getRightByRoleId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="roleId") String roleId){
		Map map = new HashMap();
		try {
			List list=roleRightService.getRightByRoleId(roleId);
			List list1=userRightService.getAllId();
			map.put("list", list);
			map.put("list1", list1);
			JsonResProcessor js=new JsonResProcessor();
			js.returnResInfo(map, response);  
		} catch (Exception e) { 
			e.printStackTrace();
		}   
	}

	public RoleRightService getRoleRightService() {
		return roleRightService;
	}

	public void setRoleRightService(RoleRightService roleRightService) {
		this.roleRightService = roleRightService;
	}
	
}
