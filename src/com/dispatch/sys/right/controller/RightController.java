package com.dispatch.sys.right.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dispatch.sys.bean.Right;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.right.service.RightService;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;

/**
 * 功能描述：权限菜单管理功能信息   .  <BR>
 */
@Controller
public class RightController extends BaseController{

	@Autowired
	RightService rightService;
	
	/**
	 * 方法说明： 获取权限树. <BR>
	 */
	@Ecclog(value="获取权限树",type="2",key="sys_right") 
	@RequestMapping("/right/lodeTreeData.do")
	public void lodeTreeData(HttpServletRequest request, 
			@RequestParam (defaultValue="0",value="id") String id,HttpServletResponse response){
		
		
		 List<Map<String, Object>> map = rightService.getRightTreeData(id); 
		
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
	@Ecclog(value="获取全部权限信息树",type="2",key="sys_right") 
	@RequestMapping("/right/AllRightTreeData.do")
	public void AllRightTreeData(HttpServletRequest request, 
			@RequestParam (defaultValue="-1",value="id") String id,HttpServletResponse response){
		
		 List<Map<String, Object>> map = rightService.AllRightTreeData(id); 
			JsonResProcessor js=new JsonResProcessor(); 
			try {
				js.returnResInfo(map, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
	}
	@Ecclog(value="获取权限过滤树",type="2",key="sys_right") 
	@RequestMapping("/right/AllRightTreeDataByUser.do")
	public void AllRightTreeDataByUser(HttpServletRequest request, 
			@RequestParam (defaultValue="0",value="id") String id,HttpServletResponse response){
		 List<Map<String, Object>> map=null;
		 Map<String,Object> sessionMap=(Map<String, Object>) request.getSession().getAttribute("ECCSESSIONMAP");
		 if(sessionMap!=null){
			 Map<String,Object> rightMap=(Map<String, Object>) sessionMap.get("ECCRIGHT");
			 if(rightMap.get("stat").equals("all")){
				  map = rightService.AllRightTreeData(id); 
			 }else{
				 String rightids="";
				 List<Map<String,Object>> rightlist= (List<Map<String, Object>>) rightMap.get("rightids");
				 for(int i=0;i<rightlist.size();i++){
					 if (rightids != "") rightids += ","; 
					 	rightids += "'"+rightlist.get(i).get("RIGHTID")+"'";
				 }
					 map=rightService.AllRightTreeByUser(id, rightids);
			 }
				JsonResProcessor js=new JsonResProcessor(); 
				try {
					js.returnResInfo(map, response);
				} catch (Exception e) {

					e.printStackTrace();
				}
		 }else{
			 
		 }
		 
	}
	/**
	 * 方法说明： . <BR>
	 */
	@Ecclog(value="查找list",type="2",key="sys_right") 
	@RequestMapping("/right/selectRightByPid.do")
	public void selectRightByPid(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(defaultValue = "", value = "pid") String pid){
		    List list=rightService.selectRightByPid(super.getUser(),pid);
		    super.returnListJson(list, response);
	}
	/**
	 * 方法说明： . <BR>
	 */
	@Ecclog(value="查找list",type="2",key="sys_right") 
	@RequestMapping("/right/updateRightSeq.do")
	public void updateRightSeq(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "ids") String ids,
			@RequestParam(defaultValue = "", value = "tableName") String tableName){
		rightService.updateRightSeq(ids.split("-"),tableName);
		super.returnnBaseJson(true, "保存成功", response);
		
	}
	
	/**
	 * 方法说明：保存权限信息 . <BR>
	 */
	@Ecclog(value="保存权限",type="1",key="sys_right") 
	@RequestMapping("/right/saveRight.do")
	public void saveRight(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "json") String json
			
			)throws Exception{
			
			boolean bool = false;
			JSONObject jsonObj=JSONObject.fromObject(json);
			Right right=(Right) JSONObject.toBean(jsonObj,Right.class); 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			User user = (User)request.getSession().getAttribute("ECCUSER");
			if (right.getParentid() == "") {
				right.setParentid("0");
			} 
			
			right.setIsDelete("2");
			right.setPicUrl("");
			right.setCreatetime(df.format(new Date()));
			right.setCreateman(user.getUserName());
			bool = rightService.saveRight(right);
			super.returnnBaseJson(bool, bool==true?"保存成功":"保存失败!", response);
	}
	/**
	 * 方法说明：删除权限信息 . <BR>
	 */
	@Ecclog(value="删除权限",type="1",key="sys_right") 
	@RequestMapping("/right/deleteRight.do")
	public void deleteRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="rightId") String id ){
		Right right = new Right();
		right.setId(id);
		Map map = new HashMap();
		rightService.deleteRight(right);
		map.put("messager", "删除成功!"); 
		map.put("flag", true);
		JsonResProcessor js=new JsonResProcessor();
		
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：通过id查询权限信息 . <BR>
	 */
	@Ecclog(value="根据id查找权限",type="2",key="sys_right") 
	@RequestMapping("/right/findRightById.do")
	public void findRightById(HttpServletRequest request, HttpServletResponse response,@RequestParam (defaultValue="",value="RightId") String id ) {
		Right right = rightService.findRightById(id);
		JsonResProcessor js=new JsonResProcessor();
		try {
			js.returnResInfo(right, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明： 修改权限信息. <BR>
	 */
	@Ecclog(value="修改权限信息",type="1",key="sys_right") 
	@RequestMapping("/right/updateRight.do")
	public void updateRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="json") String json
			){
			boolean bool = false;
			User user = (User)request.getSession().getAttribute("ECCUSER");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			JSONObject jsonObj=JSONObject.fromObject(json);
			Right right=(Right) JSONObject.toBean(jsonObj,Right.class); 
			right.setCreatetime(df.format(new Date()));
			right.setCreateman(user.getUserName());
			bool = rightService.updateRight(right);
			super.returnnBaseJson(bool, bool==true?"修改成功":"修改失败!", response);
	}
	/**
	 * 方法说明：查看权限子节点 . <BR>
	 */
	@Ecclog(value="查看权限子节点",type="2",key="sys_right") 
	@RequestMapping("/right/selectChildRight.do")
	public void selectChildRight(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "rightId") String id) {
		int count = rightService.findChildrenCount(id);
		Map map = new HashMap();
		if(count>0){
			
			map.put("messager", "存在子节点,确定要全部删除吗？");
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法说明： 查询符合条件的权限菜单树. <BR>
	 */
	@Ecclog(value="查询符合条件的权限菜单树",type="2",key="ecc_unitinfo") 
	@RequestMapping("/right/selectRightTreeData.do")
	public void selectRightTreeData(HttpServletRequest request,HttpServletResponse response,
			@RequestParam (defaultValue="0",value="id") String id,
			@RequestParam (defaultValue="",value="rightName") String rightName
			){
		List<Map<String, Object>> map = rightService.selectRightTreeData(rightName);
		JsonResProcessor js=new JsonResProcessor(); 
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
