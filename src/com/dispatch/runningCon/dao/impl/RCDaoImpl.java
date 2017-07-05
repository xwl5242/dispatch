package com.dispatch.runningCon.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dispatch.runningCon.dao.RCDao;
import com.frames.jdbc.PageListJdbcTemplate;

@Repository
public class RCDaoImpl extends PageListJdbcTemplate implements RCDao {

	@Override
	public Map<String,Object> queryRealDataByNodeId(int page,int rows,String nodeId) {
		String sql = "select R.ID,R.COLLNAME,R.COLLVALUE,R.COLLDATE,U.UNITNAME NODENAME from t_real R,ECC_IEU_UNITINFO U where R.NODEID = U.ID AND R.nodeId = '"+nodeId+"'";
		return super.queryGridist(sql, "", page, rows);
	}

	@Override
	public Map<String,Object> queryHistoryDataByNodeId(String collName,String nodeId, String startdate, String enddate,String minvalue,
			String maxvalue,String eqvalue, int currentPage,int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (select A.colldate");
		List<Map<String,Object>> collList = queryCollByNodeId(nodeId);
		
		for(Map<String,Object> collMap:collList){
			String newCollName = collMap.get("COLLNAME").toString();
			sb.append(",sum(decode(A.collname,'"+newCollName+"',A.collvalue)) as H"+newCollName);
		}
		sb.append(" FROM (");
		
		sb.append("SELECT * FROM t_history HI where 1=1 ");
		if(null!=startdate&&!"".equals(startdate)){
			sb.append(" AND HI.COLLDATE >= '"+startdate+"' ");
		}
		if(null!=enddate&&!"".equals(enddate)){
			sb.append(" AND HI.COLLDATE <= '"+enddate+"' ");
		}
		sb.append(" AND HI.NODEID = '"+nodeId+"' ) A GROUP BY A .colldate ORDER BY A.colldate desc)B where 1=1 ");
		if(null!=collName&&!"".equals(collName)){
			if(null!=minvalue&&!"".equals(minvalue)){
				sb.append(" AND B.H"+collName+">="+minvalue);
			}
			if(null!=maxvalue&&!"".equals(maxvalue)){
				sb.append(" AND B.H"+collName+"<="+maxvalue);
			}
			if(null!=eqvalue&&!"".equals(eqvalue)){
				sb.append(" AND B.H"+collName+"="+eqvalue);
			}
		}
		return super.queryGridist(sb.toString(), "", currentPage, pageSize);
	}
	
	@Override
	public List<Map<String, Object>> queryCollByNodeId(String nodeId) {
		String collnameSQL = "select collname from t_real where nodeid = '"+nodeId+"'";
		return super.queryForList(collnameSQL);
	}

	@Override
	public Map<String,Object> queryRealDataLine(String nodeId,String startTime,String endTime,boolean isHis) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String, Object>> collNames = new ArrayList<Map<String,Object>>();
		
		String collnameSQL = "select collname from t_real where nodeid = '"+nodeId+"'";
		collNames = super.queryForList(collnameSQL);
		if(null!=collNames&&collNames.size()>0){
			for(Map<String,Object> map : collNames){
				String collname = map.get("COLLNAME").toString();
				if(isHis){
					String sql = "select collname,collvalue,colldate scadatime from t_history where colldate >= "
							+ "'"+startTime+"' and colldate <= '"+endTime+"' and "
							+ "nodeid = '"+nodeId+"' and collname = '"+collname+"' ORDER BY SCADATIME ASC";
					
					result.put(collname, super.queryForList(sql));
				}else{
					String sql = "select collname,collvalue,colldate scadatime from t_history where substr(colldate,0,10) = "
							+ "to_char(SYSDATE,'yyyy-mm-dd') and nodeid = '"+nodeId+"' and collname = '"+collname+"' "
							+ "ORDER BY SCADATIME ASC";
					
					result.put(collname, super.queryForList(sql));
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> runningCon(String nodeId) {
		Map<String, Object> result = new HashMap<String,Object>();
		String sql = "select u.unitname nodename,r.collname,r.collvalue,r.colldate scadatimes from t_real r,ECC_IEU_UNITINFO u where u.id = r.nodeid and u.id = '"+nodeId+"'";
		List<Map<String,Object>> rList =  super.queryForList(sql);
		if(null!=rList&&rList.size()>0){
			for(Map<String,Object> map:rList){
				String collname = map.get("collname").toString();
				result.put(collname, map);
			}
		}
		return result;
	}

}
