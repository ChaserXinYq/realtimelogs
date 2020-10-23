package com.xyq.web.servlet;




import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 通过访问路径执行servlet中的不同方法（方法分发）
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();
        //获取请求路径下的方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        try {
            //获取方法对象
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 简化代码，将序列化代码抽取出来
     * 将传入的对象序列化为json，并且写回客户端
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValue(Object obj, HttpServletResponse response){
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
