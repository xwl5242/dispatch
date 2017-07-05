/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  HeatStation.java     <BR>
 * Author: fupenglin  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2014-12-23 下午06:57:08 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

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

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.Ecclog;
import com.dispatch.unit.bean.UnitInfo;
import com.dispatch.unit.unitInfo.service.DetailsService;
import com.dispatch.unit.unitInfo.service.HeatStationService;
import com.util.EnumUtil;
import com.util.common.JsonResProcessor;

/**
 * 
 * 功能描述：热力站功能信息  .  <BR>
 * 历史版本: <Br>
 * 开发者: fupenglin  <BR>
 * 时间：2015-1-16 下午04:08:35  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：2015-1-16 下午04:08:35 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
@SuppressWarnings("static-access")
@Controller
public class HeatStationController {
	@Autowired
	private HeatStationService heatStationService; 
	@Autowired
	private DetailsService detailsService;
	
	/**
	 * 方法说明：修改热力站和支路信息 . <BR>
	 */
	@Ecclog(value="修改热力站和支路信息",type="1",key="ieu_unitInfo")
	@RequestMapping("/heatstation/updateHeatStation.do")
	public void updateHeatStation(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id,//id
			@RequestParam(defaultValue = "", value = "detailsId") String detailsId,
			@RequestParam (defaultValue="",value="onwerType") String onwerType ,
			@RequestParam (defaultValue="",value="unitId") String unitId ,
			@RequestParam (defaultValue="",value="unitCode") String unitCode ,//父id
			@RequestParam (defaultValue="",value="unitName") String unitName ,
			@RequestParam (defaultValue="",value="shortName") String shortName ,
			@RequestParam (defaultValue="",value="jianPin") String jianPin ,
			@RequestParam (defaultValue="",value="heatingType") String heatingType ,
			@RequestParam (defaultValue="",value="manageType") String manageType ,
			@RequestParam (defaultValue="",value="mjHeatarea") String mjHeatarea,
			@RequestParam (defaultValue="",value="gjheatarea") String gjheatarea,
			@RequestParam (defaultValue="",value="mjHeatareaH") String mjHeatareaH,
			@RequestParam (defaultValue="",value="gjheatareaH") String gjheatareaH,
			@RequestParam (defaultValue="",value="allheatarea") String allheatarea,
			@RequestParam (defaultValue="",value="gjHeatNumH") String gjHeatNumH ,
			@RequestParam (defaultValue="",value="gjHeatNum") String gjHeatNum ,
			@RequestParam (defaultValue="",value="heatnumH") String heatnumH ,
			@RequestParam (defaultValue="",value="heatnum") String heatnum ,
			@RequestParam (defaultValue="",value="longitude") String longitude ,
			@RequestParam (defaultValue="",value="latitude") String latitude ,
			@RequestParam (defaultValue="",value="seq") String seq,
			@RequestParam (defaultValue="",value="remarks") String remarks,
			@RequestParam (defaultValue="",value="zlObjs") String zlObjs
			) throws Exception {  
		
		User u2=(User)request.getSession().getAttribute("ECCUSER");
		Org o2=(Org) request.getSession().getAttribute("ECCORG");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		UnitInfo unitInfo = new UnitInfo(); 
		unitInfo.setId(id);
		unitInfo.setUnitName(unitName);
		unitInfo.setUnitCode(unitCode);
		unitInfo.setShortName(shortName);
		unitInfo.setJianPin(jianPin);
		unitInfo.setOnwerType(onwerType);
		if(unitId.isEmpty()){
			unitInfo.setUnitId("0");
		}else{
			unitInfo.setUnitId(unitId);
		}
		unitInfo.setDetailsId(detailsId);
		unitInfo.setMjHeatarea(mjHeatarea);
		unitInfo.setHeatingType(heatingType);
		unitInfo.setManageType(manageType);
		unitInfo.setGjheatarea(gjheatarea);
		unitInfo.setMjHeatareaH(mjHeatareaH);
		unitInfo.setGjheatareaH(gjheatareaH);
		unitInfo.setGjHeatNum(gjHeatNum);
		unitInfo.setGjHeatNumH(gjHeatNumH);
		unitInfo.setHeatnumH(heatnumH);
		unitInfo.setHeatnum(heatnum);
		unitInfo.setLongitude(longitude);
		unitInfo.setLatitude(latitude);
		unitInfo.setSeq(seq);
		unitInfo.setRemarks(remarks);
		unitInfo.setUnitType(EnumUtil.OWNERTYPE4);
		unitInfo.setUpdateUserID(u2.getId());
		unitInfo.setUpdateUser(u2.getUserName());
		unitInfo.setUpdateUserDepartmentID(o2.getId());
		unitInfo.setUpdateUserDepartment(o2.getOrgName());
		unitInfo.setUpdateDate(df.format(new Date()));
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(!"[]".equals(zlObjs)){
			String objs = zlObjs.substring(1, zlObjs.length()-1).replace("},{", "};{");
			String[] objStr = objs.split(";");
			
			for(int i=0;i<objStr.length;i++){
				
				JSONObject jsonObject = JSONObject.fromObject(objStr[i]);
				list.add(jsonObject);
			}
		}		
		
		JsonResProcessor js = new JsonResProcessor();
		Map map = new HashMap();
		unitInfo.setLongitude(longitude);
		unitInfo.setLatitude(latitude);
		try {
			int i = heatStationService.updateHeatStation(unitInfo,list);
			map.put("messager", "修改成功!");
			map.put("flag", true);
		} catch (Exception ex) {
				
			ex.printStackTrace();
		}
		try {
			js.returnResInfo(map, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法说明：通过id查询所属单位信息 . <BR>
	 */
	@Ecclog(value="通过id查询所属单位信息",type="2",key="ecc_ieu_unitinfo")
	@RequestMapping("/heatstation/findUnitInfoById.do")
	public void findUnitInfoById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="id") String id 
			) throws Exception{ 
		UnitInfo unitInfo = heatStationService.findUnitInfoById(id);
		JsonResProcessor js=new JsonResProcessor();
		js.returnResInfo(unitInfo, response);
	}
}
