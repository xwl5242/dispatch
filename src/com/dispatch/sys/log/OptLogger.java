package com.dispatch.sys.log;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.dispatch.sys.bean.Operatelog;
import com.dispatch.sys.bean.User;
import com.dispatch.sys.log.service.OperatelogService;
import com.util.common.UUIDGenerator;

@Aspect 
public class OptLogger { 
	@Autowired
	public OperatelogService operatelogService;
	
	@Around(" execution(* com.dispatch.*.*.controller..*(..))")   
    public Object loggerOpt(ProceedingJoinPoint pjp ) throws Throwable {   
	 	Object retVal = pjp.proceed(); 
	 	Operatelog operatelog=new Operatelog();
	 	Object[] args=pjp.getArgs();
	 	HttpServletRequest request=(HttpServletRequest)args[0];
	 	MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
	    Method method = methodSignature.getMethod();
        String methodName = pjp.getSignature().getName();   
        String clazzName = pjp.getTarget().getClass().getSimpleName();   
        method.getAnnotation(Ecclog.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        User u2=(User)request.getSession().getAttribute("ECCUSER");
        String uri = request.getRequestURI();//返回请求行中的资源名称
        String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
        String ip = request.getRemoteAddr();//返回发出请求的IP地址
        String host=request.getRemoteHost();//返回发出请求的客户机的主机名
        int port =request.getRemotePort();//返回发出请求的客户机的端口号。
        operatelog.setId(UUIDGenerator.getUUID());
        operatelog.setClassName(clazzName);
        operatelog.setMethodName(methodName);
        operatelog.setOptHost(host);
        operatelog.setOptIp(ip);
        operatelog.setOptPort(String.valueOf(port));
        operatelog.setOptRemark(method.getAnnotation(Ecclog.class)==null?"":method.getAnnotation(Ecclog.class).value());
        operatelog.setOptkey(method.getAnnotation(Ecclog.class)==null?"":method.getAnnotation(Ecclog.class).key());
        operatelog.setOpttype(method.getAnnotation(Ecclog.class)==null?"":method.getAnnotation(Ecclog.class).type());
        operatelog.setOptUri(uri);
        operatelog.setOptUrl(url);
        operatelog.setOrtTime(df.format(new Date()));
        operatelog.setUserLoginName(u2==null?"":u2.getLoginName());
        operatelogService.saveOperateLog(operatelog);
        return retVal;    
    }

	public OperatelogService getOperatelogService() {
		return operatelogService;
	}

	public void setOperatelogService(OperatelogService operatelogService) {
		this.operatelogService = operatelogService;
	}
	  
}
