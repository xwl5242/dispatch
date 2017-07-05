package com.dispatch.sys.datadic.controller;

import java.util.ArrayList;
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

import com.dispatch.sys.bean.DataDic;
import com.dispatch.sys.cache.GetDataDicCache;
import com.dispatch.sys.datadic.service.DataDicService;
import com.dispatch.sys.log.Ecclog;
import com.frames.base.BaseController;

@Controller
public class DataDicController extends BaseController{

	@Autowired
	private DataDicService dataDicService;
	
	/**
	 * 方法说明：获取数据字典树 . <BR>
	 */
	@Ecclog(value="获取数据字典树",type="2",key="sys_datadic") 
	@RequestMapping("/datadic/AllDataDicTreeData.do")
	public void AllDataDicTreeData(HttpServletRequest request, 
			@RequestParam (defaultValue="0",value="id") String id,HttpServletResponse response){
		
		 List<Map<String, Object>> map = dataDicService.AllDataDicTreeData(id);
		 super.returnListJson(map, response);
	}
	/**
	 * 方法说明：数据字典列表查询 . <BR>
	 */
	@Ecclog(value="数据字典列表查询",type="2",key="sys_datadic") 
	@RequestMapping("/datadic/getdatadicList.do")
	public void getdatadicList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "parid") String parid,
			@RequestParam(defaultValue = "", value = "dicname") String dicname,
			@RequestParam(defaultValue = "", value = "typecode") String typecode)
			throws Exception {
		getPageInfo(request);
		DataDic dic = new DataDic();
		dic.setParid(parid);
		dic.setTypecode(typecode);
		dic.setDicname(dicname);
		Map map = dataDicService.getdatadicList(super.getPageSize(),super.getCurrentPage(),dic);
		super.returnObjectJson(map, response);
	}
	
	/**
	 * 方法说明：保存数据字典信息 . <BR>
	 */
	@Ecclog(value="保存数据字典信息",type="1",key="sys_datadic") 
	@RequestMapping("/datadic/saveDatadic.do")
	public void saveDatadic(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "json") String json
			) throws Exception{
		JSONObject jsonObj=JSONObject.fromObject(json);
		DataDic dataDic=(DataDic) JSONObject.toBean(jsonObj,DataDic.class); 
		String mes = "";
		boolean flag = false;
		flag = dataDicService.saveDatadic(dataDic);
		mes = flag?"保存成功!":"保存失败!";
		//把修改后的数据实时更新
		List list=dataDicService.getDataDicListByTypeCode(dataDic.getTypecode());
		changeIsSelected(dataDic.getTypecode(),list);
		super.returnnBaseJson(flag, mes, response);
	}
	
	/**
	 * 方法说明：修改默认选中项 . <BR>
	 */
	public void changeIsSelected(String typecode,List list){
		List<Map<String, Object>> datadicReal=list;
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
		GetDataDicCache.dataDicMapSelected.put(typecode, datadicChecked);
		GetDataDicCache.dataDicMap.put(typecode, datadic);
	}
	
	/**
	 * 方法说明：检查默认选项 . <BR>
	 */
	@Ecclog(value="检查默认选项",type="1",key="sys_datadic") 
	@RequestMapping("/datadic/checkIsSelected.do")
	public void checkIsSelected(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam (defaultValue="",value="id") String id,
			@RequestParam(defaultValue = "", value = "typecode") String typecode,
			@RequestParam(defaultValue = "", value = "isSelected") String isSelected
			) throws Exception{
		DataDic dataDic=new DataDic();
		dataDic.setTypecode(typecode);
		boolean bool=false;
		String msg="";
		List list=dataDicService.checkIsSelected(dataDic);
		if("true".equals(isSelected)&&list.size()==0){
			bool=true;
			msg="";
		}else if("true".equals(isSelected)&&list.size()==1){
			String checkid=((HashMap)list.get(0)).get("ID")+"";
			bool=checkid.equals(id);
			msg=bool==true?"":"不能出现多个默认选项";
		}else if("false".equals(isSelected)){
			bool=true;
		}
		super.returnnBaseJson(bool, msg, response);
	}
	
	/**
	 * 方法说明：根据id查询字典数据 . <BR>
	 */
	@Ecclog(value="根据id查询字典数据",type="2",key="sys_datadic") 
	@RequestMapping("/datadic/findDataDicById.do")
	public void findDataDicById(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id)
			throws Exception {
		DataDic dataDic = dataDicService.findDataDicById(id);
		super.returnObjectJson(dataDic, response);
	}
	
	/**
	 * 方法说明： 修改字典信息. <BR>
	 */
	@Ecclog(value="修改字典信息",type="1",key="sys_datadic") 
	@RequestMapping("/datadic/updateDic.do")
	public void updateDic(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "json") String json ){
		JSONObject jsonObj=JSONObject.fromObject(json);
		DataDic dataDic=(DataDic) JSONObject.toBean(jsonObj,DataDic.class);
		String mes = "";
		boolean flag = false;
		int i = dataDicService.updateDic(dataDic);
		flag = i>0?true:false;
		mes = i>0?"修改成功!":"修改失败!";
		//把修改后的数据实时更新
		List list=dataDicService.getDataDicListByTypeCode(dataDic.getTypecode());
		changeIsSelected(dataDic.getTypecode(),list);
		super.returnnBaseJson(flag, mes, response);
	}
	
	/**
	 * 方法说明：查询子节点个数 . <BR>
	 */
	@Ecclog(value="查看权限子节点",type="2",key="sys_datadic") 
	@RequestMapping("/datadic/findChildCounts.do")
	public void findChildCounts(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "", value = "id") String id) {
		int count = dataDicService.getChildrenCounts(id);
		if(count>0){
			super.returnnBaseJson(true, "存在子节点,确定要全部删除吗？", response);
		}else{
			super.returnnBaseJson(false, "", response);
		}
	}
	
	/**
	 * 方法说明：删除字典 . <BR>
	 */
	@Ecclog(value="删除字典",type="1",key="sys_datadic") 
	@RequestMapping("/datadic/deleteDic.do")
	public void deleteRight(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (defaultValue="",value="ids") String ids ){
		
		String[]idss=ids.split(",");
		String typecode ="";
		for(String id:idss){
			if(id!=null && !id.equals("")){
				DataDic dataDic=new DataDic();
				dataDic.setId(id);
				DataDic dataDicValue = dataDicService.findDataDicById(id);
				dataDicService.deleteDic(dataDic);
				typecode = dataDicValue.getTypecode();
			}
		}
		//把修改后的数据实时更新
		List list=dataDicService.getDataDicListByTypeCode(typecode);
		GetDataDicCache.dataDicMap.put(typecode,list);
		Map map = new HashMap();
		map.put("messager", "删除成功!"); 
		map.put("flag", true);
		super.returnObjectJson(map, response);
	}
	
	public DataDicService getDataDicService() {
		return dataDicService;
	}

	public void setDataDicService(DataDicService dataDicService) {
		this.dataDicService = dataDicService;
	}
	
}
