package com.dispatch.sys.log.dao.impl;

import org.springframework.stereotype.Repository;

import com.dispatch.sys.bean.Operatelog;
import com.dispatch.sys.log.dao.OperatelogDao;
import com.frames.jdbc.PageListJdbcTemplate;
@Repository
public class OperatelogDaoImpl extends PageListJdbcTemplate implements OperatelogDao{

	@Override
	public void saveOperateLog(Operatelog opt) {
		String sql="insert into SYS_OPERATELOG (ID, USERLOGINNAME, OPTTIME, CLASSNAME, METHODNAME, OPTREMARK, OPTURL, OPTURI, OPTIP, OPTHOST, OPTPORT , OPTKEY , OPTTYPE) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?)";
		super.update(sql,new Object[]{
				opt.getId(),opt.getUserLoginName(),opt.getOrtTime(),opt.getClassName(),opt.getMethodName(),opt.getOptRemark(),opt.getOptUrl(),opt.getOptUri(),opt.getOptIp(),opt.getOptHost(),opt.getOptPort(),opt.getOptkey(),opt.getOpttype()
		});
	}

}
