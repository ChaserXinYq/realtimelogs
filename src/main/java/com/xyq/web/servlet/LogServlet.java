package com.xyq.web.servlet;

/**
 * @author：xinyingquan
 * @WriteTime:2020-9-1
 */

import com.trilead.ssh2.*;
import com.xyq.web.domain.*;
import com.xyq.web.service.LogService;
import com.xyq.web.service.impl.LogServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@WebServlet("/log/*")
public class LogServlet extends BaseServlet {

    private LogService logService = new LogServiceImpl();

    /**
     * 功能：动态展示环境选择页面。获取所有环境名称
     */
    public void getEnvironmentList(HttpServletRequest req, HttpServletResponse resp) {
        List<EnvironmentName> environmentNameList = logService.getEnvironmentName();
        writeValue(environmentNameList, resp);
    }

    public void getServiceList(HttpServletRequest req, HttpServletResponse resp) {
        String environmentName = req.getParameter("environmentName");
        List<ServiceDetail> serviceDetailList = logService.getServiceDetail(environmentName);
        writeValue(serviceDetailList, resp);
    }

    /**
     * 功能：根据环境名分页展示获取所有对应更改环境名的所有信息
     */
    public void getServiceUpdateList(HttpServletRequest req, HttpServletResponse resp) {
        ServiceDetailListAndPageShow serviceDetailListAndPageShow = new ServiceDetailListAndPageShow();
        String environmentName = req.getParameter("environmentName");
        String currentPage = req.getParameter("currentPage");
        List<ServiceDetail> serviceDetailList = logService.getServiceUpdateDetail(environmentName, Integer.parseInt(currentPage));
        int rows = logService.countServiceDetail(environmentName);
        int pageCount = rows % 20 == 0 ? rows / 20 : rows /20 + 1;
        serviceDetailListAndPageShow.setList(serviceDetailList);
        serviceDetailListAndPageShow.setCurrentPage(Integer.parseInt(currentPage));
        serviceDetailListAndPageShow.setPageCount(pageCount);
        writeValue(serviceDetailListAndPageShow, resp);
    }

    /**
     * 功能：展示所有服务信息，用于前端修改
     */
    public void getAllServiceDetail(HttpServletRequest req, HttpServletResponse resp) {
        List<ServiceDetail> allServiceDetailList = logService.getAllServiceDetail();
        writeValue(allServiceDetailList, resp);
    }

    /**
     * 根据serviceName + ip修改服务信息
     */
    public void updateServiceDetail(HttpServletRequest req, HttpServletResponse resp) {
        String preServiceName = req.getParameter("preServiceName");
        String preIp = req.getParameter("preIp");
        String serviceName = req.getParameter("serviceName");
        String ip = req.getParameter("ip");
        String port = req.getParameter("port");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String directory = req.getParameter("directory");
        String environmentName = req.getParameter("environmentName");
        ServiceDetail service = new ServiceDetail();
        service.setServiceName(serviceName);
        service.setIp(ip);
        service.setPort(Integer.parseInt(port));
        service.setUsername(username);
        service.setPassword(password);
        service.setDirectory(directory);
        service.setEnvironmentName(environmentName);
        System.out.println(preServiceName);
        System.out.println(preIp);
        System.out.println(service);
        int rows = logService.updateServiceDetail(preServiceName, preIp, service);
        writeValue(rows, resp);
    }

    /**
     * 增加服务信息
     */
    public void insertServiceDetail(HttpServletRequest req, HttpServletResponse resp) {
        String serviceName = req.getParameter("serviceName");
        String ip = req.getParameter("ip");
        String port = req.getParameter("port");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String directory = req.getParameter("directory");
        String environmentName = req.getParameter("environmentName");
        ServiceDetail serviceDetail = new ServiceDetail();
        serviceDetail.setServiceName(serviceName);
        serviceDetail.setIp(ip);
        serviceDetail.setPort(Integer.parseInt(port));
        serviceDetail.setUsername(username);
        serviceDetail.setPassword(password);
        serviceDetail.setDirectory(directory);
        serviceDetail.setEnvironmentName(environmentName);
        int rows = logService.insertServiceDetail(serviceDetail);
        writeValue(rows, resp);
    }

    /**
     * 删除服务信息
     */
    public void deleteServiceDetail(HttpServletRequest req, HttpServletResponse resp) {
        String serviceName = req.getParameter("serviceName");
        String ip = req.getParameter("ip");
        int rows = logService.deleteServiceDetail(serviceName, ip);
        writeValue(rows, resp);
    }

    /**
     * 功能：通过服务名称+ip获取对应的log文件名称列表
     *
     * @param req
     * @param resp
     * @author：xinyingquan
     * @WriteTime:2020-9-1
     */
    public void getLogsList(HttpServletRequest req, HttpServletResponse resp) {
        String ip = req.getParameter("ip");
        String serviceName = req.getParameter("service");
        SSHServlet sshServlet = new SSHServlet();
        ConnAndPath connAndPath = sshServlet.SSH(ip, serviceName);
        ArrayList<String> list = new ArrayList<>();
        //建立一个SFTP客户端
        SFTPv3Client sftPv3Client = null;
        try {
            sftPv3Client = new SFTPv3Client(connAndPath.getConn());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //列出给定目录下的所有文件，并判断要展示的文件名称（日志）
        //指定的目录
        String p = connAndPath.getPath();///data/logs
        String path = p + serviceName;
        Vector vector = null;
        try {
            if (sftPv3Client != null) {
                vector = sftPv3Client.ls(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sftPv3Client != null) {
                sftPv3Client.close();
            }
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
        writeValue(list, resp);
    }

    /**
     * 功能：通过linux命令获取日志总行数，用于分页和读取日志
     *
     * @param conn
     * @param filename
     * @return
     * @author：xinyingquan
     * @WriteTime:2020-9-2
     */
    public int countLogLines(Connection conn, String filename) {
        InputStream is = null;
        InputStreamReader ir = null;
        BufferedReader br = null;
        Session session = null;
        int countLogLines = 0;
        try {
            session = conn.openSession();
            session.execCommand("wc -l" + " " + filename);
            is = new StreamGobbler(session.getStdout());
            ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
            String s = br.readLine();
            countLogLines = Integer.parseInt(s.split(" ")[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ir != null) {
                    ir.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (session != null) {
                session.close();
            }
        }
        return countLogLines;
    }

    /**
     * 功能：通过linux命令输出指定begin-end行日志，并通过io流获取
     *
     * @param conn
     * @param filename
     * @param beginLine
     * @param endLine
     * @return
     * @author：xinyingquan
     * @WriteTime:2020-9-3
     */
    public StringBuilder getLog(Connection conn, String filename, int beginLine, int endLine, int countLogLines) {
        InputStream is = null;
        InputStreamReader ir = null;
        BufferedReader br = null;
        Session session = null;
        StringBuilder log = new StringBuilder();
        try {
            session = conn.openSession();
            //sed -n "起始行,结束行p" 文件路径 | awk '{gsub(/</,"\\&lt");print $0}' | awk '{gsub(/>/,"\\&gt");print $0}'
            session.execCommand("sed -n" + " '" + beginLine + "," + endLine + "p" + "' " + filename + " | awk '{gsub(/</,\"\\\\&lt\");print $0}' | awk '{gsub(/>/,\"\\\\&gt\");print $0}'");
            is = new StreamGobbler(session.getStdout());
            ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
            String s = "";
            for (int i = beginLine; i <= endLine; i++) {
                if (i <= countLogLines) {
                    s = br.readLine();
                    if (s == null) {
                        log.append(" ").append("<br/>");
                    } else {
                        log.append(s).append("<br/>");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ir != null) {
                    ir.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (session != null) {
                session.close();
            }
            conn.close();
        }
        return log;
    }

    /**
     * 功能：通过服务名 + IP + 日志名称 + 页数获取日志内容
     * 读取调用脚本
     *
     * @param req
     * @param resp
     * @author：xinyingquan
     * @WriteTime:2020-9-1
     */
    public void viewLog(HttpServletRequest req, HttpServletResponse resp) {
        //获取前端传过来的ip,serviceName,logName,第几页
        String ip = req.getParameter("ip");
        String service = req.getParameter("service");
        String logName = req.getParameter("logName");
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        SSHServlet sshServlet = new SSHServlet();
        //获取连接后的对象connAndPath
        ConnAndPath connAndPath = sshServlet.SSH(ip, service);
        //创建封装了日志和日志页数的对象，用于写回前端
        LogAndPageCount logAndPageCount = new LogAndPageCount();
        Connection conn = connAndPath.getConn();
        ;
        String p = connAndPath.getPath();
        String filename = p + service + "/" + logName;
        //调用countLogLines获取要读取日志文件的总行数,用来分页
        int countLogLines = countLogLines(conn, filename);
        //总页数
        int pageCount = (countLogLines / 500) + 1;
        //起始行
        int beginLine = (pageNum - 1) * 500 + 1;
        //结束行
        int endLine = beginLine + 499;
        StringBuilder log = getLog(conn, filename, beginLine, endLine, countLogLines);
        logAndPageCount.setLog(log);
        logAndPageCount.setCurrentPage(pageNum);
        logAndPageCount.setPageCount(pageCount);
        //最后将LogAndPageCount转换为json，写回前端
        writeValue(logAndPageCount, resp);
        conn.close();
    }
}

