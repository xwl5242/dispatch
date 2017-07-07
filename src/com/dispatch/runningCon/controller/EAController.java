package com.dispatch.runningCon.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dispatch.runningCon.service.EAService;
import com.frames.base.BaseController;

@Controller
@RequestMapping("ea")
public class EAController extends BaseController {

	@Autowired
	private EAService eaService;
	
	@RequestMapping(value = "/airsystem/air/tongbi.do")
	@ResponseBody
	public void airTongbi(HttpServletRequest request, HttpServletResponse response,String ys,String type){
		Map<String,Object> map =new HashMap<String,Object>();
		map = eaService.tongbi(ys,type);
		super.returnObjectJson(map, response);
	}
	
	@RequestMapping(value = "/airsystem/air/qushi.do")
	@ResponseBody
	public void airLine(HttpServletRequest request, HttpServletResponse response,
			String startDate,String endDate,String type) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		map = eaService.qushi(startDate,endDate,type);
		super.returnObjectJson(map, response);
	}
	
	@RequestMapping(value = "/airsystem/air/biaozhun.do")
	@ResponseBody
	public void airTongbi1(HttpServletRequest request, HttpServletResponse response,String startDate,String endDate){
		Map<String,Object> map =new HashMap<String,Object>();
		map = eaService.airTongbi1(startDate,endDate);
		super.returnObjectJson(map, response);
	}
	
}
