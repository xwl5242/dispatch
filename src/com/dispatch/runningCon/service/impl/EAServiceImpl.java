package com.dispatch.runningCon.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dispatch.runningCon.dao.EADao;
import com.dispatch.runningCon.service.EAService;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class EAServiceImpl implements EAService{
	
	@Resource
	private EADao eaDao;

	@Override
	public Map<String, Object> tongbi(String ys,String type,String eaType) {
		Map<String,Object> result =new HashMap<String,Object>();
		List<String> yearList = new ArrayList<String>();
		if(null==ys || "".equals(ys)){
			Calendar a = Calendar.getInstance();
			int year = a.get(Calendar.YEAR);
			yearList.add(year-2+"");
			yearList.add(year-1+"");
			yearList.add(year+"");
		}else{
			yearList = Arrays.asList(ys.split("\\|"));
		}
		List<Map<String, Object>> listMap = eaDao.tongbi(type,eaType);
		
		if(null!=listMap&&listMap.size()>0){
			List<String> A = new ArrayList<String>();
			List<String> B = new ArrayList<String>();
			List<String> T = new ArrayList<String>();
			for(String y:yearList){
				String ta = "0",tb="0";
				boolean flag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("Y").toString().equals(y)){
						flag = true;
						String AB = map.get("AB").toString();
						String DL = map.get("DL").toString();
						if(AB.equals("A")){
							A.add(DL);ta = DL;
						}else if(AB.equals("B")){
							B.add(DL);tb = DL;
						}
					}
				}
				if(!flag){
					A.add("0");
					B.add("0");
				}
				T.add(String.format("%.2f", (Double.valueOf(ta)+Double.valueOf(tb))));
			}
			result.put("A", A);
			result.put("B", B);
			result.put("T", T);
		}else{
			result.put("A", new String[]{"0","0","0"});
			result.put("B", new String[]{"0","0","0"});
			result.put("T", new String[]{"0","0","0"});
		}
		result.put("yearList", yearList);
		return result;
	}

	@Override
	public Map<String, Object> airTongbi1(String startDate, String endDate,String utype) {
		Map<String,Object> result =new HashMap<String,Object>();
		List<Map<String, Object>> listMap = eaDao.airTongbi1(startDate,endDate,utype);
		String[] aitype = {"1","2"};
		if(null!=listMap&&listMap.size()>0){
			List<String> A = new ArrayList<String>();
			List<String> B = new ArrayList<String>();
			for(String type:aitype){
				boolean flag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("AITYPE").toString().equals(type)){
						flag = true;
						String AB = map.get("AB").toString();
						String DL = map.get("DL").toString();
						if(AB.equals("A")){
							A.add(DL);
						}else if(AB.equals("B")){
							B.add(DL);
						}
					}
				}
				if(!flag){
					A.add("0");
					B.add("0");
				}
			}
			result.put("A", A);
			result.put("B", B);
		}else{
			result.put("A", new String[]{"0","0"});
			result.put("B", new String[]{"0","0"});
		}
		return result;
	}

	@Override
	public Map<String, Object> qushi(String startDate, String endDate,String type,String eaType) throws Exception {
		Map<String,Object> result =new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> yearList = new ArrayList<String>();
		List<String> defaultList = new ArrayList<String>();
		
		endDate=null!=endDate&&!"".equals(endDate)?endDate:sdf.format(new Date());
		Calendar a = Calendar.getInstance();
		a.setTime(sdf.parse(endDate));
		a.add(Calendar.DATE, -6);
		startDate=null!=startDate&&!"".equals(startDate)?startDate:sdf.format(a.getTime());
		
		String sd = startDate;
		while(!sd.equals(endDate)){
			yearList.add(sd);
			defaultList.add("0");
			sd = sdf.format(sdf.parse(sd).getTime()+24*60*60*1000);
		}
		yearList.add(endDate);
		defaultList.add("0");
		
		List<Map<String, Object>> listMap = eaDao.qushi(startDate,endDate,type,eaType);
		
		if(null!=listMap&&listMap.size()>0){
			List<String> A = new ArrayList<String>();
			List<String> B = new ArrayList<String>();
			List<String> T = new ArrayList<String>();
			for(String y:yearList){
				String ta = "0",tb="0";
				boolean flag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("YD").toString().equals(y)){
						flag = true;
						String AB = map.get("AB").toString();
						String DL = map.get("DL").toString();
						if(AB.equals("A")){
							A.add(DL);ta = DL;
						}else if(AB.equals("B")){
							B.add(DL);tb = DL;
						}
					}
				}
				if(!flag){
					A.add("0");
					B.add("0");
				}
				T.add(String.format("%.2f", (Double.valueOf(ta)+Double.valueOf(tb))));
			}
			result.put("A", A);
			result.put("B", B);
			result.put("T", T);
		}else{
			result.put("A", defaultList);
			result.put("B", defaultList);
			result.put("T", defaultList);
		}
		result.put("yearList", yearList);
		return result;
	}

	@Override
	public List<Map<String,String>> pricePie(String startDate, String endDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		endDate=null!=endDate&&!"".equals(endDate)?endDate:sdf.format(new Date());
		Calendar a = Calendar.getInstance();
		a.setTime(sdf.parse(endDate));
		a.add(Calendar.DATE, -6);
		startDate=null!=startDate&&!"".equals(startDate)?startDate:sdf.format(a.getTime());
		
		List<Map<String,Object>> epList = eaDao.getEnergyPrice();
		List<Map<String,Object>> listMap = eaDao.pricePie(startDate,endDate,epList);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(Map<String,Object> map:listMap){
			Map<String,String> tm = new HashMap<String,String>();
			tm.put("name", map.get("NAME")+""); 
			tm.put("value", map.get("VALUE")+""); 
		}
		return list;
	}

}
