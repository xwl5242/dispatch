package com.frames.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.frames.util.Page;
import com.frames.util.PageData;
public class PageListJdbcTemplate extends JdbcTemplate{
	@Autowired
	private DataSource dataSource; 
	public PageListJdbcTemplate(){ 
		
	}  
	public PageListJdbcTemplate(DataSource dataSource) {   
		this.dataSource = dataSource; 
		super.setDataSource(dataSource);  
	}  
	/**
	 * 方法说明： 复杂列表查询 . <BR>
	 */
	public Map queryGridist(String sql,String conditions,int pageNo, int pageSize,Object obj[])throws DataAccessException{
			String gridSql=oracleGetSql(sql+conditions, pageNo, pageSize); 
			String coutSql="select count(1)  from ("+ sql+conditions + ") ";
			int count=	super.queryForInt( coutSql,obj); 
			Map gridMap=new HashMap();
			gridMap.put("total", count);
			gridMap.put("rows",  queryForList(gridSql,obj)); 
			return gridMap;  
	}  
	
	public Map queryGridist(String sql,String conditions,int pageNo, int pageSize)throws DataAccessException{
		String gridSql=oracleGetSql(sql+conditions, pageNo, pageSize); 
		String coutSql="select count(1)  from ("+ sql+conditions + ") ";
		int count=	super.queryForInt( coutSql); 
		Map gridMap=new HashMap();
		gridMap.put("total", count);
		gridMap.put("rows",  queryForList(gridSql)); 
		return gridMap;  
    }  
	
	public String oracleGetSql(String oldSqlStr, int pageNo, int pageSize){
		int low =  (pageNo - 1) * pageSize + 1;
		int up = pageNo * pageSize;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM(SELECT A.*, rownum as rid FROM( ");
		sb.append(oldSqlStr);
		sb.append((new StringBuilder()).append(") A WHERE rownum<=").append(up).append(") WHERE rid >=").append(low).toString());
		return sb.toString();
	}
	
	/**
	 * 方法说明：单表快速列表查询. <BR>
	 */
	public Map queryGridistByTableName(String tableName,int pageNo, int pageSize,String conditions)throws DataAccessException{
		String tableSql=" select * from " + tableName + " where 1=1 " + conditions;
		String gridSql=oracleGetSql(tableSql, pageNo, pageSize); 
		String coutSql="select count(1)  from "+ tableName + " where 1=1 " + conditions;
		int count=	super.queryForInt( coutSql);
		Map gridMap=new HashMap();
		gridMap.put("total", count);
		gridMap.put("rows",  queryForList(gridSql)); 
		return gridMap;  
	} 
	public DataSource getDataSource() {
		return dataSource;
	} 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 方法说明：Html5专用分页信息 . <BR>
	 */
	public List queryGridist(Page page,String sql){
		String coutSql="select count(1)  from ("+ sql + ") ";
		int count=	super.queryForInt( coutSql);
		page.setTotalResult(count);
		page.setEntityOrField(true);	
		String gridSql=getPageDataOfSql(page,sql); 
		List list=queryForList(gridSql);
		return list;
		
	}
	/**
	 * 方法说明：Html5专用分页信息 . <BR>
	 */
	public String getPageDataOfSql(Page page,String sql){
		StringBuffer pageSql = new StringBuffer();
		pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
		pageSql.append(sql);
		pageSql.append(") tmp_tb where ROWNUM<=");
		pageSql.append(page.getCurrentResult()+page.getShowCount());
		pageSql.append(") where row_id>");
		pageSql.append(page.getCurrentResult());
		return pageSql.toString();
	}
	
}
