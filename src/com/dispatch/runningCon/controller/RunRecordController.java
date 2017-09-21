package com.dispatch.runningCon.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dispatch.runningCon.bean.RepairRecord;
import com.dispatch.runningCon.service.RunRecordService;
import com.frames.base.BaseController;
import com.frames.util.Page;

@Controller
@RequestMapping("/runRecord")
public class RunRecordController extends BaseController {

	@Autowired
	private RunRecordService rrService;
	
	/**
	 * 查询运行日志list
	 * @param request
	 * @param response
	 * @param page
	 * @param nodeId
	 */
	@RequestMapping("/query.do")
	@ResponseBody
	public void queryRunRecord(HttpServletRequest request,HttpServletResponse response,Page page,String sTime,String eTime,String dName,String kName){
		getPageInfo(request);
		Map<String,Object> result = rrService.queryRunRecord(super.getCurrentPage(),super.getPageSize(),sTime,eTime,dName,kName);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 查询日志分页信息
	 * @param request
	 * @param response
	 * @param param
	 */
	@RequestMapping("/queryLogList")
	@ResponseBody
	public void queryLogList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String,String> param){
		getPageInfo(request);
		Map<String,Object> result = rrService.queryLogList(super.getCurrentPage(),super.getPageSize(),param);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 天气参数table数据
	 * @param request
	 * @param response
	 * @param curDate
	 */
	@RequestMapping("/weatherJson")
	@ResponseBody
	public void weatherJson(HttpServletRequest request,HttpServletResponse response, String curDate){
		getPageInfo(request);
		if(null==curDate||"".equals(curDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			curDate = sdf.format(new Date());
		}
		Map<String,Object> result = rrService.weatherJson(super.getCurrentPage(),super.getPageSize(),curDate);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 平均气温曲线
	 * @param request
	 * @param response
	 * @param curDate
	 */
	@RequestMapping("/avgTemp")
	@ResponseBody
	public void avgTemp(HttpServletRequest request,HttpServletResponse response, String curDate){
		if(null==curDate||"".equals(curDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			curDate = sdf.format(new Date());
		}
		Map<String,Object> result = rrService.avgTemp(curDate);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 气温曲线
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	@RequestMapping("/tempTrend")
	@ResponseBody
	public void tempTrend(HttpServletRequest request,HttpServletResponse response, String startDate,
			String endDate) throws Exception{
		Map<String,Object> result = rrService.tempTrend(startDate,endDate);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 首页top上 气象参数信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/indexParams")
	@ResponseBody
	public void indexParams(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> result = rrService.indexParams();
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 维修记录分页信息
	 * @param request
	 * @param response
	 * @param sTime
	 * @param eTime
	 * @param dName
	 */
	@RequestMapping("/repairRecords.do")
	@ResponseBody
	public void repairRecords(HttpServletRequest request,HttpServletResponse response,String sTime,String eTime,String dName){
		getPageInfo(request);
		Map<String,Object> result = rrService.repairRecords(super.getCurrentPage(),super.getPageSize(),sTime,eTime,dName);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 新增维修记录
	 * @param request
	 * @param response
	 * @param rr
	 */
	@RequestMapping("/addRR.do")
	@ResponseBody
	public void addRR(HttpServletRequest request,HttpServletResponse response,RepairRecord rr){
		Map<String,Object> result = rrService.addRR(rr);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 修改维修记录
	 * @param request
	 * @param response
	 * @param rr
	 */
	@RequestMapping("/editRR.do")
	@ResponseBody
	public void editRR(HttpServletRequest request,HttpServletResponse response,RepairRecord rr){
		Map<String,Object> result = rrService.editRR(rr);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 删除维修记录
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/removeRR.do")
	@ResponseBody
	public void removeRR(HttpServletRequest request,HttpServletResponse response,String ids){
		Map<String,Object> result = rrService.removeRR(ids);
		super.returnObjectJson(result, response);
	}
}
