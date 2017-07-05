package com.dispatch.sys.role.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.dispatch.sys.bean.Role;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.role.service.RoleService;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;
@Controller
public class RoleController extends BaseController{
	@Autowired
	private RoleService roleservice;
	/**
	 * 方法说明：获取角色列表 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param RoleName
	 * @throws Exception
	 * @return: void
	 * @Author: yaohaoxing <BR>
	 * @Datetime：2015年9月1日 上午11:08:00 <BR>
	 */
	@Ecclog(value="获取角色列表",type="2",key="sys_role") 
	@RequestMapping("/role/getRoleList.do")
	public void getRoleList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rolename") String RoleName
			) throws Exception{ 
		getPageInfo(request);
		User user=(User)request.getSession().getAttribute("ECCUSER");
		Role role = new Role(); 
		role.setRolename(RoleName);
		Map map = roleservice.getRoleList(role,user, super.getCurrentPage(), super.getPageSize()); 
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);   
	}
	/**
	 * 方法说明：获取角色列表 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param currentPage
	 * @param RoleName
	 * @throws Exception
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:17:24 <BR>
	 */
	@Ecclog(value="获取角色列表",type="2",key="sys_role") 
	@RequestMapping("/role/getRoleListByUser.do") 
	public void getRoleListByUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="10",value="rows") int pageSize ,
			@RequestParam (defaultValue="1",value="page") int currentPage ,
			@RequestParam (defaultValue="",value="rolename") String RoleName,
			@RequestParam (defaultValue="",value="userId") String userId
			) throws Exception{  
		Org o2=(Org) request.getSession().getAttribute("ECCORG"); 
		User user = (User)request.getSession().getAttribute("ECCUSER");
		Role role = new Role();    
		 role.setRolename(RoleName);
		 Map map = roleservice.getRoleListByUser(role,o2,user, currentPage, pageSize, userId); 
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);   
	}
	@Ecclog(value="获取用户角色权限菜单",type="2",key="sys_role") 
	@RequestMapping("/role/getRoleRightByUserId.do") 
	public void getRoleRightByUserId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String userId
			) throws Exception{  
		 List list = roleservice.getRoleRightByUserId(userId); 
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(list, response);   
	}
	/**
	 * 方法说明：保存用户角色授权 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param currentPage
	 * @param RoleName
	 * @throws Exception
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:17:24 <BR>
	 */
	@Ecclog(value="保存用户角色授权",type="1",key="sys_role") 
	@RequestMapping("/role/saveUserRole.do") 
	public void saveUserRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String userId,
			@RequestParam (defaultValue="",value="roleIds") String roleIds
			) throws Exception{  
		Map map = new HashMap();
		map.put("messager", "保存成功!");
		map.put("flag", true);
		try {
			roleservice.saveUserRole(userId,roleIds); 
		} catch (Exception e) {
			
			map.clear();
			map.put("messager", "保存失败!");
			map.put("flag", true);
		}
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);   
	}
	/**
	 * 方法说明：删除用户角色授权 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param currentPage
	 * @param RoleName
	 * @throws Exception
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:17:24 <BR>
	 */
	@Ecclog(value="删除用户角色授权",type="1",key="sys_role") 
	@RequestMapping("/role/deleteUserRole.do") 
	public void deleteUserRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="userId") String userId,
			@RequestParam (defaultValue="",value="roleIds") String roleIds
			) throws Exception{  
		Map map = new HashMap();
		map.put("messager", "删除成功!");
		map.put("flag", true);
		try {
			roleservice.deleteUserRole(userId,roleIds); 
		} catch (Exception e) {
			// TODO: handle exception
			map.clear();
			map.put("messager", "删除失败!");
			map.put("flag", true);
		}
		
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);   
	}
	
	/**
	 * 方法说明：获取角色列表 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param currentPage
	 * @param RoleName
	 * @throws Exception
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:17:24 <BR>
	 */
	@Ecclog(value="获取角色列表",type="2",key="sys_role") 
	@RequestMapping("/role/getRoleListByUserRight.do") 
	public void getRoleListByUserRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="10",value="rows") int pageSize ,
			@RequestParam (defaultValue="1",value="page") int currentPage ,
			@RequestParam (defaultValue="",value="rolename") String RoleName,
			@RequestParam (defaultValue="",value="userId") String userId
			) throws Exception{  
		 Role role = new Role();   
		 role.setRolename(RoleName);
		Map map = roleservice.getRoleListByUserRight(role, currentPage, pageSize, userId); 
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);   
	}
	/**
	 * 方法说明：保存角色 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param RoleName
	 * @param RoleDesc
	 * @throws Exception
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:17:48 <BR>
	 */
	@Ecclog(value="保存角色",type="1",key="sys_role") 
	@RequestMapping("/role/saveRole.do")
	public void saveRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rolename") String RoleName ,
			@RequestParam (defaultValue="",value="roledesc") String RoleDesc
			) throws Exception {  
			Map map = new HashMap();
			map.put("messager", "保存成功!");
			map.put("flag", true);
			
			
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			User user = (User)request.getSession().getAttribute("ECCUSER");
			Role Role = new Role();     
		    Role.setRolename(RoleName);
			Role.setRoledesc(RoleDesc);
			Role.setSeq("10");
			Role.setIsDelete("2");
			if(user!=null){
				Role.setCreateman(user.getId());
			}
			
			Role.setCreatetime(df.format(new Date()));
			roleservice.saveRole(Role); 
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("messager", "保存失败!");
			map.put("flag", true);
		}   
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response); 
	}
	/**
	 * 方法说明：根据id查找角色 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param id
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:18:14 <BR>
	 */
	@Ecclog(value="根据id查找角色",type="2",key="sys_role") 
	@RequestMapping("/role/findRoleById.do")
	public void findRoleById(HttpServletRequest request, HttpServletResponse response,@RequestParam (defaultValue="",value="RoleId") String id ) {
		Role  Role =	roleservice.findRoleById(id); 
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(Role, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明： 修改角色. <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param id
	 * @param RoleName
	 * @param RoleDesc
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:18:22 <BR>
	 */
	@Ecclog(value="修改角色",type="1",key="sys_role") 
	@RequestMapping("/role/updateRole.do")
	public void updateRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="RoleId") String id,
			@RequestParam (defaultValue="",value="rolename") String RoleName ,
			@RequestParam (defaultValue="",value="roledesc") String RoleDesc ) {
		Role r=new Role();
		User user = (User)request.getSession().getAttribute("ECCUSER");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		r.setId(id);
		r.setRolename(RoleName);
		r.setRoledesc(RoleDesc);
		if(user!=null){
			r.setCreateman(user.getUserName());
		}
		r.setCreatetime(df.format(new Date()));
		Map map = new HashMap();
		map.put("messager", "修改成功!");
		map.put("flag", true);
		int  i =	roleservice.updateRole(r); 
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：删除角色 . <BR>
	 * @see com.ecc.sys.role.controller.RoleController <BR>
	 * @param request
	 * @param response
	 * @param ids
	 * @return: void
	 * @Author: liuzijian <BR>
	 * @Datetime：2014年12月22日 下午4:18:41 <BR>
	 */
	@Ecclog(value="删除角色",type="1",key="sys_role") 
	@RequestMapping("/role/deleteRole.do")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="RoleId") String ids ) {
		
		String[]idss=ids.split(",");
		for(String id:idss){
			if(id!=null && !id.equals("")){
				Role r=new Role();
				r.setId(id);
				roleservice.deleteRole(r);
			}
		}
		
		Map map = new HashMap();
		map.put("messager", "删除成功!"); 
		map.put("flag", true);
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}