package com.dispatch.sys.org.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.org.dao.IOrgDao;
import com.dispatch.sys.org.service.Orgservice;

@Service
public class OrgserviceImpl implements Orgservice {
	
	@Autowired
	IOrgDao orgdao;
	/**
	 * 查询所有机构信息以树形展示
	 */
	 public List<Map<String, Object>>  getOrgTreeData(Org org,String t) {
		List<Map<String, Object>> list = orgdao.getOrgTreeData(org, "0");
		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		if (!"0".equals(t)) {
			List<Map<String, Object>> listc = orgdao.getOrgTreeData(org, "1");
			for (Map<String, Object> map : listc) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", map.get("ID"));
				m.put("text", map.get("ORGNAME"));
				m.put("status", map.get("ISDELETE"));
				String num = map.get("NUMS")+ "";
				m.put("state", num.equals("0") ? "open" : "closed");
				String type=map.get("ORGTYPE")+"";
				getTypeIcon(type, m);
				returnlist.add(m);
			}
		} else {
			for (Map<String, Object> map : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", map.get("ID"));
				m.put("text", map.get("ORGNAME"));
				m.put("status", map.get("ISDELETE"));
				String num = map.get("NUMS")+ "";
				m.put("state", num.equals("0") ? "open" : "closed");
				String type=map.get("ORGTYPE")+"";
				getTypeIcon(type, m);
				returnlist.add(m);
			}
		}
		return returnlist;

	}
	 public void getTypeIcon(String type,Map m){
		 if(type.equals("1")){
			 m.put("iconCls","icon-org");
		 }else{
			 m.put("iconCls","");
		 }
		
	 }
	 	
	  public Map<String, List<Org>> list4Map(List<Org> list) {  
	    	  
	        Map<String, List<Org>> map = new HashMap<String, List<Org>>();  
	  
	        if ((list != null) && (list.size() != 0)) {  
	            for (Org test : list) {  
	                List<Org> testList = map.get(test.getId());  
	                if (testList == null) {  
	                    testList = new ArrayList<Org>();  
	                }  
	                testList.add(test);  
	                map.put(test.getId(), testList);  
	            }  
	        }  
	        return map;  
	    }  
	
	@Override
	public Org getOrgnByOrgId(String orgId) {
		return orgdao.getOrgnByOrgId(orgId);
	}

	/**
	 * 通过id查询机构信息
	 */
	public Org findOrgById(String id){
		Org org = null;
		if(!id.isEmpty()){
			org=orgdao.findOrgById(id);
		}
		return org; 
	}
	/**
	 * 保存机构信息
	 */
	@Override
	public int saveOrg(Org org, HttpServletRequest request) {

		int i =0;
		if(org!=null){
			   i =orgdao.addOrg(org,request);
			   }else{ return 0;}
				return i;
	
	}
	/**
	 * 实现说明：检查组织编码唯一性 . <BR>
	 */
	public int checkOrgCode(Org org){
		int i = 0;
		if (org != null) {
			i = orgdao.checkOrgCode(org);
		} else {
			return 0;
		}
		return i;
	}
	
	/**
	 * 实现说明：检查简拼唯一性 . <BR>
	 */
	public int checkJianPin(Org org){
		int i = 0;
		if (org != null) {
			i = orgdao.checkJianPin(org);
		} else {
			return 0;
		}
		return i;
	}
	
	public IOrgDao getOrgdao() {
		return orgdao;
	}

	public void setOrgdao(IOrgDao orgdao) {
		this.orgdao = orgdao;
	}

	@Override
	public int deleteOrg(Org org) {
		int i =0;
		if(org!=null){
			i = orgdao.deleteOrg(org);
		}else{
			return 0;
		}
		return i;
	}
	/**
	 * 修改机构信息
	 */
	@Override
	public int updateOrg(Org org, HttpServletRequest request) {
		 int i =0;
		 if(org!=null){
			i =  orgdao.editOrg(org, request);
		
		 }else{
			 return 0;
		 }
		return i;
	}

	/**
	 * 实现说明：查询是否有子节点 . <BR>
	 */
	@Override
	public int findChildrenCount(String id) {
		int count = 0;
		if(!id.isEmpty()){
			 count=orgdao.getChildrenOrg(id);
		}
		return count; 
		
	}
	/**
	 * 实现说明：查询是否为根节点 . <BR>
	 */
	public int findParentCount(String id){
		int count=0;
		if(!id.isEmpty()){
			 count=orgdao.findParentCount(id);
		}
		return count;
	}
	
	@Override
	public List<Map<String, Object>> getOrgTreeDateByOrgId(String id) {
		Org org=orgdao.getOrgnByOrgId(id);
		 List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
			Map<String, Object> m=new HashMap<String, Object>(); 
				m.put("id",org.getId());
				m.put("text", org.getOrgName());
			    String num = orgdao.getChildrenCount(org.getId()) + "";
	            m.put("state", num.equals("0") ? "open" : "closed");
	           if(!"0".equals(num)){
	           		m.put("children", getOrgTreeData(org,"0"));
	           }
	           returnlist.add(m);
	          
		return returnlist;
	}

	/**
	 * 实现说明： 查询符合条件的组织机构树. <BR>
	 */
	@Override
	public List<Map<String, Object>> selectTreeData(String orgName,String managerType) {
		return orgdao.selectTreeData(orgName,managerType);
	}

	/**
	 * 实现说明： 组织机构调级. <BR>
	 */
	@Override
	public void tjOrg(String sureId, String idd) {
		orgdao.tjOrg(sureId,idd);
	}

	/**
	 * 实现说明：查询可用的组织机构树 . <BR>
	 */
	@Override
	public List<Map<String, Object>> findTreeData(String pid, String t) {
		List<Map<String, Object>> list = orgdao.findTreeData(pid,"0"); 
	   	 List<Map<String, Object>> returnlist = new ArrayList<Map<String,Object>>();
	   		if(!"0".equals(t)){
	   			List<Map<String, Object>> listc = orgdao.findTreeData(pid,"1"); 
	   			 for (Map<String, Object> map : listc) {
	   					Map<String, Object> m=new HashMap<String, Object>(); 
	   						m.put("id",map.get("ID"));
	   						m.put("text", map.get("ORGNAME"));
	   						m.put("status", map.get("ISDELETE"));
	   					    String num = orgdao.getChildrenCount((String) map.get("ID")) + "";
	   			            m.put("state", num.equals("0") ? "open" : "closed");
	   			            returnlist.add(m);
	   				}	
	   		}else{
	   		 for (Map<String, Object> map : list) {
	   			Map<String, Object> m=new HashMap<String, Object>(); 
	   				m.put("id",map.get("ID"));
	   				m.put("text", map.get("ORGNAME"));
	   				m.put("status", map.get("ISDELETE"));
	   			    String num = orgdao.getChildrenCount((String) map.get("ID")) + "";
	   	            m.put("state", num.equals("0") ? "open" : "closed");
	   	            returnlist.add(m);
	   		}
	   		}        
			return returnlist;
	}

	/**
	 * 实现说明： . <BR>
	 */
	@Override
	public List<Map<String, Object>> zTreeData(Org org) {
		return orgdao.zTreeData(org);
	}
	@Override
	public Map selectOrgList(Org org, int currentPage, int pageSize, String oid) {
		return orgdao.selectOrgList(org,currentPage,pageSize,oid);
	}
	/**
	 * 实现说明：首页点击多个年份时查询要显示的所有单位  . <BR>
	 */
	@Override
	public List selectOrgList(String oid, Org org) {
		return orgdao.selectOrgList(oid, org);
	}

}
