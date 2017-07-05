package com.util.common;

import java.io.IOException;

import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frames.base.BaseController;

/**
 * 功能描述： 登陆拦截器 .  <BR>
 */
public class SessionFilter extends BaseController implements Filter{
	Logger log = LoggerFactory.getLogger(SessionFilter.class);
	private SessionFilterMode sfm = new SessionFilterMode();

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse  httpres = (HttpServletResponse) response;
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(
				(HttpServletResponse) response);

		if (this.sfm.getIffilter().toUpperCase().equals("N")) {
			chain.doFilter(request, response);
			return;
		}
		
		if (checkIfNeedfilter(httpreq.getRequestURI())) {
			Object o = httpreq.getSession().getAttribute(
					this.sfm.getSession_parameter());
			if (o == null) {
				wrapper.setStatus(5008);
				if ((httpreq.getHeader("x-requested-with") != null)&& ("XMLHttpRequest".equals(httpreq.getHeader("x-requested-with")))) {
					printHttpHeader(httpreq);
					ResponseMessage responseMessage = new ResponseMessage(
							ResponseLevel.FATAL,
							"用户会话过期");
					responseMessage.setArgs(new String[] { httpreq
							.getContextPath() + this.sfm.getRedirect_url() });
					httpres.setHeader("sessionstatus", "会话过期");
				} else {
					this.log.info("****** This is a common Request **********************");
					
					wrapper.sendRedirect(httpreq.getContextPath());
				}
			}
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fc)
	    throws ServletException
	{
		this.sfm.setFilter_exception(fc.getInitParameter("filter_exception"));
		this.sfm.setFilter_content(fc.getInitParameter("filter_content"));
		this.sfm.setIffilter(fc.getInitParameter("iffilter"));
		this.sfm.setRedirect_url(fc.getInitParameter("redirect_url"));
		this.sfm.setSession_parameter(fc.getInitParameter("session_parameter"));
	}
	
	 /**
	 * 方法说明：是否需要过滤器 . <BR>
	 */
	private boolean checkIfNeedfilter(String url)
	  {
	    String[] inc = this.sfm.getFilter_exception().split(";");
	    for (int i = 0; i < inc.length; i++) {
	      if (url.indexOf(inc[i]) >= 0) {
	        return false;
	      }
	    }
	    inc = this.sfm.getFilter_content().split(";");
	    for (int i = 0; i < inc.length; i++) {
	      if (url.indexOf(inc[i]) >= 0) {
	        return true;
	      }
	    }
	    return false;
	  }

	  /**
	 * 方法说明： 打印头. <BR>
	 */
	private void printHttpHeader(HttpServletRequest httpreq) {
	    this.log.info("UrlFilter check URL：" + httpreq.getRequestURI() + " **** this session id is ：" + httpreq.getSession().getId());
	    Enumeration en = httpreq.getHeaderNames();
	    Object hname = null;
	    this.log.info("*************printHttpHeader start***************");
	    while (en.hasMoreElements()) {
	      hname = en.nextElement();
	      this.log.info(" Request Head " + hname.toString() + " = " + httpreq.getHeader(hname.toString()));
	    }
	    this.log.info("**************printHttpHeader end**************");
	  }
	
	public void destroy() {
	    this.sfm = null;
	    this.log = null;
	  }
}
