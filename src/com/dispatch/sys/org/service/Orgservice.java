package com.dispatch.sys.org.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dispatch.sys.bean.Org;
/**
 * 功能描述： 机构服务信息 .  <BR>
 */
public interface Orgservice {
	/**
	 * 方法说明：查询所有信息展示成树形 . <BR>
	 */
	public   List<Map<String, Object>> getOrgTreeData(Org org,String t);
	/**
	 * 方法说明： . <BR>
	 */
	public Org getOrgnByOrgId(String orgId);
	/**
	 * 方法说明：保存机构信息. <BR>
	 */
	public int saveOrg(Org org, HttpServletRequest request);
	
	/**
	 * 方法说明：检测组织编码的唯一性 . <BR>
	 */
	public int checkOrgCode(Org org);
	
	/**
	 * 方法说明：检测简拼的唯一性  . <BR>
	 */
	public int checkJianPin(Org org);
	/**
	 * 方法说明：修改机构信息 . <BR>
	 */
	public int updateOrg(Org org, HttpServletRequest request);
	/**
	 * 方法说明： 删除机构信息. <BR>
	 */
	public int deleteOrg(Org org);
	/**
	 * 通过id查询机构信息
	 */
	public Org findOrgById(String id);
	/**
	* 方法说明：查询是否有子节点 . <BR>
	*/
	public int findChildrenCount(String id);
	/**
	 * 方法说明：查询该节点是否为跟节点 . <BR>
	 */
	public int findParentCount(String id);
	
	public List<Map<String,Object>> getOrgTreeDateByOrgId(String id);
	
	/**
	 * 方法说明： 查询符合条件的组织机构树. <BR>
	 */
	public List<Map<String, Object>> selectTreeData(String orgName,String managerType);
	
	/**
	 * 方法说明： 组织机构调级. <BR>
	 */
	public void tjOrg(String sureId, String idd);
	
	/**
	 * 方法说明：查询可用的组织机构树 . <BR>
	 */
	public List<Map<String, Object>> findTreeData(String id, String t);
	
	/**
	 * 方法说明： . <BR>
	 */
	public List<Map<String, Object>> zTreeData(Org org);
	public Map selectOrgList(Org org, int currentPage, int pageSize, String oid);
	/**
	 * 方法说明：首页点击多个年份时查询要显示的所有单位  . <BR>
	 */
	public List selectOrgList(String oid,Org org);

}
