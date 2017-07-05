package com.dispatch.sys.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dispatch.sys.datadic.dao.DataDicDao;
@Service
public class GetDataDicCache {
	@Resource 
	private DataDicDao dataDicDao;
	public static Map<String,List<Map<String, Object>>> dataDicMap = new HashMap<String, List<Map<String, Object>>>();
	public static Map<String,List<Map<String, Object>>> dataDicMapSelected = new HashMap<String, List<Map<String, Object>>>();
	public static List columnList=new ArrayList();
	public static HashMap columnMap=new HashMap();
	public void getDataDic(){
		System.out.println("----------开始加载数据字典---------");
		List<Map<String, Object>> typeCodeList=dataDicDao.getAllTypeCode();
		for(int i=0;i<typeCodeList.size();i++){
			String typecode=typeCodeList.get(i).get("TYPECODE").toString();
			List<Map<String, Object>> datadicReal=dataDicDao.getDataDicByTypeCode(typecode);
			List<Map<String, Object>> datadicChecked=new ArrayList<Map<String, Object>>(); 
			List<Map<String, Object>> datadic=new ArrayList<Map<String, Object>>(); 
			for(int k=0;k<datadicReal.size();k++){
				HashMap map=(HashMap)datadicReal.get(k);
				HashMap mapCheck=new HashMap();
				mapCheck.put("ID", map.get("ID"));
				mapCheck.put("TEXT", map.get("TEXT"));
				datadic.add(mapCheck);
				if("true".equals(map.get("SELECTED")+"")){
					map.put("selected", true);
				}
				datadicChecked.add(map);
			}
			dataDicMapSelected.put(typecode, datadicChecked);
			dataDicMap.put(typecode, datadic);
		}
		System.out.println("----------完成加载数据字典---------");
	}
}
