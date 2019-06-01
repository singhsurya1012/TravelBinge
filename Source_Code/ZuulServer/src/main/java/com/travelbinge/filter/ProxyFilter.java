package com.travelbinge.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ProxyFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		System.out.println("Inside Pre Zuul Filter");
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String username = null!=SecurityContextHolder.getContext().getAuthentication() ? (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal():null;
		
		System.out.println(username);
		
		if(null!=username) {
			ctx.addZuulRequestHeader("username", username);
		}
		
		System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1; // run before PreDecoration
	}



}
