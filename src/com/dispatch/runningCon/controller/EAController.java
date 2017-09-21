package com.dispatch.runningCon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dispatch.runningCon.service.EAService;
import com.frames.base.BaseController;

@Controller
@RequestMapping("ea")
public class EAController extends BaseController {

	@Autowired
	private EAService eaService;
	
	/**
	 * 同比柱状图（A座，B座，总体）
	 * @param request
	 * @param response
	 * @param ys
	 * @param type
	 * @param eaType
	 */
	@RequestMapping(value = "/airsystem/air/tongbi.do")
	@ResponseBody
	public void airTongbi(HttpServletRequest request, HttpServletResponse response,
			String ys,String type,String eaType){
		Map<String,Object> map =new HashMap<String,Object>();
		map = eaService.tongbi(ys,type,eaType);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 所有曲线图
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param eaType
	 * @throws Exception
	 */
	@RequestMapping(value = "/airsystem/air/qushi.do")
	@ResponseBody
	public void airLine(HttpServletRequest request, HttpServletResponse response,
			String startDate,String endDate,String type,String eaType) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		map = eaService.qushi(startDate,endDate,type,eaType);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 柱状图
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @param type
	 */
	@RequestMapping(value = "/airsystem/air/biaozhun.do")
	@ResponseBody
	public void biaozhun(HttpServletRequest request, HttpServletResponse response,
			String startDate,String endDate,String type){
		Map<String,Object> map =new HashMap<String,Object>();
		map = eaService.airTongbi1(startDate,endDate,type);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 饼图
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	@RequestMapping(value = "/airsystem/air/pricePie.do")
	@ResponseBody
	public void pricePie(HttpServletRequest request, HttpServletResponse response,
			String startDate,String endDate) throws Exception{
		List<Map<String,String>> map = eaService.pricePie(startDate,endDate);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * cop曲线
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	@RequestMapping(value = "/airsystem/air/copLine.do")
	@ResponseBody
	public void copLines(HttpServletRequest request, HttpServletResponse response,
			String startDate,String endDate) throws Exception{
		Map<String, Object> map = eaService.copLine(startDate,endDate);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 平均cop曲线
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	@RequestMapping(value = "/airsystem/air/copLine4Avg.do")
	@ResponseBody
	public void copLine4Avg(HttpServletRequest request, HttpServletResponse response,
			String startDate,String endDate) throws Exception{
		Map<String, Object> map = eaService.copLine4Avg(startDate,endDate);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 度日数柱状图，同比
	 * @param request
	 * @param response
	 * @param ys
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	@RequestMapping(value = "/airsystem/air/drsh.do")
	@ResponseBody
	public void drsh(HttpServletRequest request, HttpServletResponse response,String ys,
			String startDate,String endDate) throws Exception{
		Map<String, Object> map = eaService.drsh(ys,startDate,endDate);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 度日数曲线
	 * @param request
	 * @param response
	 * @param ys
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	@RequestMapping(value = "/airsystem/air/DRSHtrend.do")
	@ResponseBody
	public void DRSHtrend(HttpServletRequest request, HttpServletResponse response,String ys,
			String startDate,String endDate) throws Exception{
		Map<String, Object> map = eaService.DRSHtrend(ys,startDate,endDate);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 数据更新（分页查询）
	 * @param request
	 * @param response
	 * @param param
	 */
	@RequestMapping("/editKV.do")
	@ResponseBody
	public void editKV(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String,String> param){
		getPageInfo(request);
		Map<String,Object> result = eaService.editKV(super.getCurrentPage(),super.getPageSize(),param);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 数据更新（修改）
	 * @param request
	 * @param response
	 * @param T
	 * @param K
	 * @param V
	 */
	@RequestMapping("/edit.do")
	@ResponseBody
	public void editKV(HttpServletRequest request,HttpServletResponse response,
			String T,String K,String V){
		int result = eaService.edit(T,K,V);
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("flag", result==1);
		super.returnObjectJson(retMap, response);
	}
	
	/**
	 * 数据更新（批量修改）
	 * @param request
	 * @param response
	 * @param pname
	 * @param startTime
	 * @param endTime
	 * @param pvalue
	 */
	@RequestMapping("/batchSavePV.do")
	@ResponseBody
	public void batchSavePV(HttpServletRequest request,HttpServletResponse response,
			String pname,String startTime,String endTime,String pvalue){
		int result = eaService.batchSavePV(pname,startTime,endTime,pvalue);
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("flag", result>=0);
		super.returnObjectJson(retMap, response);
	}
	
	/**
	 * 采集点名称集合
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pnameListJson.do")
	@ResponseBody
	public void pnameListJson(HttpServletRequest request,HttpServletResponse response){
		List<Map<String,Object>> result = eaService.pnameListJson();
		super.returnObjectJson(result, response);
	}
}
