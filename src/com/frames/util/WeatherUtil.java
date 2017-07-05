/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:dispatch<BR>
 * File name:  WeatherUtil.java     <BR>
 * Author: guoyuejun  <BR>
 * Project:dispatch    <BR>
 * Version: v 1.0      <BR>
 * Date: 2016年1月9日 下午11:26:03 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */

package com.frames.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WeatherUtil {
	public static String getWeather() {
		StringBuffer strBuf;
		String baiduUrl = "";
        
		baiduUrl = "http://api.k780.com:88/?app=weather.future&weaid=101030000&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";
//		baiduUrl = "http://m.weather.com.cn/mpub/hours/101030100.html";
		strBuf = new StringBuffer();
		try {
			URL url = new URL(baiduUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));// 转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuf.toString();
	}
	public static void main(String[] args) throws DocumentException {
		
		String a = WeatherUtil.getWeather(); 
		
		try {  
            SAXReader reader = new SAXReader();  
             Document doc;   
             doc = DocumentHelper.parseText(a);   
  
            //Document doc = reader.read(ffile); //读取一个xml的文件  
            Element root = doc.getRootElement();  
            Attribute testCmd= root.attribute("root");  
            System.out.println(testCmd.getName()+"-***--"+testCmd.getValue());                         
            Element eName = root.element("Name");  
            System.out.println("节点内容*--"+eName.getTextTrim());        
          
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

	}
//	public static String parseXml(String str){
//		String className 	= 	str.get
//		return className;
////		String url 		 	= 	ParseXML.getText(path, "url");
////		String username  	= 	ParseXML.getText(path, "name");
////		String password  	= 	ParseXML.getText(path, "password");
////		String maxactive	=	ParseXML.getText(path, "maxActive");
//	}

}
