package com.dispatch.sys.userright.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.userright.service.UserRightService;
import com.util.common.JsonResProcessor;
@Controller
public class UserRightController {
	@Autowired
	private UserRightService userRightService;
	/** 
	 * 方法说明： 保存选中权限. <BR>
	 */
	@Ecclog(value="保存用户权限授权",type="1",key="sys_userright") 
	@RequestMapping("/userright/checkUserRight.do")
	public void checkUserRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rightIds") String rightIds,
			@RequestParam (defaultValue="",value="userId") String userId){
		Map map = new HashMap();
		map.put("messager", "保存成功!");
		map.put("flag", true); 
		try {
			userRightService.checkUserRight(rightIds, userId);
			JsonResProcessor js=new JsonResProcessor();
			js.returnResInfo(map, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	} 
	/** 
	 * 方法说明： 获取用户权限. <BR>
	 */
	@Ecclog(value="获取用户权限",type="2",key="sys_userright") 
	@RequestMapping("/userright/getRightByUserId.do") 
	public void getRightByUserId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String userId){
		Map map = new HashMap();
		map.put("messager", "保存成功!");
		map.put("flag", true); 
		try { 
			List list=userRightService.getRightByUserId(userId);
			List list1=userRightService.getAllId();
			map.put("list", list);
			map.put("list1", list1);
			JsonResProcessor js=new JsonResProcessor();
			js.returnResInfo(map, response); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	} 
	
	/** 
	 * 方法说明： 保存用户组织权限. <BR>
	 */
	@Ecclog(value="保存用户组织权限",type="1",key="sys_userright") 
	@RequestMapping("/userright/saveUserOrgRight.do") 
	public void saveUserOrgRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="orgIds") String orgIds,
			@RequestParam (defaultValue="",value="userId") String userId){
		Map map = new HashMap();
		map.put("messager", "保存成功!");
		map.put("flag", true); 
		try { 
			userRightService.saveUserOrgRight(orgIds, userId);
			JsonResProcessor js=new JsonResProcessor();
			js.returnResInfo(map, response); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	} 
	/** 
	 * 方法说明： 获取用户权限. <BR>
	 */
	@Ecclog(value="获取用户权限组织",type="2",key="sys_userright") 
	@RequestMapping("/userright/getOrgByUserId.do") 
	public ModelAndView getOrgByUserId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="id") String id){
		List list=userRightService.getOrgByUserId(id);
		JSONArray json=JSONArray.fromObject(list);
		ModelAndView mv=new ModelAndView();
		mv.addObject("list", json.toString());
		mv.setViewName("sys/userright/userorgright");
		return mv;
	}
	public UserRightService getUserRightService() {
		return userRightService;
	}
	public void setUserRightService(UserRightService userRightService) {
		this.userRightService = userRightService;
	}
	
}
