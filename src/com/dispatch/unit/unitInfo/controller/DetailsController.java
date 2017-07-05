package com.dispatch.unit.unitInfo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.dispatch.sys.bean.EmpLoyee;
import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.service.DetailsService;
import com.util.EnumUtil;
import com.util.common.JsonResProcessor;

/**
 * 功能描述：热源功能信息  .  <BR>
 */
@SuppressWarnings("static-access")
@Controller
public class DetailsController {
	@Autowired
	private DetailsService detailsService;
	
	/**
	 * 方法说明：保存热源信息 . <BR>
	 */
	@Ecclog(value="保存热源信息",type="1",key="ieu_unitInfo")
	@RequestMapping("/details/saveDetails.do")
	public void saveDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="orgId") String orgId ,
			@RequestParam (defaultValue="",value="orgName") String orgName ,
			@RequestParam (defaultValue="",value="unitId") String unitId ,//父id
			@RequestParam (defaultValue="",value="unitName") String unitName ,
			@RequestParam (defaultValue="",value="unitCode") String unitCode ,
			@RequestParam (defaultValue="",value="shortName") String shortName ,
			@RequestParam (defaultValue="",value="jianPin") String jianPin ,
			@RequestParam (defaultValue="",value="seq") String seq ,
			@RequestParam (defaultValue="",value="remarks") String remarks,
			@RequestParam (defaultValue="",value="unitType") String unitType,
			@RequestParam (defaultValue="",value="status") String status 
			) throws Exception {  
			Map map = new HashMap();
			
		try {//名称、编号、简称、简拼、排序、备注  
			//隐藏信息：性质、状态、创建人编号、创建人部门编号、创建时间、
			
			User u2=(User)request.getSession().getAttribute("ECCUSER");
			Org o2=(Org) request.getSession().getAttribute("ECCORG");
			EmpLoyee e2=(EmpLoyee)request.getSession().getAttribute("ECCEMP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			UnitInfo unitInfo = new UnitInfo();  
			unitInfo.setUnitName(unitName);
			unitInfo.setUnitCode(unitCode);
			unitInfo.setShortName(shortName);
			unitInfo.setJianPin(jianPin);
			if(unitId.isEmpty()){
				unitInfo.setUnitId("0");
			}else{
				unitInfo.setUnitId(unitId);
			}
			unitInfo.setSeq("7");
			unitInfo.setRemarks(remarks);
			unitInfo.setUnitType(unitType);
			unitInfo.setStatus(EnumUtil.PARAMETERSSTATUS1);//启用
			unitInfo.setCreateUserID(u2.getId());
			unitInfo.setCreateUser(u2.getUserName());
			unitInfo.setOrgId(orgId);
			unitInfo.setOrgName(orgName);
			unitInfo.setCreateUserDepartmentID(o2.getId());
			unitInfo.setCreateUserDepartment(o2.getOrgName());
			
			unitInfo.setCreateDate(df.format(new Date()));
			
			detailsService.saveDetails(unitInfo);
			map.put("messager", "保存成功!");
			map.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("messager", "保存失败!");
			map.put("flag", false);
		}   
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response); 
	}
	
	/**
	 * 方法说明：通过id查询热源信息 . <BR>
	 */
	@Ecclog(value="通过id查询热源信息",type="2",key="ieu_unitInfo")
	@RequestMapping("/details/findDetailsById.do")
	public void findDetailsById(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "detailsId") String id) {
		UnitInfo unitInfo   = detailsService.findDetailsById(id);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(unitInfo, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法说明：通过用能id查询热源详细信息 . <BR>
	 */
	@Ecclog(value="通过用能id查询热源详细信息",type="1",key="ecc_ieu_unitinfo_details")
	@RequestMapping("/details/findDetailsByUnitId.do")
	public void findDetailsByUnitId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="10",value="rows") int pageSize ,
			@RequestParam (defaultValue="1",value="page") int currentPage ,
			@RequestParam (defaultValue="",value="id") String id 
			) throws Exception{ 
		Map map = detailsService.findDetailsByUnitId(id,currentPage,pageSize);
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(map, response);
	}
	
	/**
	 * 方法说明： 批量保存热源子表信息. <BR>
	 */
	@Ecclog(value="批量保存热源子表信息",type="1",key="ecc_ieu_unitinfo_details")
	@RequestMapping("/details/saveDetailsBatch.do")
	public void saveDetailsBatch(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "detailsObj") String detailsObj,
			@RequestParam(defaultValue = "", value = "unitId") String unitId) {
		User user=(User)request.getSession().getAttribute("ECCUSER");
		Org org=(Org) request.getSession().getAttribute("ECCORG");
		List<JSONObject> list = new ArrayList<JSONObject>();
		Map map = new HashMap();
		
		if(!"[]".equals(detailsObj)){
			String objs = detailsObj.substring(1, detailsObj.length()-1).replace("},{", "};{");
			String[] objStr = objs.split(";");
			for(int i=0;i<objStr.length;i++){
				JSONObject jsonObject = JSONObject.fromObject(objStr[i]);
				list.add(jsonObject);
			}
		}
		int j = 0;
		String info="";
		String id = null;
		for(int k=0;k<list.size();k++){
			if(list.get(k).get("ID")!=null){
				id =id + "," + list.get(k).get("ID").toString();
			}
		}
		for(int i=0;i<list.size();i++){
			String changeDate = list.get(i).get("CHANGEDATE").toString();
			List li = new ArrayList();
			JSONObject jo = list.get(i);
			li.add(jo);
			String status = list.get(i).get("STATUS").toString();
			if(status.equals(EnumUtil.PARAMETERSSTATUS1)){
				j = detailsService.checkBydate(changeDate,unitId,id);
				if(j>0){
					info="同一个变更日期只能有一条可用的子信息!";
					map.put("flag", false);
					break;
					
				}else{
					try {
						detailsService.saveDetailsBatch(li,unitId,user,org);
						info="保存成功!";
						map.put("flag", true);
					} catch (Exception e) {
						info="保存失败!";
						map.put("flag", true);
						break;
					}
				}
			}else{
				detailsService.saveDetailsBatch(li,unitId,user,org);
				info="保存成功!";
				map.put("flag", true);
			}
		}
		map.put("messager", info);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			map.put("messager", "保存失败!");
			map.put("flag", false);
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：删除热源详细信息 . <BR>
	 */
	@Ecclog(value="删除热源详细信息",type="1",key="ecc_ieu_unitinfo_details")
	@RequestMapping("/details/deleteChildInfo.do")
	public void deleteChildInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "deleteChildObj") String deleteChildObj){
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(!"[]".equals(deleteChildObj)){
			String objs = deleteChildObj.substring(1, deleteChildObj.length()-1).replace("},{", "};{");
			String[] objStr = objs.split(";");
			for(int i=0;i<objStr.length;i++){
				JSONObject jsonObject = JSONObject.fromObject(objStr[i]);
				list.add(jsonObject);
			}
		}
		detailsService.deleteChildInfo(list);
		Map map = new HashMap();
		map.put("messager", "删除成功!");
		map.put("flag", true);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			map.put("messager", "删除失败!");
			map.put("flag", false);
			e.printStackTrace();
		}
	}
}
