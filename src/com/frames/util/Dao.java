package com.frames.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dao {
	
	private MyConn conn;
	
	private int count=0;
	
	private List list;
	
	public Dao(){
		conn=new MyConn();
	}
	
	public List excuteSerch(String sql,String columns []){
		count++;
		
		list=new ArrayList();
		if(count>150){
			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn=new MyConn();
			count=0;
		}
		
		ResultSet rs= conn.executeQuery(sql);
		
		try {
			while(rs.next()){
				HashMap map=new HashMap();
				for(int i=0;i<columns.length;i++){
					map.put(columns[i], rs.getString(columns[i]));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void excuteInsert(String sql){
		count++;
		if(count>150){
			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn=new MyConn();
			
			count=0;
		}
		
		conn.executeUpdate(sql);
	}
	
	
	

}
