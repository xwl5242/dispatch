package com.dispatch.sys.datadic.dao;

import java.util.List;
import java.util.Map;

import com.dispatch.sys.bean.DataDic;

public interface DataDicDao {
	/**
	 * 方法说明：获取数据字典树 . <BR>
	 */
	public List<Map<String, Object>> getDataDicTreeData(String pid);
	/**
	 * 方法说明：根据父节点id得到子节点个数 . <BR>
	 */
	public int getChildrenCount(String id);
	/**
	 * 方法说明：数据字典列表查询 . <BR>
	 */
	public Map getdatadicList(int pageSize,int currentPage, DataDic dic);
	/**
	 * 方法说明：保存数据字典信息 . <BR>
	 */
	public boolean saveDatadic(DataDic dataDic);
	/**
	 * 方法说明：得到所有的类型编码集合 . <BR>
	 */
	public List<Map<String,Object>> getAllTypeCode();
	/**
	 * 方法说明：根据类型编码得到数据字典集合 . <BR>
	 */
	public List<Map<String,Object>> getDataDicByTypeCode(String typecode);
	/**
	 * 方法说明：根据id查询字典数据 . <BR>
	 */
	public DataDic findDataDicById(String id);
	/**
	 * 方法说明：修改字典信息 . <BR>
	 */
	public int updateDic(DataDic dataDic);
	/**
	 * 方法说明：根据父节点id得到子节点个数 . <BR>
	 */
	public int getChildrenCounts(String id);
	/**
	 * 方法说明：删除字典 . <BR>
	 */
	public void deleteDic(DataDic dataDic);
	/**
	 * 方法说明：检查默认选项 . <BR>
	 */
	public List checkIsSelected(DataDic dataDic);
}
