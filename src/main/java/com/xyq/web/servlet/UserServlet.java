package com.xyq.web.servlet;

import com.xyq.web.domain.ResultInfo;
import com.xyq.web.domain.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    /**
     * 功能：登录验证
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //账号密码放在配置文件中,加载配置文件进内存
        Properties pro = new Properties();
        InputStream is = UserServlet.class.getClassLoader().getResourceAsStream("account.properties");
        pro.load(is);
        //获取用户名和密码
        String username = pro.getProperty("username");
        String password = pro.getProperty("password");
        String usernameInput = request.getParameter("username");
        String passwordInput = request.getParameter("password");
        ResultInfo info = new ResultInfo();
        if(usernameInput == null || passwordInput == null || usernameInput.length() == 0 || passwordInput.length() ==0) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码不能为空！");

        } else if(username.equals(usernameInput) && password.equals(passwordInput)) {
            info.setFlag(true);
            User user = new User();
            user.setUsername(usernameInput);
            user.setPassword(passwordInput);
            request.getSession().setAttribute("user",user);
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        writeValue(info,response);
    }

}
