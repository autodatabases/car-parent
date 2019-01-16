package com.emate.shop.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class JspFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request0, ServletResponse response0, 
    		FilterChain chain)throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) response0;
		response.sendRedirect("/page/404");
		return;
    }

    @Override
    public void destroy() {
    }
}
