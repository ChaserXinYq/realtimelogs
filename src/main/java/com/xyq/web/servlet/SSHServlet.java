package com.xyq.web.servlet;

import com.trilead.ssh2.Connection;
import com.xyq.web.domain.ConnAndPath;
import com.xyq.web.domain.ResultInfo;
import com.xyq.web.domain.ServiceDetail;
import com.xyq.web.service.SSHService;
import com.xyq.web.service.impl.SSHServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;

@WebServlet("/ssh/*")
public class SSHServlet extends HttpServlet {

    private SSHService sshService = new SSHServiceImpl();

    /**
     * 根据ip进行ssh连接
     *
     * @param ip
     * @return 返回一个封装了Connection连接和此连接中保存了日志的路径对象
     */
    public ConnAndPath SSH(String ip, String service) {//ip加服务名确定数据库唯一一条记录
        ServiceDetail serviceDeatil = sshService.getServiceDeatil(ip, service);

        //ssh连接
        Connection conn = new Connection(serviceDeatil.getIp(), serviceDeatil.getPort());
        try {
            conn.connect();
            //远程服务器的用户名和密码
            boolean b = conn.authenticateWithPassword(serviceDeatil.getUsername(), serviceDeatil.getPassword());
            if (!b) {
                throw new IOException("身份验证失败，Username or Password is error");
            } else {
                ConnAndPath connAndPath = new ConnAndPath();
                connAndPath.setConn(conn);
                connAndPath.setPath(serviceDeatil.getDirectory());
                return connAndPath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}




