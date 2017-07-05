/**
 * Copyright (C), 2009-2012, 北京华热科技发展有限公司.<BR>
 * ProjectName:ECC<BR>
 * File name:  Ecclog.java     <BR>
 * Author: liuzijian  <BR>
 * Project:ECC    <BR>
 * Version: v 1.0      <BR>
 * Date: 2014年12月22日 上午10:06:02 <BR>
 * Description:     <BR>
 * Function List:  <BR>
 */ 

package com.dispatch.sys.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD})     
@Retention (RetentionPolicy.RUNTIME)     
@Documented 
public  @interface  Ecclog {
    /**    
     * 操作名称    
     * @return 用户操作名称，默认为空串    
     */      
    String value() default   "" ;
    /**    
     * 操作类型    
     * @return 用户操作名称，默认为空串    
     */
    String type() default "";
    /**    
     * 功能模块key  
     * @return 用户操作名称，默认为空串    
     */
    String key() default "";
}
