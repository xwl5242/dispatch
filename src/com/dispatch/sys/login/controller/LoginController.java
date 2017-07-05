package com.dispatch.sys.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dispatch.sys.bean.EmpLoyee;
import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.cache.GetDataDicCache;
import com.dispatch.sys.employee.service.EmpLoyeeService;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.org.service.Orgservice;
import com.dispatch.sys.right.service.RightService;
import com.dispatch.sys.user.service.UserService;
import com.dispatch.unit.unit.service.UnitService;
import com.dispatch.unit.unitInfo.service.UnitInfoService;
import com.frames.base.BaseController;
import com.util.EnumUtil;
import com.util.common.JsonResProcessor;

@Controller
public class LoginController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private Orgservice orgservice;
	@Autowired
	private EmpLoyeeService empLoyeeService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private RightService rightService;
	@Autowired
	private UnitInfoService unitInfoService; 
	
	/**
	 * 方法说明：登录系统 . <BR>
	 */
	@Ecclog("集成登录系统")
	@RequestMapping("/login/newCheckLogin.do")
	public void newCheckLogin(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam (defaultValue="",value="loginName") String loginName , 
			@RequestParam (defaultValue="",value="password") String password) throws Exception{
		Map<String,Object> eccSessionMap = new HashMap<String,Object>();
		Map<String,Object> rightMap = new HashMap<String,Object>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("messager", "登录成功!");
		map.put("flag", true);
		EmpLoyee emp=null;
		Org org=null;
		GetDataDicCache.columnMap=userService.getDataBaseColumnList();
		
		User viewuser = new User();     
		viewuser.setLoginName(loginName);
		viewuser.setPassWord(password);
		User user = userService.userLogin(viewuser); 
		if(user==null){ 
			map.clear();
			map.put("messager", "用户名密码不对!");
			map.put("flag", false);
		}else if("2".equals(user.getUserStat())){
			map.clear();
			map.put("messager", "该用户已被停用!");
			map.put("flag", false);
		}else if("3".equals(user.getUserStat())){
			map.clear();
			map.put("messager", "该用户无效！");
			map.put("flag", false);
		}else{
			if(user.getSysEmpId()!=null){
				emp=empLoyeeService.findEmpLoyeeById(user.getSysEmpId());
				if(emp.getOrgcode()!=null){
					org=orgservice.findOrgById(emp.getOrgcode());
					org.setUnitParentId(checkOrgType(org));
					userService.saveUserCount(user);
				}
			} 
		}
		
		eccSessionMap.put("ECCUSER", user);
		eccSessionMap.put("ECCEMP", emp);
		eccSessionMap.put("ECCORG", org);
		if("admin".equals(loginName)){
			rightMap.put("stat", "all");
			eccSessionMap.put("ECCRIGHT", rightMap);
		}
		HttpSession session = request.getSession();
		session.setAttribute("ECCUSER", user);
		session.setAttribute("ECCEMP", emp);
		session.setAttribute("ECCORG", org); 
		session.setAttribute("ECCSESSIONMAP", eccSessionMap); 
		
		JsonResProcessor js=new JsonResProcessor();  
		js.returnResInfo(map, response); 
	}
	
	/**
	 * 方法说明： 注销session. <BR>
	 */
	@Ecclog("注销登录/退出")
	@RequestMapping("/login/removeSession.do")
	public void removeSession(HttpServletRequest request, HttpServletResponse response){
		User user=(User)request.getSession().getAttribute("ECCUSER");
		if(user!=null){
			request.getSession().removeAttribute("ECCUSER");
			request.getSession().removeAttribute("ECCEMP");
			request.getSession().removeAttribute("ECCORG");
			request.getSession().removeAttribute("ECCSESSIONMAP");
			
		}
		super.returnnBaseJson(true, "注销成功!", response);
	} 
	
	/**
	 * 方法说明： 修改密码. <BR>
	 */
	@Ecclog("修改密码")
	@RequestMapping("/login/updatePassWord.do")
	public void updatePassWord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="oldPassWord") String oldPassword,
			@RequestParam (defaultValue="",value="newPassWord") String newPassWord
			){
		User user=(User) request.getSession().getAttribute("ECCUSER");
		Map map = new HashMap();
		map.put("messager", "修改成功!");  
		map.put("flag", true);
		try {
			if(user.getPassWord().equals(oldPassword)){
				user.setPassWord(newPassWord);
				userService.updatePassWord(user);
				request.getSession().removeAttribute("ECCUSER");
				request.getSession().setAttribute("ECCUSER", user); 
			}else{
				map.clear();
				map.put("messager", "原始密码不正确!");
				map.put("flag", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("messager", "修改失败!");
			map.put("flag", false);
		}
		JsonResProcessor js=new JsonResProcessor();
			try {
				js.returnResInfo(map, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * 方法说明： . <BR>
	 */
	@Ecclog("获取首页信息")
	@RequestMapping("/login/main.do")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response,@RequestParam (defaultValue="",value="id") String id,
			@RequestParam (defaultValue="",value="url") String url,@RequestParam (defaultValue="2",value="rType") String rType){
		User u2=(User)request.getSession().getAttribute("ECCUSER");
		ModelAndView mv=this.getModelAndView();
		List sysList=new ArrayList();
		sysList=rightService.getSystemRight("");
		
		List<Right>  rightList=new ArrayList();
		
		if(!"".equals(id)&&id!=null&&u2!=null){
			rightList=rightService.getSystemMenuList(u2, id);
		}
		//取二级三级菜单
		if(null!=sysList&&!sysList.isEmpty()){
			for (Object object : sysList) {
				if(null==object||null==u2){
					continue;
				}
				Map<String,Object> map=(Map<String, Object>) object;
				List subRight=rightService.getSystemMenuList(u2,map.get("ID").toString());
				if(null!=subRight&&!subRight.isEmpty()){
					map.put("hasSubRight", true);
					map.put("subRight", subRight);
				}
			}
		}
		mv.addObject("rightList",rightList);
		mv.addObject("sysList",sysList);
		mv.addObject("url",url.equals("")?"/login/systemHome.do":url);
		
		if(u2==null){
			mv.setViewName("sys/admin/login");
		}else{
			mv.addObject("username",u2.getLoginName());
			mv.setViewName("sys/new/index");
			mv.addObject("favorite", rightService.userFavorite(u2));
			
			List list = userService.findNamesByOid(u2.getId());
			HashMap map = (HashMap)list.get(0);
			Object username = map.get("USERNAME");
			Object orgname = map.get("ORGNAME");
			mv.addObject("usernamev",username);
			mv.addObject("orgname",orgname);
			
			if(rType.equals("2")){
				
				List unitList=userService.getUnitList(super.getOrg());
				mv.addObject("unitList",unitList);
				mv.addObject("cType",super.getOrg().getCompanyType());
			}
			
		}
		mv.addObject("rType",rType);
		return mv;
	}
	
	/**
	 * 方法说明： . <BR>
	 */
	@Ecclog("获取首页信息")
	@RequestMapping("/login/refreshFavorite.do")
	public void refreshFavorite(HttpServletRequest request, HttpServletResponse response){
		
		User user=getUser();
		if(user!=null){
			List list=rightService.userFavorite(user);
			super.returnListJson(list, response);
		}
		
	}
	
	@Ecclog("获取首页信息")
	@RequestMapping("/login/systemHome.do")
	public ModelAndView systemHome(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	
	public String checkOrgType(Org org){
		String pid =org.getId();
		String orgType=org.getOrgType();
		if(orgType.equals(EnumUtil.ORGTYPE1)){
			return pid;
		}
		List list = unitService.findUnitId(pid);
		HashMap map = (HashMap)list.get(0);
		String id = map.get("ID").toString();
		return id;
	}
	
	/**
	 * 方法说明： . <BR>
	 */
	@Ecclog("二级首页首页信息")
	@RequestMapping("/login/secondMain.do")
	public ModelAndView secondMain(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam (defaultValue="",value="id") String id,
			@RequestParam (defaultValue="2",value="rType") String rType){
		User u2=(User)request.getSession().getAttribute("ECCUSER");
		ModelAndView mv=this.getModelAndView();
		List<Right>  rightList=new ArrayList();
		if(!"".equals(id)&&id!=null&&u2!=null){
			rightList=rightService.getSystemMenuList(u2,id);
		}
		JSONArray array = JSONArray.fromObject(rightList);     
		mv.addObject("rightList",array);
		if(u2==null){
			mv.setViewName("sys/admin/login");
		}else{
			mv.addObject("username",u2.getLoginName());
			mv.setViewName("sys/new/second");
			if(rType.equals("2")){
				List unitList=userService.getUnitList(super.getOrg());
				mv.addObject("unitList",unitList);
				mv.addObject("cType",super.getOrg().getCompanyType());
			}
		}
		mv.addObject("rType",rType);
		return mv;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Orgservice getOrgservice() {
		return orgservice;
	}

	public void setOrgservice(Orgservice orgservice) {
		this.orgservice = orgservice;
	}

	public EmpLoyeeService getEmpLoyeeService() {
		return empLoyeeService;
	}

	public void setEmpLoyeeService(EmpLoyeeService empLoyeeService) {
		this.empLoyeeService = empLoyeeService;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
}
