package com.sumCo.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author oplus
 * @Description: TODO(XSS過濾)
 * @date 2017-6-23 15:07
 */
public class XssFilter implements Filter {


	private String exclude;

	public XssFilter(String exclude) {
		this.exclude = exclude;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri=httpRequest.getRequestURI();
		if(uri.indexOf(".") == -1 && !uri.contains(exclude)){
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}