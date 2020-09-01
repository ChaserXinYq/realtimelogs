package com.xyq.web.servlet;

import com.trilead.ssh2.*;
import com.xyq.web.domain.ConnAndPath;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

@WebServlet("/log/*")
public class LogServlet extends BaseServlet {

    /**
     * 功能：通过服务名称+ip获取对应的log文件名称列表
     *
     * @param req
     * @param resp
     */
    public void getLogsList(HttpServletRequest req, HttpServletResponse resp) {
        String ip = req.getParameter("ip");
        String serviceName = req.getParameter("service");
        SSHServlet sshServlet = new SSHServlet();
        ConnAndPath connAndPath = sshServlet.SSH(ip);
        //建立一个SFTP客户端
        SFTPv3Client sftPv3Client = null;
        try {
            sftPv3Client = new SFTPv3Client(connAndPath.getConn());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //列出给定目录下的所有文件，并判断要展示的文件名称（日志）
        //指定的目录
        String p = connAndPath.getPath();
        ArrayList<String> list = new ArrayList<>();
        String path = p + "/" + serviceName;
        Vector vector = null;
        try {
            if (sftPv3Client != null) {
                vector = sftPv3Client.ls(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (vector != null) {
            for (int i = 0; i < vector.size(); i++) {
                SFTPv3DirectoryEntry sftPv3DirectoryEntry = new SFTPv3DirectoryEntry();
                sftPv3DirectoryEntry = (SFTPv3DirectoryEntry) vector.get(i);
                //文件名
                String filename = sftPv3DirectoryEntry.filename;
                list.add(filename);
            }
        }
        //最后将list转换为json，写回前端
            writeValue(list,resp);
    }

    /**
     * 通过wc -l -日志名称获取要读日志的行数
     * @return
     */
    public int countLogLines(Connection conn, String filename) {
        InputStream is = null;
        BufferedReader br = null;
        Session session = null;
        int countLogLines = 0;
        try {
            session = conn.openSession();
            session.execCommand("wc -l" +" "+ filename);
            is = new StreamGobbler(session.getStdout());
            br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            countLogLines = Integer.parseInt(s.split(" ")[0]);
            System.out.println(countLogLines);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
                if(is != null) {
                    is.close();
                }
                if(session != null) {
                    session.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return countLogLines;
    }

    /**
     * 功能：通过服务名 + IP + 日志名称获取整个日志内容
     *
     * @param req
     * @param resp 第一版本：目前是再次连接。可以进行优化，将选择服务时进行的连接直接拿来用，暂不优化
     */
    public void viewLog(HttpServletRequest req, HttpServletResponse resp) {
        //获取前端传过来的ip,serviceName,logName,第几页
        String ip = req.getParameter("ip");
        String service= req.getParameter("service");
        String logName = req.getParameter("logName");
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        SSHServlet sshServlet = new SSHServlet();
        //获取连接后的对象connAndPath
        ConnAndPath connAndPath = sshServlet.SSH(ip);
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        Connection conn = null;
        SFTPv3Client sftPv3Client = null;
        //远程打开文件，可以进行读
        try {
            //建立一个SFTP客户端
            conn = connAndPath.getConn();
            sftPv3Client = new SFTPv3Client(conn);
            String p = connAndPath.getPath();
            String filename = p + "/" + service + "/" + logName;
            sftPv3Client.openFileRO(filename);
            is = sftPv3Client.read(filename);
            reader = new InputStreamReader(is);
            br = new BufferedReader(reader);
            //调用countLogLines获取要读取日志文件的总行数,用来分页
            int countLogLines = countLogLines(conn, filename);
            //总页数
            int pageCount = (countLogLines / 1000) + 1;
            String s = "";
            //int count = (pageNum - 1) * 1000;
            StringBuilder string = new StringBuilder();
            /*Date dateBegin = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = dateFormat.format(dateBegin);
            System.out.println(format);*/
            for (int i = 0; i < 1000; i++) {
                s = br.readLine();
                string.append(s == null ? "" : s).append("</br>");
            }
            /*Date dateEnd = new Date();
            String format1 = dateFormat.format(dateEnd);
            System.out.println(format1);*/
            //最后将String转换为json，写回前端
            writeValue(string,resp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
                if(reader != null) {
                    reader.close();
                }
                if(is != null) {
                    is.close();
                }
                if(conn != null) {
                    conn.close();
                }
                if(sftPv3Client != null) {
                    sftPv3Client.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
