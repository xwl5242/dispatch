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
				String ata = "0",atb="0";
				boolean aflag = false;
				boolean bflag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("Y").toString().equals(y)){
						String AB = map.get("AB").toString();
						String DL = map.get("DL").toString();
						String AVGDL = map.get("DL").toString();
						if("DH".equals(eaType)){
							DL = String.format("%.2f", Double.parseDouble(DL)/getABArea("AB"));
						}
						if(AB.equals("A")){
							aflag = true;
							A.add(DL);ta = DL;ata=AVGDL;
						}else if(AB.equals("B")){
							bflag = true;
							B.add(DL);tb = DL;atb=AVGDL;
						}
					}
				}
				if(!aflag){
					A.add("0");
				}
				if(!bflag){
					B.add("0");
				}
				if("DH".equals(eaType)){
					T.add(String.format("%.2f", (Double.valueOf(ata)+Double.valueOf(atb)/(getABArea("AB")*2))));
				}else{
					T.add(String.format("%.2f", (Double.valueOf(ta)+Double.valueOf(tb))));
				}
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
				boolean aflag = false;
				boolean bflag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("AITYPE").toString().equals(type)){
						String AB = map.get("AB").toString();
						String DL = map.get("DL").toString();
						if(AB.equals("A")){
							aflag = true;
							A.add(DL);
						}else if(AB.equals("B")){
							bflag = true;
							B.add(DL);
						}
					}
				}
				if(!aflag){
					A.add("0");
				}
				if(!bflag){
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
				String ata = "0",atb="0";
				boolean aflag = false;
				boolean bflag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("YD").toString().equals(y)){
						String AB = map.get("AB").toString();
						String DL = map.get("DL").toString();
						String AVGDL = map.get("DL").toString();
						if("DH".equals(eaType)){
							DL = String.format("%.2f", Double.parseDouble(DL)/getABArea("AB"));
						}
						if(AB.equals("A")){
							aflag = true;
							A.add(DL);ta = DL;ata=AVGDL;
						}else if(AB.equals("B")){
							bflag = true;
							B.add(DL);tb = DL;atb=AVGDL;
						}
					}
				}
				if(!aflag){
					A.add("0");
				}
				if(!bflag){
					B.add("0");
				}
				if("DH".equals(eaType)){
					T.add(String.format("%.2f", (Double.valueOf(ata)+Double.valueOf(atb)/(getABArea("AB")*2))));
				}else{
					T.add(String.format("%.2f", (Double.valueOf(ta)+Double.valueOf(tb))));
				}
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
			list.add(tm);
		}
		return list;
	}

	private double getABArea(String type){
		List<Map<String,Object>> abArea = eaDao.getArea(type);
		if(abArea!=null&&abArea.size()>0){
			Double area = Double.parseDouble(abArea.get(0).get("AREA").toString());
			return area;
		}
		return 0;
	}

	@Override
	public Map<String, Object> copLine(String startDate, String endDate)throws Exception {
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
			for(int h=0;h<24;h++){
				String hStr = sd + " " +(h<=9?"0"+h:h+"");
				yearList.add(hStr);
			}
			defaultList.add("0");
			sd = sdf.format(sdf.parse(sd).getTime()+24*60*60*1000);
		}
		for(int h=0;h<24;h++){
			String hStr = endDate + " " +(h<=9?"0"+h:h+"");
			yearList.add(hStr);
		}
		defaultList.add("0");
		
		List<Map<String, Object>> listMap = eaDao.copLine(startDate,endDate);
		if(null!=listMap&&listMap.size()>0){
			List<String> A1 = new ArrayList<String>();
			List<String> A2 = new ArrayList<String>();
			List<String> A3 = new ArrayList<String>();
			List<String> B1 = new ArrayList<String>();
			List<String> B2 = new ArrayList<String>();
			List<String> B3 = new ArrayList<String>();
			for(String y:yearList){
				for(Map<String,Object> map:listMap){
					if(map.get("HH").toString().equals(y)){
						String a1 = map.get("A1").toString();
						String a2 = map.get("A2").toString();
						String a3 = map.get("A3").toString();
						String b1 = map.get("B1").toString();
						String b2 = map.get("B2").toString();
						String b3 = map.get("B3").toString();
						A1.add(a1);A2.add(a2);A3.add(a3);
						B1.add(b1);B2.add(b2);B3.add(b3);
					}
				}
			}
			result.put("A1", A1);
			result.put("A2", A2);
			result.put("A3", A3);
			result.put("B1", B1);
			result.put("B2", B2);
			result.put("B3", B3);
		}else{
			result.put("A1", defaultList);
			result.put("A2", defaultList);
			result.put("A3", defaultList);
			result.put("B1", defaultList);
			result.put("B2", defaultList);
			result.put("B3", defaultList);
		}
		result.put("yearList", yearList);
		return result;
	}

	@Override
	public Map<String, Object> drsh(String ys,String startDate,String endDate)throws Exception {
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		endDate=null!=endDate&&!"".equals(endDate)?endDate:sdf.format(new Date());
		Calendar a = Calendar.getInstance();
		a.setTime(sdf.parse(endDate));
		a.add(Calendar.DATE, -6);
		startDate=null!=startDate&&!"".equals(startDate)?startDate:sdf.format(a.getTime());
		
		List<Map<String, Object>> listMap = eaDao.drsh(startDate,endDate);
		if(null!=listMap&&listMap.size()>0){
			List<String> A = new ArrayList<String>();
			for(String y:yearList){
				boolean flag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("Y").toString().equals(y)){
						flag = true;
						String value = map.get("DRSH").toString();
						A.add(value);
					}
				}
				if(!flag){
					A.add("0");
				}
			}
			result.put("drsh", A);
		}
		result.put("yearList", yearList);
		return result;
	}
}
