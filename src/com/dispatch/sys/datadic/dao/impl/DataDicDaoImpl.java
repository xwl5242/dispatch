package com.dispatch.sys.datadic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.DataDic;
import com.dispatch.sys.datadic.dao.DataDicDao;
import com.frames.jdbc.PageListJdbcTemplate;
import com.frames.util.CreateSqlTool;
import com.util.common.ECCBeanUtil;
import com.util.common.UUIDGenerator;
@Repository
public class DataDicDaoImpl extends PageListJdbcTemplate implements DataDicDao{
	/**
	 * 实现说明：根据父节点id得到子节点个数 . <BR>
	 */
	public int getChildrenCount(String id) {
		String sql ="select count(1) from  SYS_DATADIC where PARID='"+id+"' and PARID IN ('0','1')  ";
		int ChildrenCount =super.queryForInt(sql);
		return ChildrenCount;
	}
	/**
	 * 实现说明：获取数据字典树 . <BR>
	 */
	public List<Map<String, Object>> getDataDicTreeData(String pid) {
		List<Map<String, Object>>  list;
		   if("0".equals(pid)){ 
			    list =super.queryForList("SELECT id,DICNAME FROM SYS_DATADIC where PARID ='"+pid+"' order by to_number(dicsort)");
		   }else{
			    list =super.queryForList("SELECT id,DICNAME FROM SYS_DATADIC where PARID ='"+pid+"'   and PARID IN ('0','1') order by to_number(dicsort) ");
		   }
			return list;
	}
	/**
	 * 实现说明：数据字典列表查询 . <BR>
	 */
	@Override
	public Map getdatadicList(int pageSize, int currentPage, DataDic dic) {
		
		String sql = " select ID,TYPECODE,KEYNAME,DICNAME,decode(ISSELECTED,'false','否','true','是',NULL,'否') ISSELECTED,DICSORT from SYS_DATADIC where 1=1 ";
		String conditions= "";
		
		if(dic.getTypecode()!= null && !dic.getTypecode().equals("")){
			conditions += " and typecode like '%"+dic.getTypecode()+"%' ";
			dic.setParid("");
		}
		if(dic.getDicname()!= null && !dic.getDicname().equals("")){
			conditions += " and dicname like '%"+dic.getDicname()+"%' ";
			
			dic.setParid("");
		}
		if(dic.getParid()!= null && !dic.getParid().equals("")){
			 conditions=" and parid = '"+dic.getParid()+"' "; 
		}
		conditions += " order by to_number(dicsort)";
		return 	super.queryGridist(sql, conditions, currentPage, pageSize);
	}

	/**
	 * 实现说明： 数据字典保存. <BR>
	 */
	@Override
	public boolean saveDatadic(DataDic dataDic) {
		try {
			int n = 0;
			if(dataDic!=null){
				String uuid=UUIDGenerator.getUUID();
				dataDic.setId(uuid);
				String sql = CreateSqlTool.genInsertSql("com.dispatch.sys.bean.DataDic", dataDic, "sys_datadic");
				n = super.update(sql);
			}
			return n>0?true:false;
		} catch (DataAccessException e) {
			logger.error(e.toString(), e);
			return false;
		}
	}
	/**
	 * 实现说明：得到所有的类型编码集合 . <BR>
	 */
	@Override
	public List<Map<String, Object>> getAllTypeCode() {
		
		String sql="select d.typecode from sys_datadic d where typecode is not null  group by typecode";
		return super.queryForList(sql);
	}
	/**
	 * 实现说明：根据类型编码得到数据字典集合 . <BR>
	 */
	@Override
	public List<Map<String, Object>> getDataDicByTypeCode(String typecode) {
		String sql="select d.keyname as id ,d.dicname as text,d.isselected selected from sys_datadic d where d.typecode='"+typecode+"' and d.parid!='1' and d.parid!='0' order by to_number(d.dicsort) ";
		return super.queryForList(sql);
	} 
	
	/**
	 * 实现说明：根据id查询字典数据 . <BR>
	 */
	public DataDic findDataDicById(String id) {
		DataDic datadic =null;
		datadic= (DataDic)super.queryForObject("select * from SYS_DATADIC where id=?", new Object[] { id }, new RowMapper<Object>() {  
            @Override  
            public Object mapRow(ResultSet rs, int rowNum)  
               throws SQLException {  
            	 DataDic datadic2= new DataDic();
               try {
				ECCBeanUtil.resultSetToBean(rs, datadic2); 
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}
               return 	datadic2; 
            }
            }  );
		return datadic;
	}

	/**
	 * 实现说明：数据字典修改 . <BR>
	 */
	@Override
	public int updateDic(DataDic dataDic) {
		int i = 0;
		try {
			if(dataDic!=null){
				String sql = CreateSqlTool.genUpdateSql("com.dispatch.sys.bean.DataDic", dataDic, "sys_datadic"," where ID='"+dataDic.getId()+"'");
				DataDic dataDicById = findDataDicById(dataDic.getId());
				if(dataDicById!=null&&!dataDicById.getTypecode().equals(dataDic.getTypecode())){
					String str = "update sys_datadic s set s.typecode='"+dataDic.getTypecode()+"' where s.parid='"+dataDic.getId()+"'";
					super.update(str);
				}
				i = super.update(sql);
			}
		} catch (DataAccessException e) {
			logger.error(e.toString(), e);
		}
		return i;  
	}
	/**
	 * 实现说明：删除字典 . <BR>
	 */
	@Override
	public void deleteDic(DataDic dataDic) {
		
		String sql =  "delete from SYS_DATADIC where id='"+dataDic.getId()+"' or parid='"+dataDic.getId()+"'";
		super.update(sql);
	}

	/**
	 * 实现说明：检查默认选项 . <BR>
	 */
	@Override
	public List checkIsSelected(DataDic dataDic) {
		
		String sql="select * from sys_datadic where typecode='"+dataDic.getTypecode()+"' and isselected='true'" ;
		List list=super.queryForList(sql);
		return list;
	}
	/**
	 * 实现说明：根据父节点id得到子节点个数 . <BR>
	 */
	@Override
	public int getChildrenCounts(String id) {
		String sql ="select count(1) from  SYS_DATADIC where PARID='"+id+"'";
		return super.queryForInt(sql);
	}
	
}
