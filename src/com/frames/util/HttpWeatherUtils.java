package com.frames.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP天气获取工具类
 * 
 * @author pangchengyong
 * 
 */
public class HttpWeatherUtils {
	
	/**
	 * 天气接口
	 */
	//public static String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName";
	public static String url = "http://api.k780.com:88/";
	
	
	
	/**
	 * 使用Get方式获取数据
	 * 
	 * @param url
	 *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
	 * @param charset
	 * @return
	 */
	public static String sendGet(String url, String charset) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * POST请求，字符串形式数据
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求数据
	 * @param charset
	 *            编码方式
	 */
	public static String sendPostUrl(String url, String param, String charset) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * POST请求，Map形式数据
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求数据
	 * @param charset
	 *            编码方式
	 */
	public static String sendPost(String url, Map<String, String> param, String charset) {

		StringBuffer buffer = new StringBuffer();
		if (param != null && !param.isEmpty()) {
			for (Map.Entry<String, String> entry : param.entrySet()) {
				buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");

			}
		}
		buffer.deleteCharAt(buffer.length() - 1);

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(buffer);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 
	 * @param cityName 城市名字
	 * @param format 返回格式  json或xml  默认json
	 * @return 七天内天气情况
	 */
	public static  String getWeatherFuture(String cityName , String format){
		Map<String , String > param = new HashMap<String , String>();
		if(null != cityName && !cityName.equals("") ){
			param.put("app", "weather.future");
			param.put("weaid", cityName);
			param.put("appkey", "10003");
			param.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
			if(format.equals("xml")){
				param.put("format", format);
			}else{
				param.put("format", "json");
			}
			
		}else{
			return "城市名字为空  ！ " ; 
		}
		return sendPost(url, param, "utf-8") ;
	}
	/**
	 * 
	 * @param format 返回格式  json或xml  默认json
	 * @return 返回七天内天津天气情况
	 */
	public static  String getWeatherByTianjin(){
		Map<String , String > param = new HashMap<String , String>();
			param.put("app", "weather.future");
			param.put("weaid", "101030100");
			param.put("appkey", "10003");
			param.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
			param.put("format", "json");
		return sendPost(url, param, "utf-8") ;
	}
	/**
	 * 
	 * @param cityName 城市名字
	 * @param format 返回格式  json或xml  默认json
	 * @return 当天天气情况
	 */
	public static  String getWeatherToDay(String cityName , String format){
		Map<String , String > param = new HashMap<String , String>();
		if(null != cityName && !cityName.equals("") ){
			param.put("app", "weather.today");
			param.put("weaid", cityName);
			param.put("appkey", "10003");
			param.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
			if(format.equals("xml")){
				param.put("format", format);
			}else{
				param.put("format", "json");
			}
			
		}else{
			return "城市名字为空  ！ " ; 
		}
		return sendPost(url, param, "utf-8") ;
	}
}