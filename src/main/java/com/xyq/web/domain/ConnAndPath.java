package com.xyq.web.domain;

import com.trilead.ssh2.Connection;

/**
 * 此类封装了Connection连接对象和此连接IP日志所在路径
 */
public class ConnAndPath {

    private Connection conn;
    private String path;

    @Override
    public String toString() {
        return "ConnAndPath{" +
                "conn=" + conn +
                ", path='" + path + '\'' +
                '}';
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
