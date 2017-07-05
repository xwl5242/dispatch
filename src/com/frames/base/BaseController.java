package com.frames.base;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.right.service.RightService;
import com.frames.util.Page;
import com.frames.util.PageData;
import com.util.common.JsonResProcessor;
import com.util.common.UUIDGenerator;

public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RightService rightService;
	
	private int pageSize;
	
	private int currentPage;
	
	public void getPageInfo(HttpServletRequest request){
		int pageSize=Integer.parseInt(request.getParameter("rows")+"");
		int currentPage=1;
		if(request.getParameter("page")!=null){
			currentPage=Integer.parseInt(request.getParameter("page")+"");
		}
	
		this.pageSize=pageSize;
		this.currentPage=currentPage;
	}
	
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	public  JsonResProcessor getJsonResProcessor(){
		return new JsonResProcessor();
	}
	
	/**
	 * 方法说明： ajax基础返回方法. <BR>
	 */
	public void returnnBaseJson(boolean type,String msg,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		HashMap map=new HashMap();
		map.put("flag", type);
		map.put("messager", msg);
		try {
			json.returnResInfo(map, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	public void returnListJson(List list,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		try {
			json.returnResInfo(list, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	public void returnObjectJson(Object obj,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		try {
			json.returnResInfo(obj, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	public void returnObjectJsonForList(Object obj,HttpServletResponse response){
		JsonResProcessor json=new JsonResProcessor();
		try {
			json.returnResInfoForList(obj, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}
	
	/**
	 * 得到32位的uuid
	 */
	public String get32UUID(){
		
		return UUIDGenerator.getUUID();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	/**
	 * 方法说明： . <BR>
	 */
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	/**
	 * 方法说明： . <BR>
	 */
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	/**
	 * 方法说明：公共方法获取用户系统权限 . <BR>
	 */
	public List sysRightList(){
		List sysList= new ArrayList();
		sysList=rightService.getSystemRight("");
		return sysList;
	}
	/**
	 * 方法说明：获取用户二级权限 . <BR>
	 */
	public List<Right> sysSubRightList(String id){
		List<Right>  rightList=new ArrayList();
		if(!"".equals(id)&&id!=null&&getUser()!=null){
			rightList=rightService.getSystemMenuList(getUser(),id);
		}
		return rightList;
	}
	/**
	 * 方法说明：获取用户二级权限 . <BR>
	 */
	public List<Right> sysSubRightList(List sysList){
		List<Right>  rightList=new ArrayList();
		String id="";
		for(int i=0;i<sysList.size();i++){
			HashMap map=(HashMap)sysList.get(i);
			List tempList=new ArrayList();
			id=map.get("ID")+"";
			if(!"".equals(id)&&id!=null&&getUser()!=null){
				tempList=rightService.getSystemMenuList(getUser(),id);
				rightList.addAll(tempList);
			}
			
		}
		return rightList;
	}
	
	/**
	 * 方法说明：获取session用户 . <BR>
	 */
	public User getUser(){
		User u2=(User)getRequest().getSession().getAttribute("ECCUSER");
		return u2;
	}
	/**
	 * 方法说明：获取session组织 . <BR>
	 */
	public Org getOrg(){
		Org org=(Org)getRequest().getSession().getAttribute("ECCORG");
		return org;
	}
	
	public BaseData getBaseData(BaseData baseData){
		Org org=(Org)getRequest().getSession().getAttribute("ECCORG");
		User u2=(User)getRequest().getSession().getAttribute("ECCUSER");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String createDate = df.format(new Date());// new Date()为获取当前系统时间
		baseData.setCreateDate(createDate);
		baseData.setCreateUser(u2.getUserName());
		baseData.setCreateUserID(u2.getId());
		baseData.setCreateUserDepartment(org.getOrgName());
		baseData.setCreateUserDepartmentID(org.getId());
		baseData.setUpdateUser(u2.getUserName());
		baseData.setUpdateUserID(u2.getId());
		baseData.setUpdateUserDepartment(org.getOrgName());
		baseData.setUpdateUserDepartmentID(org.getId());
		baseData.setUpdateDate(createDate);
		return baseData;
	}
	
	/**
	 * 方法说明：检测单位类型，并返回单位ID . <BR>
	 */
	public String checkOrgType(){
		return getOrg().getUnitParentId();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
