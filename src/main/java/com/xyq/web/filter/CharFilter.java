package com.xyq.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器解决全栈乱码问题
 */
@WebFilter("/*")
public class CharFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将父接口转换为子接口,为了获取请求方法这个功能
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String method = request.getMethod();//Post/Get
        //解决Post或Get请求中文数据乱码问题
        if("Post".equalsIgnoreCase(method) || "Get".equalsIgnoreCase(method)) {
            request.setCharacterEncoding("utf-8");
            //处理响应乱码
            response.setContentType("text/html;charset=utf-8");
        }
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
