package com.frames.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


public class PublicUtil {
	
	private static String startDate;
	
	private static String endDate;
	
	public static void main(String[] args) {
		System.out.println("本机的ip=" + PublicUtil.getIp());
	}
	
	public static String getPorjectPath(){
		String nowpath = "";
		nowpath=System.getProperty("user.dir")+"/";
		
		return nowpath;
	}
	
	/**
	 * 获取本机ip
	 * @return
	 */
	public static String getIp(){
		String ip = "";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
			//System.out.println("本机的ip=" + ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ip;
	}
	public static void getDateByDateType(String type,String oid){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int num = 0;
		if ("1".equals(type)) {
			startDate=simple.format(new Date());
			num = 1;
			cal.setTime(new Date());
			cal.add(cal.DATE, num);
			endDate=simple.format(cal.getTime());
		} else if ("2".equals(type)) {
			num = -1;
			cal.setTime(new Date());
			cal.add(cal.DATE, num);
			endDate=(simple.format(cal.getTime()));
			startDate=(simple.format(cal.getTime()));
		} else if ("3".equals(type)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			startDate=(simple.format(cal.getTime()));
			cal.setTime(new Date());
			cal.add(cal.DATE, 0);
			endDate=(simple.format(cal.getTime()));
		} else if ("4".equals(type)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.add(cal.DATE, -7);
			startDate=(simple.format(cal.getTime()));
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.add(cal.DATE, -1);
			endDate=(simple.format(cal.getTime()));
		} else if ("5".equals(type)) {
			
			
			cal.set(Calendar.DAY_OF_MONTH, 1);
			startDate=(simple.format(cal.getTime()));
			cal.setTime(new Date());
			cal.add(cal.DATE, 0);
			endDate=(simple.format(cal.getTime())); 
		} else if ("6".equals(type)) {
			
			
			GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -1);
			Date theDate = cal.getTime();
			gcLast.setTime(theDate);
			gcLast.set(Calendar.DAY_OF_MONTH, 1);
			startDate=(simple.format(gcLast.getTime()));
			cal.add(cal.MONTH, 1);
			cal.set(cal.DATE, 1);
			cal.add(cal.DATE, -1);
			endDate=(simple.format(cal.getTime()));
		} else if ("7".equals(type)) {
			String sql="SELECT t.gnjstartdate,t.gnjenddate from ECC_IEU_CAINUANJI t  WHERE t.unitid='"+oid+"' and t.gnid =(select max(gnid) from  ECC_IEU_CAINUANJI   WHERE unitid='"+oid+"' )  AND t.status='2' ";
			Dao dao=new Dao();
			List list=dao.excuteSerch(sql, new String []{"gnjstartdate","gnjenddate"});
			if(list.size()==1){
				HashMap map=(HashMap)list.get(0);
				startDate=map.get("gnjstartdate")+"";
				endDate=map.get("gnjenddate")+"";
			}
		}
	}
	
	/**
	 * 获得描述：String  startDate. <BR>
	 */
	public static String getStartDate() {
		return startDate;
	}

	/**
	 * 获得描述：String  startDate. <BR>
	 * @param startDate the startDate to set
	 */
	public static void setStartDate(String startDate) {
		PublicUtil.startDate = startDate;
	}

	/**
	 * 获得描述：String  endDate. <BR>
	 */
	public static String getEndDate() {
		return endDate;
	}

	/**
	 * 获得描述：String  endDate. <BR>
	 * @param endDate the endDate to set
	 */
	public static void setEndDate(String endDate) {
		PublicUtil.endDate = endDate;
	}

	
}