package com.dispatch.runningCon.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public void queryRunRecord(HttpServletRequest request,HttpServletResponse response,Page page,String nodeId){
		getPageInfo(request);
		Map<String,Object> result = rrService.queryRunRecord(super.getCurrentPage(),super.getPageSize(),nodeId);
		super.returnObjectJson(result, response);
	}
	
	@RequestMapping("/queryLogList")
	@ResponseBody
	public void queryLogList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String,String> param){
		getPageInfo(request);
		Map<String,Object> result = rrService.queryLogList(super.getCurrentPage(),super.getPageSize(),param);
		super.returnObjectJson(result, response);
	}
}
