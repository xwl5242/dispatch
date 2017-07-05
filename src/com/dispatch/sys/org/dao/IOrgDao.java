package com.dispatch.sys.org.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dispatch.sys.bean.Org;

public interface IOrgDao {
	/**
	 * 方法说明：组织机构查询 . <BR>
	 */
	public List<Map<String, Object>> getOrgTreeData(Org org,String t);
	
	/**
	 * 方法说明： 获得某组织机构直属上级删除组织机构 . <BR>
	 */
	public int getChildrenCount(String id);
	
	/**
	 * 方法说明：根据orgId取得结果集 . <BR>
	 */
	public Org getOrgnByOrgId(String orgId);
	
	/**
	 * 方法说明：组织机构新增加 . <BR>
	 */
	public Integer addOrg(Org org, HttpServletRequest request);
	
	/**
	 * 方法说明： . <BR>
	 */
	public int checkOrgCode(Org org);
	
	/**
	 * 方法说明： . <BR>
	 */
	public int checkJianPin(Org org);
	
	/**
	 * 方法说明 ：编辑组织机构 . <BR>
	 */
	public Integer editOrg(Org org, HttpServletRequest request);
	
	/**
	 * 方法说明： 获得某组织机构直属上级删除组织机构. <BR>
	 */
	public List<Map<String,Object>> getParentOrgList(Long id);
	
	/**
	 * 方法说明：通过id查询机构信息 . <BR>
	 */
	public Org findOrgById(String id);
	
	/**
	 * 删除机构信息
	 */
	public int deleteOrg(Org org);
	
	/**
	 * 查询所有可显示机构的信息
	 */
	
	int getOrgCount();
	
	/**
	 * 方法说明：查询子节点 . <BR>
	 */
	public int getChildrenOrg(String id);
	
	/**
	 * 方法说明：查找是否为根节点 . <BR>
	 */
	public int findParentCount(String id);

	/**
	 * 方法说明： 查询符合条件的组织机构树. <BR>
	 */
	public List<Map<String, Object>> selectTreeData(String orgName,String managerType);

	/**
	 * 方法说明： 组织机构调级. <BR>
	 */
	public void tjOrg(String sureId, String idd);

	/**
	 * 方法说明： 查询可用的组织机构树. <BR>
	 */
	public List<Map<String, Object>> findTreeData(String pid, String string);

	/**
	 * 方法说明： . <BR>
	 */
	public List<Map<String,Object>> zTreeData(Org org);

	public Map selectOrgList(Org org, int currentPage, int pageSize, String oid);
	
	/**
	 * 方法说明：首页点击多个年份时查询要显示的所有单位 . <BR>
	 */
	public List selectOrgList(String oid,Org org);
	
	/**
	 * 方法说明：首页多年份查询点击详细时查询所有应显示的单位 . <BR>
	 */
	public List selectDetailOrgList(String oid,String codeid);
	
}
