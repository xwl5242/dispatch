package com.dispatch.runningCon.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.runningCon.bean.RepairRecord;
import com.dispatch.runningCon.dao.RunRecordDao;
import com.dispatch.runningCon.service.RunRecordService;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class RunRecordServiceImpl implements RunRecordService {

	@Autowired
	private RunRecordDao rrDao;

	/**
	 * 查询运行日志list
	 */
	@Override
	public Map<String, Object> queryRunRecord(int currentPage, int pageSize,
			String sTime,String eTime,String dName,String kName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		eTime=null!=eTime&&!"".equals(eTime)?eTime:sdf.format(new Date());
		Calendar a = Calendar.getInstance();
		try {
			a.setTime(sdf.parse(eTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		a.add(Calendar.DATE, -6);
		sTime=null!=sTime&&!"".equals(sTime)?sTime:sdf.format(a.getTime());
		return rrDao.queryRunRecord(currentPage,pageSize,sTime,eTime,dName,kName);
	}
	
	/**
	 * 查询日志分页信息
	 */
	@Override
	public Map<String, Object> queryLogList(int currentPage, int pageSize,
			Map<String, String> param) {
		String startEventTime = param.get("startEventTime");
		String endEventTime = param.get("endEventTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		endEventTime=null!=endEventTime&&!"".equals(endEventTime)?endEventTime:sdf.format(new Date());
		Calendar a = Calendar.getInstance();
		try {
			a.setTime(sdf.parse(endEventTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		a.add(Calendar.DATE, -6);
		startEventTime=null!=startEventTime&&!"".equals(startEventTime)?startEventTime:sdf.format(a.getTime());
		param.put("startEventTime", startEventTime+" 00:00:00");
		param.put("endEventTime", endEventTime+" 23:59:59");
		
		return rrDao.queryLogList(currentPage,pageSize,param);
	}

	@Override
	public Map<String, Object> weatherJson(int currentPage, int pageSize,
			String param) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> listMap = rrDao.weatherJson(param);
		if(null!=listMap&&listMap.size()>0){
			List<Map<String,String>> dl = new ArrayList<Map<String,String>>();
			for(int i=0;i<=23;i++){
				boolean flag = false;
				Map<String,String> tm = new HashMap<String,String>();
				for(Map<String,Object> m:listMap){
					String istr = i<10?("0"+i):(i+"");
					String hh = m.get("HH")+"";
					if(hh.equals(istr)){
						flag = true;
						tm.put("hh", i+"时");
						tm.put("fs", m.get("FS")+"");
						tm.put("fx", m.get("FX")+"");
						tm.put("sd", m.get("SD")+"");
						tm.put("wd", m.get("WD")+"");
						tm.put("pm", m.get("PM")+"");
					}
				}
				if(!flag){
					tm.put("hh", i+"时");
					tm.put("fs", "0");
					tm.put("fx", "0");
					tm.put("sd", "0");
					tm.put("wd", "0");
					tm.put("pm", "0");
					dl.add(tm);
				}
				dl.add(tm);
			}
			result.put("rows", dl);
		}else{
			List<Map<String,String>> dl = new ArrayList<Map<String,String>>();
			for(int i=0;i<=23;i++){
				Map<String,String> tm = new HashMap<String,String>();
				tm.put("hh", i+"时");
				tm.put("fs", "0");
				tm.put("fx", "0");
				tm.put("sd", "0");
				tm.put("wd", "0");
				tm.put("pm", "0");
				dl.add(tm);
			}
			result.put("rows", dl);
		}
		result.put("total", 24);
		return result;
	}

	@Override
	public Map<String, Object> avgTemp(String curDate) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> listMap = rrDao.weatherJson(curDate);
		List<String> dl = new ArrayList<String>();
		if(null!=listMap&&listMap.size()>0){
			for(int i=0;i<=23;i++){
				boolean flag = false;
				for(Map<String,Object> m:listMap){
					String istr = i<10?("0"+i):(i+"");
					String hh = m.get("HH")+"";
					if(hh.equals(istr)){
						flag = true;
						dl.add(m.get("WD")+"");
					}
				}
				if(!flag){
					dl.add("0");
				}
			}
		}else{
			for(int i=0;i<=23;i++){
				dl.add("0");
			}
		}
		result.put("y", dl);
		return result;
	}

	@Override
	public Map<String, Object> tempTrend(String startDate, String endDate) throws Exception {
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
		List<Map<String,Object>> listMap = rrDao.tempTrend(startDate,endDate);
		if(null!=listMap&&listMap.size()>0){
			List<String> MAX = new ArrayList<String>();
			List<String> MIN = new ArrayList<String>();
			List<String> AVG = new ArrayList<String>();
			for(String y:yearList){
				boolean flag = false;
				for(Map<String,Object> map:listMap){
					if(map.get("YD").toString().equals(y)){
						flag = true;
						MAX.add(map.get("MAXT")+"");
						MIN.add(map.get("MINT")+"");
						AVG.add(map.get("AVGT")+"");
					}
				}
				if(!flag){
					MAX.add("0");
					MIN.add("0");
					AVG.add("0");
				}
			}
			result.put("max", MAX);
			result.put("min", MIN);
			result.put("avg", AVG);
		}else{
			result.put("max", defaultList);
			result.put("min", defaultList);
			result.put("avg", defaultList);
		}
		result.put("yearList", yearList);
		return result;
	}

	@Override
	public Map<String, Object> indexParams() {
		return rrDao.indexParams();
	}

	@Override
	public Map<String, Object> repairRecords(int currentPage, int pageSize,
			String sTime, String eTime, String dName) {
		return rrDao.queryRRs(currentPage,pageSize,sTime,eTime,dName);
	}

	@Override
	public Map<String, Object> addRR(RepairRecord rr) {
		return rrDao.insertRR(rr);
	}

	@Override
	public Map<String, Object> editRR(RepairRecord rr) {
		return rrDao.updateRR(rr);
	}

	@Override
	public Map<String, Object> removeRR(String ids) {
		StringBuilder in = new StringBuilder();
		if(ids.contains(",")){
			String[] idArrays = ids.split(",");
			for(String idArray:idArrays){
				in.append("'").append(idArray).append("',");
			}
		}else{
			in.append(ids+",");
		}
		return rrDao.deleteRR(in.substring(0, in.length()-1));
	}
}
