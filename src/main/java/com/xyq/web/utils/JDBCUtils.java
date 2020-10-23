package com.xyq.web.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

    private static DataSource ds;

    static {
        //加载配置文件进内存
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            Properties pro = new Properties();
            pro.load(is);
            //初始化连接池,使用配置文件中的参数
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接池
    public static DataSource getDataSource() {
        return ds;
    }

    //获取连接
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
