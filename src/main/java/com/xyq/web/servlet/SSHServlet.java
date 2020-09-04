package com.xyq.web.servlet;

import com.trilead.ssh2.Connection;
import com.xyq.web.domain.ConnAndPath;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;

@WebServlet("/ssh/*")
public class SSHServlet extends HttpServlet {

    /**
     * 根据ip进行ssh连接
     * @param ip
     * @return 返回一个封装了Connection连接和此连接中保存了日志的路径对象
     */
    public ConnAndPath SSH(String ip) {
        //加载配置文件进内存
        InputStreamReader reader = null;
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = SSHServlet.class.getClassLoader().getResourceAsStream("real-time-log");
            reader = new InputStreamReader(is);
            br = new BufferedReader(reader);
            String i = "";
            while ((i = br.readLine()) != null) {
                String[] str = i.split(",");
                //查看ip是否存在
                if (ip.equals(str[1])) {
                    //存在，进行ssh连接
                    Connection conn = new Connection(str[1], Integer.parseInt(str[2]));
                    conn.connect();
                    //远程服务器的用户名和密码
                    boolean b = conn.authenticateWithPassword(str[3], str[4]);
                    if (!b) {
                        throw new IOException("身份验证失败，Username or Password is error");
                    } else {
                        ConnAndPath connAndPath = new ConnAndPath();
                        connAndPath.setConn(conn);
                        connAndPath.setPath(str[5]);
                        return connAndPath;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
