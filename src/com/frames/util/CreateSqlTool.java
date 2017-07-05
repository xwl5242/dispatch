/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  CreateSqlTool.java     <BR>
 * Author: xupei  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2015-7-17 上午9:38:32 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.frames.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.dispatch.sys.cache.GetDataDicCache;

/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者: xupei  <BR>
 * 时间：2015-7-17 上午9:38:32  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：2015-7-17 上午9:38:32 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
public class CreateSqlTool {
	
	private static String fieldTypeArray="";
	
	private static String fieldType="";
	
	
	
	public static void main(String arg[] ) throws IllegalArgumentException, IllegalAccessException {
		
	}
	
	public static String genUpdateSql(String bean,Object obj,String tableName,String condition){
		String column=getDataBaseColumn(tableName);
		String fieldList =  getBeanFilesList(bean,column);
		String valueList=getValueFieldList( bean, obj,column);
		String updateField=getBaseBeanFilesList(bean,"update",column);
		String updateValue=getBaseValueFieldList(bean, obj,"update",column);
		String sql="update "+tableName+" set ";
		if(updateValue.contains("@")){
			for(int i=0;i<updateValue.split("@").length;i++){
				if(!updateValue.split("@")[i].equals("'null'")){
					sql+=updateField.split("@")[i]+"="+updateValue.split("@")[i]+",";
				}
				
			}
		}
		
		for(int i=0;i<fieldList.split("@").length;i++){
			if(!valueList.split("@")[i].equals("'null'")){
				sql+=fieldList.split("@")[i]+"="+valueList.split("@")[i]+",";
			}
			
		}
		if(sql.endsWith(",")){
			sql=sql.substring(0,sql.length()-1);
		}
		
		return sql+condition;
	}
	
	public static String genInsertSql(String bean,Object obj,String tableName){  
		String column=getDataBaseColumn(tableName);
		String fieldList =  getBeanFilesList(bean,column);
		String valueList=getValueFieldList( bean, obj,column);
		String createField=getBaseBeanFilesList(bean,"create",column);
		String createValue=getBaseValueFieldList(bean, obj,"create",column);
		String apend="";
		if(createField.length()>0){
			apend=",";
		}
        return "insert into "+tableName+"("+fieldList.replaceAll("@", ",")+apend+createField.replaceAll("@", ",")+") values("+valueList.replaceAll("@", ",")+apend+createValue.replaceAll("@", ",")+")";
    }
	
	public static String  getDataBaseColumn(String table){
		HashMap map=GetDataDicCache.columnMap;
		String column="";
		String type="";
		ArrayList list=(ArrayList) map.get(table.toUpperCase());
		for(int i=0;i<list.size();i++){
			HashMap mapList=(HashMap)list.get(i);
			column+=mapList.get("column")+"";
			type+=mapList.get("type")+"";;
			if(i<list.size()-1){
				column+=",";
				type+=",";
			}
		}
		fieldTypeArray=type;
		return column;
	}
	
	public static String getValueFieldList(String bean,Object obj,String column){
		Field [] prFields=obj.getClass().getDeclaredFields();
		
		StringBuffer sb = new StringBuffer();  
		for(Field field:prFields)
        {
            
			if (!field.getName().equals("tableName")&&!field.getType().equals("List")&&getRealField(column,field.getName().toUpperCase())) {  
				field.setAccessible(true);
				
				try {
					sb.append(getValue(field.get(obj)+"",fieldType)+"@");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
             }  
       }
		if(sb.length()>0){
        	sb.deleteCharAt(sb.toString().lastIndexOf("@"));  
        } 
		
		return sb.toString();
	}
	public static String getBaseValueFieldList(String bean,Object obj,String type,String column){
		Field [] prFields=obj.getClass().getSuperclass().getDeclaredFields();
		
		StringBuffer sb = new StringBuffer();  
		for(Field field:prFields)
        {
            
			if (field.getName().contains(type)&&getRealField(column,field.getName().toUpperCase()))  {  
				field.setAccessible(true);
				
				try {
					sb.append(getValue(field.get(obj)+"",fieldType)+"@");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
             }  
       }
		if(sb.length()>0){
        	sb.deleteCharAt(sb.toString().lastIndexOf("@"));  
        }  
		return sb.toString();
	}
	public static String getValue(String value,String type){
		
		if(type.contains("NUMBER")){
			value="".equals(value)?null:""+value+"";
			//value=""+value+"";
		}else if(type.equals("DATE")){
			value="to_date('"+value+"','yyyy-MM-dd hh24:mi:ss')";
		}else{
			if(value==null){
				value="";
			}
			value="'"+value+"'";
		}
		
		return value;
		
	}
	 public static String getBeanFilesList(String bean,String column){  
	        try {  
	            Class clz = Class.forName(bean);
	            
	            Field[] strs = clz.getDeclaredFields();  
	            StringBuffer sb = new StringBuffer();  
	            for (int i = 0; i < strs.length; i++) {  
	                if (!strs[i].getName().equals("tableName")&&!strs[i].getType().equals("List")&&getRealField(column,strs[i].getName().toUpperCase())) {  
	                	sb.append(strs[i].getName()+"@"); 
	                }  
	            } 
	            if(sb.length()>0){
	            	sb.deleteCharAt(sb.toString().lastIndexOf("@"));  
	            }
	            
	            return sb.toString();  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	 }  
	 
	 public static String getBaseBeanFilesList(String bean,String type,String column){  
	        try {  
	            Class clz = Class.forName(bean);
	            
	            Field[] strs = clz.getSuperclass().getDeclaredFields();  
	            StringBuffer sb = new StringBuffer();  
	            for (int i = 0; i < strs.length; i++) {  
	                if (strs[i].getName().contains(type)&&getRealField(column,strs[i].getName().toUpperCase())) {  
	                   sb.append(strs[i].getName()+"@");  
	                }  
	            }
	            if(sb.length()>0){
	            	sb.deleteCharAt(sb.toString().lastIndexOf("@"));  
	            }
	            
	            return sb.toString();  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	 }
	 
	 public static boolean getRealField(String column,String field){
		 boolean bool=false;
		 fieldType="";
		 for(int i=0;i<column.split(",").length;i++){
			 String temp=column.split(",")[i];
			 if(temp.equals(field.toUpperCase())){
				 bool=true;
				 fieldType=fieldTypeArray.split(",")[i];
				 break;
			 }
		 }
		 return bool;
	 }
	 
	 

	/**
	 * 获得描述：String  fieldType. <BR>
	 */
	public static String getFieldType() {
		return fieldTypeArray;
	}

	/**
	 * 获得描述：String  fieldType. <BR>
	 * @param fieldType the fieldType to set
	 */
	public static void setFieldType(String fieldType) {
		CreateSqlTool.fieldTypeArray = fieldType;
	}
	 
	 
}
