package com.dispatch.sys.org.controller;

import java.io.IOException;
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
import org.springframework.web.servlet.ModelAndView;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.sys.org.service.Orgservice;
import com.frames.base.BaseController;
import com.frames.util.CreateSqlTool;
import com.util.EnumUtil;
import com.util.common.JsonResProcessor;
import com.util.common.UUIDGenerator;

@Controller
public class OrgController extends BaseController{
	@Autowired
	private Orgservice orgservice;
	
	@Ecclog(value="组织机构树",type="2",key="sys_org") 
	@RequestMapping("/org/loadTreeDataForZTree.do")
	public void loadTreeDataForZTree(HttpServletRequest request,HttpServletResponse response){
		List list=orgservice.zTreeData(getOrg());
		super.returnListJson(list, response);
	}
	
	@Ecclog(value="组织机构树",type="2",key="sys_org") 
	@RequestMapping("/org/returnBaseLayout.do")
	public ModelAndView returnBaseLayout(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue = "", value = "menuurl") String menuurl){
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("tree/layout_tree");
		mv.addObject("menuUrl","carbon/findOrgCarbonQuantity.do");
		return mv;
	}

	/**
	 * 方法说明：构建树 . <BR>
	 */
	@Ecclog(value="组织机构树查询",type="2",key="sys_org") 
	@RequestMapping("/org/lodeTreeData.do")
	public void lodeTreeData(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "id") String id,
			@RequestParam(defaultValue = "", value = "status") String status,
			HttpServletResponse response) {
		String t = "1"; 
		if("0".equals(id)&&super.getOrg() !=null){
				id = super.checkOrgType();
				t="0";
		}
		Org org=new Org();
		org.setId(id);
		org.setIsDelete(status);
		List<Map<String, Object>> map = orgservice.getOrgTreeData(org,t); 
		super.returnListJson(map, response);
	}
	
	@RequestMapping("/org/lodeTreeDataByOrg.do")
	public void lodeTreeDataByOrg(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "id") String id,
			HttpServletResponse response){
		List<Map<String, Object>> map = orgservice.getOrgTreeDateByOrgId(id);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 保存机构信息 方法说明： . <BR>
	 */
	@Ecclog(value="保存组织机构",type="1",key="sys_org") 
	@RequestMapping("/org/saveOrg.do")
	public void saveOrg(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "orgparentId") String parentId,
			@RequestParam(defaultValue = "", value = "orgCode") String orgCode,
			@RequestParam(defaultValue = "", value = "orgName") String orgName,
			@RequestParam(defaultValue = "", value = "sortName") String sortName,
			@RequestParam(defaultValue = "", value = "jianPin") String jianPin,
			@RequestParam(defaultValue = "", value = "orgType") String orgType,
			@RequestParam(defaultValue = "", value = "managerType") String managerType,
			@RequestParam(defaultValue = "", value = "seq") String seq,
			@RequestParam(defaultValue = "", value = "createTime") String createTime,
			@RequestParam(defaultValue = "", value = "createMan") String createMan,
			@RequestParam(defaultValue = "", value = "companyType") String companyType,
			@RequestParam(defaultValue = "", value = "groupType") String groupType)
			throws Exception {
		Map map = new HashMap();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Org org = new Org();
			User user = (User) request.getSession().getAttribute("ECCUSER");
			
			org.setParentId(parentId.equals("")?"0":parentId);
			String uuid=UUIDGenerator.getUUID();
			org.setId(uuid);
			org.setOrgCode(orgCode);
			org.setOrgName(orgName);
			org.setSortName(sortName);
			org.setJianPin(jianPin);
			org.setOrgType(orgType);
			org.setManagerType(managerType);
			org.setSeq(seq);
			org.setCompanyType(companyType);
			org.setGroupType(groupType);
			org.setIsDelete(EnumUtil.RIGHTSTAT1);
			org.setCreateTime(df.format(new Date()));
			if (user != null) {
				org.setCreateMan(user.getUserName());
			}
			int i=orgservice.checkOrgCode(org);
			int k=orgservice.checkJianPin(org);
			
			if(i>0){
				map.put("messager", "组织编码重复");
				map.put("flag", false);
			};
			if(k>0){
				map.put("messager", "组织简拼重复");
				map.put("flag", false);
			};
			if(i==0&&k==0){
				orgservice.saveOrg(org,request);
				map.put("messager", "保存成功!");
				map.put("flag", true);
				map.put("id", uuid);
				map.put("orgName", orgName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("messager", "保存失败!");
			map.put("flag", true);
		}
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);
	}

	/**
	 * 方法说明：删除机构信息 . <BR>
	 */
	@Ecclog(value="删除组织机构",type="1",key="sys_org") 
	@RequestMapping("/org/deleteOrg.do")
	public void deleteOrg(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "status") String status) {
		String[] ids = id.split(",");
		String idd = "";
		for(int i=0;i<ids.length;i++){
			idd = ids[i];
			Org org = new Org();
			org.setId(idd);
			org.setIsDelete(status);
			orgservice.deleteOrg(org);
		}
		Map map = new HashMap();
		map.put("flag", true);
		
		JsonResProcessor js = new JsonResProcessor();

		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法说明：查找该节点是否为根节点 . <BR>
	 */
	@Ecclog(value="检查是否为根节点",type="2",key="sys_org") 
	@RequestMapping("/org/checkOrgRoot.do")
	public void checkOrgRoot(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "OrgId") String id){
		int count1 =orgservice.findParentCount(id);
		Map map = new HashMap();
		if(count1==0){
			map.put("messager", "此处为根节点不可删除");
			map.put("root", true);
			map.put("flag", true);
		}else{
			map.put("root", false);
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
	 * 通过id查找机构信息 方法说明： . <BR>
	 */
	@Ecclog(value="构树子节点数量",type="2",key="sys_org") 
	@RequestMapping("/org/selectChildOrg.do")
	public void selectChildOrg(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "OrgId") String id) {
		int count = orgservice.findChildrenCount(id);
		int count1 =orgservice.findParentCount(id);
		Map map = new HashMap();
		if(count1==0){
			map.put("messager", "此处为根节点不可删除");
			map.put("root", true);
			map.put("flag", true);
		}else{
			if(count>0){
				map.put("root", false);
				map.put("messager", "确定删除该节点下所有的信息么？");
				map.put("flag", true);
			}else{
				map.put("root", false);
				map.put("flag", false);
			}
			
		}
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：根据id获取组织机构 . <BR>
	 */
	@Ecclog(value="根据id获取组织机构",type="2",key="sys_org") 
	@RequestMapping("/org/findOrgById.do")
	public void findOrgById(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "OrgId") String id) {
		Org org = orgservice.findOrgById(id);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(org, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：修改机构信息 . <BR>
	 */
	@Ecclog(value="修改组织机构",type="1",key="sys_org") 
	@RequestMapping("/org/updateOrg.do")
	public void updateOrg(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String OrgId,
			@RequestParam(defaultValue = "", value = "parentId") String parentId,
			@RequestParam(defaultValue = "", value = "orgCode") String orgCode,
			@RequestParam(defaultValue = "", value = "orgName") String orgName,
			@RequestParam(defaultValue = "", value = "sortName") String sortName,
			@RequestParam(defaultValue = "", value = "jianPin") String jianPin,
			@RequestParam(defaultValue = "", value = "seq") String seq,
			@RequestParam(defaultValue = "", value = "orgType") String orgType,
			@RequestParam(defaultValue = "", value = "managerType") String managerType,
			@RequestParam(defaultValue = "", value = "companyType") String companyType,
			@RequestParam(defaultValue = "", value = "groupType") String groupType,
			@RequestParam(defaultValue = "", value = "isDelete") String isDelete) {
		User user = (User) request.getSession().getAttribute("ECCUSER");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Org org = new Org();
		org.setId(OrgId);
		org.setOrgCode(orgCode);
		org.setOrgName(orgName);
		org.setSortName(sortName);
		org.setJianPin(jianPin);
		org.setOrgType(orgType);
		org.setCompanyType(companyType);
		org.setGroupType(groupType);
		org.setManagerType(managerType);
		org.setSeq(seq);
		org.setIsDelete(isDelete);
		org.setParentId(parentId);
		org.setCreateTime(df.format(new Date()));
		if (user != null) {
			org.setCreateMan(user.getUserName());
		}
		Map map = new HashMap();
		int l = orgservice.checkOrgCode(org);
		int k=orgservice.checkJianPin(org);
		if(k>0){
			map.put("messager", "组织简拼重复");
			map.put("flag", false);
		};
		if(k==0){
			if(l>0){
				map.put("messager", "编号重复，请重新输入!");
				map.put("flag", false);
			}else{
				int i = orgservice.updateOrg(org,request);
				map.put("orgName", orgName);
				map.put("messager", "修改成功!");
				map.put("flag", true);
			}
		}
		String sql=CreateSqlTool.genInsertSql("com.dispatch.sys.bean.Org",org,"sys_org");
		System.out.println(sql);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 方法说明：查询符合条件的组织机构树 . <BR>
	 */
	@Ecclog(value="查询符合条件的组织机构树",type="1",key="sys_org") 
	@RequestMapping("/org/selectTreeData.do")
	public void selectTreeData(HttpServletRequest request,HttpServletResponse response,
			@RequestParam (defaultValue="0",value="id") String id,
			@RequestParam (defaultValue="",value="orgName") String orgName,
			@RequestParam (defaultValue="",value="managerType") String managerType
			){
		List<Map<String, Object>> map = orgservice.selectTreeData(orgName,managerType);
		JsonResProcessor js=new JsonResProcessor(); 
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法说明：组织机构调级 . <BR>
	 */
	@Ecclog(value="组织机构调级",type="1",key="sys_org") 
	@RequestMapping("/org/tjOrg.do")
	public void tjOrg(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "pid") String sureId,
			@RequestParam(defaultValue = "", value = "id") String id)
			throws Exception {
		Map map = new HashMap();
		orgservice.tjOrg(sureId,id);
		map.put("messager", "调级成功!");
		map.put("flag", true);	        
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);
	}

	/**
	 * 方法说明：查询可用的组织机构树 . <BR>
	 */
	@Ecclog(value="查询可用的组织机构树",type="2",key="sys_org") 
	@RequestMapping("/org/findTreeData.do")
	public void findTreeData(HttpServletRequest request,
			@RequestParam(defaultValue = "0", value = "id") String id,
			HttpServletResponse response) {
		Map<String,Object> sessionMap=(Map<String, Object>) request.getSession().getAttribute("ECCSESSIONMAP");
		Org org=(Org) request.getSession().getAttribute("ECCORG");
		String t = "1"; 
		if("0".equals(id)&&org !=null){
				id = org.getId();
				t="0";
		}
		List<Map<String, Object>> map = orgservice.findTreeData(id,t); 
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：获取组织列表 . <BR>
	 */
	@Ecclog(value="获取组织列表",type="2",key="sys_org") 
	@RequestMapping("/org/selectOrgList.do")
	public void selectOrgList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="orgName") String orgName,
			@RequestParam (defaultValue="",value="managerType") String managerType){
		getPageInfo(request);
		String oid = this.checkOrgType();
		Org org = new Org();
		org.setOrgName(orgName);
		org.setManagerType(managerType);
		Map map = orgservice.selectOrgList(org,super.getCurrentPage(), super.getPageSize(),oid);
		super.returnObjectJson(map, response);
	}
	
	public Orgservice getOrgservice() {
		return orgservice;
	}

	public void setOrgservice(Orgservice orgservice) {
		this.orgservice = orgservice;
	}

}
