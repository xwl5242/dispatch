package com.dispatch.sys.datadic.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.DataDic;
import com.dispatch.sys.datadic.dao.DataDicDao;
import com.dispatch.sys.datadic.service.DataDicService;
@Service
public class DataDicServiceImpl implements DataDicService {

	@Autowired
	private DataDicDao dataDicDao;
	/**
	 * 实现说明：获取数据字典树 . <BR>
	 */
	@Override 
	public List<Map<String, Object>> AllDataDicTreeData(String pid) {
		
			List<Map<String, Object>> list = dataDicDao.getDataDicTreeData(pid);
			 List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
			 for (Map<String, Object> map : list) {
					Map<String, Object> m=new HashMap<String, Object>(); 
						m.put("id",map.get("ID"));
						m.put("text", map.get("DICNAME"));
					    String num = dataDicDao.getChildrenCount((String) map.get("ID")) + "";
			            m.put("state", "0".equals(num) ? "open" : "closed");
			            if(!"0".equals(num)){
			            	m.put("children", AllDataDicTreeData(map.get("ID").toString()));
			            }
			            m.put("typecode", map.get("TYPECODE"));
			            returnlist.add(m);
				} 
					return returnlist; 
		}
	
	/**
	 * 实现说明：数据字典列表查询 . <BR>
	 */
	@Override
	public Map getdatadicList(int pageSize, int currentPage, DataDic dic) {
		
		return dataDicDao.getdatadicList(pageSize, currentPage, dic);
	}
	/**
	 * 实现说明：保存数据字典信息 . <BR>
	 */
	@Override
	public boolean saveDatadic(DataDic dataDic) {
		
		return dataDicDao.saveDatadic(dataDic);
	}
	
	public DataDicDao getDataDicDao() {
		return dataDicDao;
	}
	public void setDataDicDao(DataDicDao dataDicDao) {
		this.dataDicDao = dataDicDao;
	}

	/**
	 * 实现说明：根据id查询字典数据 . <BR>
	 */
	@Override
	public DataDic findDataDicById(String id) {
		
		return dataDicDao.findDataDicById(id);
	}

	/**
	 * 实现说明：修改字典信息 . <BR>
	 */
	@Override
	public int updateDic(DataDic dataDic) {
		
		return dataDicDao.updateDic(dataDic);
	}
	/**
	 * 实现说明：查询子节点个数 . <BR>
	 */
	@Override
	public int getChildrenCounts(String pid) {
		// TODO Auto-generated method stub
		return dataDicDao.getChildrenCounts(pid);
	}

	/**
	 * 实现说明：删除字典 . <BR>
	 */
	@Override
	public void deleteDic(DataDic dataDic) {
		
		dataDicDao.deleteDic(dataDic);
	}

	/**
	 * 实现说明：根据类型编码得到数据字典集合 . <BR>
	 */
	@Override
	public List getDataDicListByTypeCode(String typecode) {
		
		return dataDicDao.getDataDicByTypeCode(typecode);
	}

	/**
	 * 实现说明：检查默认选项 . <BR>
	 */
	@Override
	public List checkIsSelected(DataDic dataDic) {
		
		return dataDicDao.checkIsSelected(dataDic);
	}
}
