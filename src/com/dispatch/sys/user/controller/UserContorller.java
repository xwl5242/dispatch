package com.dispatch.sys.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.user.service.UserService;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;


@Controller
public class UserContorller extends BaseController {
	@Autowired
	private UserService userService;

	/**
	 * 方法说明：获取用户列表  . <BR>
	 */
	@Ecclog(value="查询用户列表",type="2",key="sys_user")
	@RequestMapping("/user/getUserList.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userName") String userName ,
			@RequestParam (defaultValue="",value="loginName") String loginName ,
			@RequestParam (defaultValue="",value="userStat") String userStat ,
			@RequestParam(defaultValue = "", value = "orgId") String orgId
			) throws Exception{ 
		 getPageInfo(request);
		 User u=(User)request.getSession().getAttribute("ECCUSER");
		 User user = new User();     
		 user.setUserName(userName);
		 user.setLoginName(loginName);
		 user.setUserStat(userStat); 
		 Org org=super.getOrg();
		 if(!"".equals(orgId)){
			 org.setUnitParentId(orgId);
		 }
		Map map = userService.getUserList(user,u,org, super.getCurrentPage(), super.getPageSize()); 
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);   
	}
	/**
	 * 方法说明： 保存用户. <BR>
	 */
	@Ecclog(value="保存用户",type="1",key="sys_user")
	@RequestMapping("/user/saveUser.do")
	public void saveUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userName") String userName ,
			@RequestParam (defaultValue="",value="loginName") String loginName ,
			@RequestParam (defaultValue="",value="userStat") String userStat ,
			@RequestParam (defaultValue="",value="mail") String mail ,
			@RequestParam (defaultValue="",value="mobile") String mobile ,
			@RequestParam (defaultValue="",value="address") String address ,
			@RequestParam (defaultValue="",value="passWord") String passWord,
			@RequestParam (defaultValue="",value="sysId") String sysId
			) throws Exception {  
			Map map = new HashMap();
			map.put("messager", "保存成功!");
			map.put("flag", true);
			
			User user = new User();     
			user.setUserName(userName);
			user.setLoginName(loginName);
			int i=userService.checkLoginName(user);
			
			if(i>0){
				map.put("messager", "保存失败!该登录名已经存在");
				map.put("flag", false);
				JsonResProcessor js=new JsonResProcessor();
				js.returnResInfo(map, response); 
			}else{
				try {
					Map<String,Object> sessionMap=(Map<String, Object>) request.getSession().getAttribute("ECCSESSIONMAP");
					User u=(User)request.getSession().getAttribute("ECCUSER");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					user.setUserStat(userStat); 
					user.setMail(mail);
					user.setMobile(mobile);
					user.setPassWord(passWord);
					user.setAddress(address);
					user.setLoginCount(0);
					user.setCreateMan(u.getId());
					user.setCreateTime(df.format(new Date()));
					user.setSysEmpId(sysId);
					 int j = userService.findEmpCount(sysId);
					 if(j==1){
						 userService.saveUser(user); 
					 }else{
						 map.put("messager", "该员工已不存在，请重新选择!");
						 map.put("flag", false);
					 }
				} catch (Exception e) {
					e.printStackTrace();
					map.clear();
					map.put("messager", "保存失败!");
					map.put("flag", false);
				}   
				JsonResProcessor js=new JsonResProcessor();
				js.returnResInfo(map, response);
			}
		 
	}
	/**
	 * 方法说明： 根据id 查找用户. <BR>
	 */
	@Ecclog(value="根据id查找用户",type="2",key="sys_user")
	@RequestMapping("/user/findUserById.do")
	public void findUserById(HttpServletRequest request, HttpServletResponse response,@RequestParam (defaultValue="",value="userId") String id ) {
		User  user =	userService.findUserById(id); 
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(user, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法说明：修改用户 . <BR>
	 */
	@Ecclog(value="修改用户",type="1",key="sys_user")
	@RequestMapping("/user/updateUser.do")
	public void updateUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String id,
			@RequestParam (defaultValue="",value="loginName") String loginName,
			@RequestParam (defaultValue="",value="userName") String userName ,
			@RequestParam (defaultValue="",value="userStat") String userStat ,
			@RequestParam (defaultValue="",value="mail") String mail ,
			@RequestParam (defaultValue="",value="mobile") String mobile ,
			@RequestParam (defaultValue="",value="address") String address ) {
		User u=new User();
		u.setId(id);
		u.setUserName(userName);
		u.setUserStat(userStat);
		u.setLoginName(loginName);
		u.setMail(mail);
		u.setMobile(mobile);
		u.setAddress(address);
		Map map = new HashMap();
		map.put("messager", "修改成功!");
		map.put("flag", true);
		int  i =	userService.updateUser(u); 
		JsonResProcessor js=new JsonResProcessor();
		
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明： 删除用户. <BR>
	 */
	@Ecclog(value="删除用户",type="1",key="sys_user")
	@RequestMapping("/user/deleteUser.do")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String id ) {
		User u=new User();
		u.setId(id);
		Map map = new HashMap();
		map.put("messager", "删除成功!"); 
		map.put("flag", true);
		int  i =	userService.deleteUser(u); 
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明： 删除用户. <BR>
	 */
	@Ecclog(value="检查账户",type="1",key="sys_user")
	@RequestMapping("/user/deleteUser.do")
	public void  checkLoginName(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="loginName") String loginName ){
		User u=new User();
		u.setLoginName(loginName);
		int  i =userService.checkLoginName(u); 
		Map map = new HashMap();
		map.put("messager", "账户检查!");
		if(i==0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//启用
	/**
	 * 方法说明： 启用. <BR>
	 */
	@Ecclog(value="启用用户",type="1",key="sys_user")
	@RequestMapping("/user/enableUser.do")
	public void enableUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String id ) {
		User u=new User();
		u.setId(id);
		Map map = new HashMap();
		map.put("messager", "启用成功!"); 
		map.put("flag", true);
		int  i =	userService.enableUser(u);  
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明： 停用. <BR>
	 */
	@Ecclog(value="停用用户",type="1",key="sys_user")
	@RequestMapping("/user/disableUser.do")
	public void disableUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String id ) {
		User u=new User();
		u.setId(id);
		Map map = new HashMap();
		map.put("messager", "停用成功!"); 
		map.put("flag", true);
		int  i =	userService.disableUser(u); 
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}