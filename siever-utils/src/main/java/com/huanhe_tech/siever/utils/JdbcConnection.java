package com.huanhe_tech.siever.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
    /**
     *  链接数据库的方法
     * @return con 返回一个 Connection
     */
    public static Connection jdbcConnect() {
        Configurations configs = new Configurations();
        Configuration config = null;
        Connection con = null;

        // 读取配置文件
        try {
            config = configs.properties(new File(new LoadReSrc("resources", "conf/jdbc_connection_conf.properties").getUri()));
        } catch (ConfigurationException e) {
            e.printStackTrace();
            System.out.println("配置文件加载失败。");
        }

        if (config != null) {
            // 加载驱动
            try {
                Class.forName(config.getString("sqlite_driver"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("未找到相关配置项。");
            }

            // 获取链接
            try {
                con = DriverManager.getConnection(config.getString("url"));
                System.out.println(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("未找到相关数据库。");
            }
        } else {
            System.out.println("配置文件读取失败。");
        }

        return con;

    }

    /**
     *  断开数据库资源连接
     * @param con Connection
     * @param statement PreparedStatement
     */
    public static void jdbcClose(Connection con, Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
