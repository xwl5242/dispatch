package com.util.common;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;


/**
 * <code>Assert</code> 的注释
 * 判断空值或NULL的工具类
 */
public final class Assert {
    public static boolean notNull(Object obj){
        return obj!=null;
    }
    
    public static boolean isNull(Object obj){
        return !notNull(obj);
    }
    
    public static boolean notEmpty(String str){
        return notNull(str)&&(str.trim().length()>0);
    }
    
    public static boolean isEmpty(String str){
        return !notEmpty(str);
    }
    
    public static boolean notNullOrEmpty(String str){
        return notNull(str)&&notEmpty(str);
    }
    
    public static boolean isNullOrEmpty(String str){
        return !notNullOrEmpty(str);
    }

    public static String defaultString(Object o ,String defaultString){
    	if(o ==null)
    		return defaultString;
    	else
    		return o.toString();
    }
    /**
     * 增加天数函数
     * @param date 日期
     * @param n  增加或减少的天数
     * @return
     */
    public static String addDays(String date, int n) {
    	String returnDate = null;
    	if (notNullOrEmpty(date)) {
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date d = Date.valueOf(date);
	    	long l = d.getTime() + 24*60*60*n*1000;
	    	d.setTime(l);
	    	returnDate = sdf.format(d.getTime());
    	} else {
    		returnDate = "";
    	}
    	return returnDate;
    }

    /**
     * 将BigDecimal类型变量转换为字符串类型
     * 形如"####00.99"，其中99表示小数点后保留两位
     * @param b      格式化的对象
     * @param count  小数点后保留的位数
     * @return
     */
    public static String decimalFormat(BigDecimal b,int count) {
    	String strBit = "";
    	for (int i = 0;i < count;i++) {
    		strBit = strBit + "0";
    	}
    	DecimalFormat df = new DecimalFormat("0" + ("".equals(strBit)?"":("." + strBit)));
    	return df.format(b);
    }

    /**
     * 将BigDecimal类型变量转换为字符串类型，并以分为最小单位显示
     * 形如"####00.99"，其中99表示小数点后保留两位
     * @param b      格式化的对象
     * @param count  小数点后保留的位数
     * @return
     */
    public static String decimalFormat2(BigDecimal b,int count) {
    	String strBit = "";
    	for (int i = 0;i < count;i++) {
    		strBit = strBit + "0";
    	}
    	DecimalFormat df = new DecimalFormat("0" + ("".equals(strBit)?"":("." + strBit)));

    	if (b == null) {
    		b = BigDecimal.ZERO;
    	}
    	return df.format(b.multiply(new BigDecimal("100")));
    }
    
    /**
	 * 获取全局流水号
	 */
    public static synchronized String getTranno()
	{
		String formatDate="yyyyMMddHHmmssSSS";
		java.util.Date date=new java.util.Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(formatDate);
		return simpleDateFormat.format(date)+genRandomNum(6);
	}
	
	/**
	 * 生成随机数
	 */
    public static String genRandomNum(int len) {
		// 0开始，10个数字
		final int maxNum = 10;
		int i; // 生成的随机数
		int count = 0;
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
    
    /**
	 * 根据日期时间格式获取相应字符串
	 */
    public static synchronized String getCurFormatDate(String formatDate)
	{
		java.util.Date date=new java.util.Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(formatDate);
		return simpleDateFormat.format(date);
	}
    
    /**
     * 将字符串类型变量转换为BigDecimal类型
     */
    public static BigDecimal strToBigDecimalFormat(String str,int count,int roundInt) {
    	
    	BigDecimal strToBig=new BigDecimal("0.00");
    	
    	if(notNullOrEmpty(str))
    	{
    		strToBig=new BigDecimal(str).setScale(count, roundInt);
    	}
    	
    	return strToBig;
    }
    
    public static void main(String[] args){
    	String [] map = {"1","2","3"};
    	String sql = "status not in(";
    	String b = new String();
    	for(String m : map){
    		b += m+",";
    	}
    	 
    	sql += b.substring(0, b.length()-1)+")";
    	System.out.println(sql);
    }
    
}

