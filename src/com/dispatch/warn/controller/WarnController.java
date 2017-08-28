package com.dispatch.warn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dispatch.warn.service.WarnService;
import com.frames.base.BaseController;

@Controller
@RequestMapping("/warning")
public class WarnController extends BaseController{

	@Autowired
	private WarnService warnService;
	
	@RequestMapping(value = "/listWarnData.do")
	@ResponseBody
	public void listWarnData(HttpServletRequest request, HttpServletResponse response,
			String sTime,String eTime,String pointName,String status){
		Map<String,Object> map =new HashMap<String,Object>();
		getPageInfo(request);
		map = warnService.listWarnData(sTime,eTime,pointName,status,super.getCurrentPage(),super.getPageSize());
		super.returnObjectJson(map, response);
	}
	
	@RequestMapping(value = "/warnCount.do")
	@ResponseBody
	public void warnCount(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map =new HashMap<String,Object>();
		map = warnService.warnCount();
		super.returnObjectJson(map, response);
	}
}
