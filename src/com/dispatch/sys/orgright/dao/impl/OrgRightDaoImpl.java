package com.dispatch.sys.orgright.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Org;
import com.dispatch.sys.orgright.dao.OrgRightDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.util.EnumUtil;
import com.util.common.UUIDGenerator;
@Repository
public class OrgRightDaoImpl extends PageListJdbcTemplate implements OrgRightDao {
  
	/**
	 * 实现说明：获取组织机构树形列表 . <BR>
	 */
	public Map getOrgTreeGrid(Org o2) { 
		String sql = "select * from (SELECT id,orgcode,orgname,(select d.dicname from sys_datadic d where d.keyname = t.managertype and d.typecode = 'OWNERTYPE') managertype, sortname,(select d.dicname from sys_datadic d where d.keyname = t.orgtype and d.typecode = 'ORGTYPE') orgtype,parentid,seq FROM SYS_ORG t where isdelete='"+EnumUtil.RIGHTSTAT1+"' START WITH orgname='"+o2.getOrgName()+"' CONNECT BY PRIOR ID=PARENTID  union all SELECT id, orgcode, orgname, managertype, sortname, orgtype, parentid,seq  FROM SYS_ORG where isdelete = '"+EnumUtil.RIGHTSTAT1+"' and parentid = '0'  union all SELECT id,orgcode,orgname,managertype,sortname,orgtype,parentid,seq FROM SYS_ORG where isdelete = '"+EnumUtil.RIGHTSTAT1+"' and parentid = '1') order by seq";
		
		sql="SELECT * "+
				"  FROM (SELECT ID, "+
				"               ORGCODE, "+
				"               ORGNAME, "+
				"               (SELECT D.DICNAME "+
				"                  FROM SYS_DATADIC D "+
				"                 WHERE D.KEYNAME = T.MANAGERTYPE "+
				"                   AND D.TYPECODE = 'OWNERTYPE') MANAGERTYPE, "+
				"               SORTNAME, "+
				"               (SELECT D.DICNAME "+
				"                  FROM SYS_DATADIC D "+
				"                 WHERE D.KEYNAME = T.ORGTYPE "+
				"                   AND D.TYPECODE = 'ORGTYPE') ORGTYPE, "+
				"               PARENTID, "+
				"               SEQ "+
				"          FROM SYS_ORG T "+
				"         WHERE ISDELETE = '"+EnumUtil.PARAMETERSSTATUS1+"' "+
				"         START WITH id = '"+o2.getId()+"' "+
				"        CONNECT BY PRIOR ID = PARENTID "+
				"       ) "+
				" ORDER BY SEQ ";
		return 	super.queryGridist(sql,"",1, 100);
	}

	/**
	 * 实现说明：组织保存选中权限 . <BR>
	 */
	@Override
	public boolean checkOrgRight(String rightIds,String orgId) {
		boolean bool = false;
		String[] ids=rightIds.split(",");
		String deleteSql="delete sys_orgright where orgId='"+orgId+"'";
		super.execute(deleteSql);
		
		if(rightIds!=""){
			try {
				for(int i=0;i<ids.length;i++){
					String uuid=UUIDGenerator.getUUID();
					String saveSql="INSERT INTO sys_orgright (id,rightid,orgid) values (?,?,?)";
					super.update(saveSql,new Object[]{uuid,ids[i],orgId});
					
				}  
				bool = true;
			} catch (Exception e) {
				logger.error(e.toString(), e);
				bool = false;
			}
			
		}
		return bool;
	}

	@Override
	public List getRightByOrgId(String orgId) {
		String ids[]=orgId.split(",");
		String conSql="";
		for(int i=0;i<ids.length;i++){
			if ( conSql != "") conSql += ","; 
			conSql+="'"+ids[i]+"'";
		}
		String sql="select distinct RIGHTID from sys_orgright where orgid in("+conSql+")";
		return super.queryForList(sql); 
	} 
 
} 
