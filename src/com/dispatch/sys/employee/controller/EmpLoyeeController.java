package com.dispatch.sys.employee.controller;

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

import com.dispatch.sys.bean.EmpLoyee;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.employee.service.EmpLoyeeService;
import com.dispatch.sys.log.Ecclog;
import com.frames.base.BaseController;
import com.util.common.JsonResProcessor;

@Controller
public class EmpLoyeeController extends BaseController{
	@Autowired
	private EmpLoyeeService EmpLoyeeService;

	/**
	 * 功能描述： 获取用户列表 . <BR>
	 */
	@Ecclog(value="员工列表查询",type="2",key="sys_empLoyee") 
	@RequestMapping("/employee/getEmpLoyeeList.do")
	public void getEmpLoyeeList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "EmpName") String EmpLoyeeName,
			@RequestParam(defaultValue = "", value = "EmpSex") String EmpLoyeeSex,
			@RequestParam(defaultValue = "", value = "orgId") String orgId,
			@RequestParam(defaultValue = "", value = "Empcode") String EmpLoyeeCode)
			throws Exception {
		getPageInfo(request);
		EmpLoyee empLoyee = new EmpLoyee();
		empLoyee.setName(EmpLoyeeName);
		empLoyee.setSex(EmpLoyeeSex);
		empLoyee.setCode(EmpLoyeeCode);
		empLoyee.setOrgcode(orgId);
		Map map = EmpLoyeeService.getEmpLoyeeList(empLoyee, super.getCurrentPage(),
				super.getPageSize());
		JsonResProcessor js = new JsonResProcessor();
		js.returnResInfo(map, response);
	}
	/**
	 * 方法说明： 保存员工. <BR>
	 */
	@Ecclog(value="保存员工信息",type="1",key="sys_empLoyee") 
	@RequestMapping("/employee/saveEmpLoyee.do")
	public void saveEmpLoyee(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "EmpLoyeeName") String EmpLoyeeName,
			@RequestParam(defaultValue = "", value = "EmpLoyeeAge") String EmpLoyeeAge,
			@RequestParam(defaultValue = "", value = "EmpLoyeeMobile") String EmpLoyeeMobile,
			@RequestParam(defaultValue = "", value = "EmpLoyeeCode") String EmpLoyeeCode,
			@RequestParam(defaultValue = "", value = "EmpLoyeeSex") String EmpLoyeeSex,
			@RequestParam(defaultValue = "", value = "EmpLoyeeOrgcode") String EmpLoyeeOrgcode)
			throws Exception {
		Map map = new HashMap();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			EmpLoyee EmpLoyee = new EmpLoyee();
			User user = (User) request.getSession().getAttribute("ECCUSER");
			EmpLoyee.setName(EmpLoyeeName);
			EmpLoyee.setAge(EmpLoyeeAge);
			EmpLoyee.setMobile(EmpLoyeeMobile);
			EmpLoyee.setCode(EmpLoyeeCode.trim());
			EmpLoyee.setSex(EmpLoyeeSex);
			EmpLoyee.setOrgcode(EmpLoyeeOrgcode);
			EmpLoyee.setCreateman(user.getUserName());
			EmpLoyee.setIsdelete("2");
			EmpLoyee.setCreatetime(df.format(new Date()));
            int i=EmpLoyeeService.checkGH(EmpLoyee);
            if(i>0){
            	map.put("messager", "工号不可重复");
        		map.put("flag", false);
            }else{
                map.put("messager", "保存成功!");
         		map.put("flag", true);
         		EmpLoyeeService.saveEmpLoyee(EmpLoyee);
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
	
	@Ecclog(value="保存员工信息",type="1",key="sys_empLoyee") 
	@RequestMapping("/employee/checkEmpCode.do")
	public void checkEmpCode(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue = "", value = "code") String code){
		EmpLoyee EmpLoyee = new EmpLoyee();
		EmpLoyee.setCode(code.trim());
		int i=EmpLoyeeService.checkGH(EmpLoyee);
		super.returnnBaseJson(i>0?false:true,i>0?"工号重复":"" , response);
	}

	/**
	 * 方法说明：通过id查找员工信息 . <BR>
	 */
	@Ecclog(value="根据id查找员工",type="2",key="sys_empLoyee") 
	@RequestMapping("/employee/findEmpLoyeeById.do")
	public void findEmpLoyeeById(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "EmpLoyeeId") String id) {
		EmpLoyee EmpLoyee = EmpLoyeeService.findEmpLoyeeById(id);
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(EmpLoyee, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法说明： 修改员工信息. <BR>
	 */
	@Ecclog(value="修改员工信息",type="1",key="sys_empLoyee") 
	@RequestMapping("/employee/updateEmpLoyee.do")
	public void updateEmpLoyee(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,
			@RequestParam(defaultValue = "", value = "name") String EmpLoyeeName,
			@RequestParam(defaultValue = "", value = "age") String EmpLoyeeAge,
			@RequestParam(defaultValue = "", value = "mobile") String EmpLoyeeMobile,
			@RequestParam(defaultValue = "", value = "code") String EmpLoyeeCode,
			@RequestParam(defaultValue = "", value = "sex") String EmpLoyeeSex,
			@RequestParam(defaultValue = "", value = "orgcode") String EmpLoyeeOrgcode) {
		EmpLoyee e = new EmpLoyee();
		User user = (User) request.getSession().getAttribute("ECCUSER");
		e.setId(id);
		e.setName(EmpLoyeeName);
		e.setAge(EmpLoyeeAge);
		e.setMobile(EmpLoyeeMobile);
		e.setCode(EmpLoyeeCode.trim());
		e.setSex(EmpLoyeeSex);
		if (user != null) {
			e.setCreateman(user.getUserName());
		}

		e.setOrgcode(EmpLoyeeOrgcode);

		Map map = new HashMap();
		int i=EmpLoyeeService.checkGH(e);
        if(i>0){
        	map.put("messager", "工号不可重复");
    		map.put("flag", false);
        }else{
            map.put("messager", "保存成功!");
     		map.put("flag", true);
     		EmpLoyeeService.updateEmpLoyee(e);
        }
		JsonResProcessor js = new JsonResProcessor();
		try {
			js.returnResInfo(map, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 删除员工信息 方法说明： . <BR>
	 */
	@Ecclog(value="删除员工信息",type="1",key="sys_empLoyee") 
	@RequestMapping("/employee/deleteEmpLoyee.do")
	public void deleteEmpLoyee(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "EmpLoyeeId") String ids) {
		String[]idss=ids.split(",");
		for(String id:idss){
			if(id!=null && !id.equals("")){
				EmpLoyee emp = new EmpLoyee();
				emp.setId(id);
				EmpLoyeeService.deleteEmpLoyee(emp);
			}
		}
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