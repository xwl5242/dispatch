/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  ${objectName}Controller.java     <BR>
 * Author: ${author}   <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date:   ${nowDate}  <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 
package com.ecc.${packageName}.${objectNameLower}.controller;

import com.ecc.sys.log.Ecclog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.frames.base.BaseController;
import com.frames.util.ObjectExcelView;
import com.frames.util.Page;
import com.frames.util.PageData;
import com.dtransfer.util.common.JsonResProcessor;

import com.ecc.${packageName}.${objectNameLower}.service.${objectName}Service;


/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者: ${author}  <BR>
 * 时间：    ${nowDate} <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：  ${nowDate}  <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
@Controller
@RequestMapping(value="/${objectNameLower}")
public class ${objectName}Controller extends BaseController{
	@Autowired
	private ${objectName}Service ${objectNameLower}Service;
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.controller.${objectName}Contorller <BR>
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @return: ModelAndView
	 * @Author: ${author} <BR>
	 * @Datetime： ${nowDate}  <BR>
	 */
	@Ecclog(value="Demo列表查询",type="2",key="demo_list")
	@RequestMapping(value="/list${objectName}.do")
	public ModelAndView list${objectName}(HttpServletRequest request, HttpServletResponse response,Page page){
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("demo/test/test");
		PageData pd = new PageData();
		page.setPd(pd);
		List	varList =${objectNameLower}Service.findList${objectName}(page);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.controller.${objectName}Contorller <BR>
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @return: ModelAndView
	 * @Author: ${author} <BR>
	 * @Datetime： ${nowDate}  <BR>
	 */
	@Ecclog(value="Demo新增",type="2",key="demo_add")
	@RequestMapping(value="/goAdd${objectName}")
	public ModelAndView goAdd${objectName}(HttpServletRequest request, HttpServletResponse response,Page page){
		ModelAndView mv=this.getModelAndView();
		mv.setViewName("demo/test/add");
		return mv;
	}
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.controller.${objectName}Contorller <BR>
	 * @param request
	 * @param response
	 * @param page
	 * @return: void
	 * @Author: ${author} <BR>
	 * @Datetime： ${nowDate} <BR>
	 */
	@Ecclog(value="Demo保存",type="2",key="demo_save")
	@RequestMapping(value="/save${objectName}")
	public void save${objectName}(HttpServletRequest request, HttpServletResponse response,Page page){
		JsonResProcessor js=new JsonResProcessor();
		PageData pd = new PageData();
		pd=this.getPageData();
		boolean bool=${objectNameLower}Service.save${objectName}(pd);
		super.returnnBaseJson(bool, bool==true?"保存成功!":"保存失败!", response);
	}
	
	/**
	 * 
	 * 方法说明： . <BR>
	 * @see com.ecc.${packageName}.${objectNameLower}.controller.${objectName}Contorller <BR>
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @return: ModelAndView
	 * @Author: ${author} <BR>
	 * @Datetime： ${nowDate} <BR>
	 */
	@Ecclog(value="Demo导出",type="2",key="demo_export")
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel${objectName}(HttpServletRequest request, HttpServletResponse response,Page page){
		ModelAndView mv=this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("A");	//1
			titles.add("B");	//2
			titles.add("C");	//3
			titles.add("D");	//4
			titles.add("E");	//5
			titles.add("F");	//6
			dataMap.put("titles", titles);
			List varOList = ${objectNameLower}Service.listAll${objectName}(pd);
			List varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", ((HashMap)varOList.get(i)).get("TITLE"));	//1
				vpd.put("var2", ((HashMap)varOList.get(i)).get("NAME"));	//2
				vpd.put("var3", ((HashMap)varOList.get(i)).get("PATH"));	//3
				vpd.put("var4", ((HashMap)varOList.get(i)).get("CREATETIME"));	//4
				vpd.put("var5", ((HashMap)varOList.get(i)).get("MASTER_ID"));	//5
				vpd.put("var6", ((HashMap)varOList.get(i)).get("BZ"));	        //6
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}

}
