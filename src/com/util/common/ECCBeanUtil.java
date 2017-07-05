package com.util.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;


public  class ECCBeanUtil {
    /**
     * 方法说明： 将一个 JavaBean 对象转化为一个  Map . <BR>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map BeanToMap(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
  
    /**
     * 方法说明：将一个 Map 对象转化为一个 JavaBean . <BR>
     */
    @SuppressWarnings("rawtypes")
	public static Object mapToBean(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                if(value!=null&&value.getClass().equals(BigDecimal.class)){
                	BigDecimal v=(BigDecimal)value;
                	int b=v.intValue();
                	 Object[] args = new Object[1];
                     args[0] = b;

                     descriptor.getWriteMethod().invoke(obj, args);
                }else{
                	 Object[] args = new Object[1];
                     args[0] = value;
                     descriptor.getWriteMethod().invoke(obj, args);
                }
               
            }
        }
        return obj;
    }
    /**
     * 方法说明： 将ResultSet 转为javaBean. <BR>
     */ 
    public static Object resultSetToBean(ResultSet rs, Object obj) throws Exception {  
        Method[] methods = obj.getClass().getMethods();   
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsCount = rsmd.getColumnCount();   
        String[] columnNames = new String[columnsCount];   
        for (int i = 0; i < columnsCount; i++) {    
            columnNames[i] = rsmd.getColumnLabel(i + 1);   
        }    
            for (int i = 0; i < columnNames.length; i++) {   
                String setMethodName = "set" + columnNames[i];   
                for (int j = 0; j < methods.length; j++) {
                	if(setMethodName.equalsIgnoreCase("setLoginCount")){
                		
                	}
                    if (methods[j].getName().equalsIgnoreCase(setMethodName)) {   
                        setMethodName = methods[j].getName();   
                        Object value = rs.getObject(columnNames[i]);  
                        if(value==null){
                        	value="";
                        } 
                        try {   
                        	if(value instanceof BigDecimal){
                        		value=((BigDecimal) value).floatValue();
                        	}
                            Method setMethod = obj.getClass().getMethod(setMethodName, value.getClass());   
                            setMethod.invoke(obj, value);   
                        } catch (Exception e) {    
                            Method setMethod = obj.getClass().getMethod(setMethodName, String.class);   
                            setMethod.invoke(obj, value.toString());   
                        }   
                    }   
                }   
            }   
        return obj;   
    }   
}
