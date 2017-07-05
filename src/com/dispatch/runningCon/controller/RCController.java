package com.dispatch.runningCon.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dispatch.runningCon.service.RCService;
import com.frames.base.BaseController;
import com.frames.util.Page;

@Controller
@RequestMapping("runningCon")
public class RCController extends BaseController {

	@Autowired
	private RCService rcService;
	
	/**
	 * 获取实时数据
	 * @param request
	 * @param response
	 * @param unitId
	 */
	@RequestMapping("/queryRealData")
	@ResponseBody
	public void queryRealDataByNodeId(HttpServletRequest request,HttpServletResponse response,Page page,String nodeId){
		getPageInfo(request);
		Map<String,Object> result = rcService.queryRealDataByNodeId(super.getCurrentPage(),super.getPageSize(),nodeId);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 获取历史数据
	 * @param request
	 * @param response
	 * @param unitId
	 */
	@RequestMapping("/queryHistoryData")
	@ResponseBody
	public void queryHistoryDataByNodeId(HttpServletRequest request,HttpServletResponse response,Page page,
			@RequestParam(defaultValue = "", value = "collName") String collName,
			@RequestParam(defaultValue = "", value = "nodeId") String nodeId,
			@RequestParam(defaultValue = "", value = "startdate") String startdate,
			@RequestParam(defaultValue = "", value = "enddate") String enddate,
			@RequestParam(defaultValue = "", value = "minvalue") String minvalue,
			@RequestParam(defaultValue = "", value = "maxvalue") String maxvalue,
			@RequestParam(defaultValue = "", value = "eqvalue") String eqvalue){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			getPageInfo(request);
			if(null==startdate || "".equals(startdate)){
				startdate = sdf.format(new Date())+" 00:00:00";
			}
			if(null==enddate || "".equals(enddate)){
				enddate = sdf.format(new Date())+" 23:59:59";
			}
			Map<String,Object>	tableList =rcService.queryHistoryDataByNodeId(collName,nodeId,startdate,enddate,minvalue,maxvalue,eqvalue,super.getCurrentPage(),super.getPageSize());
			super.returnObjectJson(tableList, response);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 获取实时或历史数据曲线
	 * @param request
	 * @param response
	 * @param unitId
	 */
	@RequestMapping("/queryRealDataLine")
	@ResponseBody
	public void queryRealDataLine(HttpServletRequest request,HttpServletResponse response,String nodeId,
			String startTime,String endTime,boolean isHis){
		Map<String,Object> result = rcService.queryRealDataLine(nodeId,startTime,endTime,isHis);
		super.returnObjectJson(result, response);
	}
	
	/**
	 * 根据换热站ID查询采集点信息
	 * @param request
	 * @param response
	 * @param unitid
	 */
	@RequestMapping(value = "/queryCollByNodeId.do")
	@ResponseBody
	public void queryCollByNodeId(HttpServletRequest request, HttpServletResponse response, String nodeId){
		List<Map<String,Object>> map =new ArrayList<Map<String,Object>>();
		map = rcService.queryCollByNodeId(nodeId);
		super.returnObjectJson(map, response);
	}
	
	@RequestMapping(value = "/runningCon.do")
	@ResponseBody
	public void runningCon(HttpServletRequest request, HttpServletResponse response, String nodeId){
		Map<String,Object> map =new HashMap<String,Object>();
		map = rcService.runningCon(nodeId);
		super.returnObjectJson(map, response);
	}
	
	
}
