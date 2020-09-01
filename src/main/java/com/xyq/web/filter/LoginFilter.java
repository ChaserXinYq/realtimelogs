package com.xyq.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        // 获得用户请求的URI
        String path = request.getRequestURI();
        //System.out.println(path);
        //登录页面无需过滤
        if (path.contains("index.html") || path.contains("login") || path.contains("js") || path.contains("css")) {
            filterChain.doFilter(request, response);
            return;
        }
        //如果未登录
        if (session.getAttribute("user") == null) {
            response.sendRedirect("index.html");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
