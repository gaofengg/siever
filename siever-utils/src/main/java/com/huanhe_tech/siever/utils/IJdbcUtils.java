package com.huanhe_tech.siever.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class IJdbcUtils {
    private static DataSource ds;
    static {
        try {
            Properties props = PropertiesLoader.getProperties("resources", "conf/jdbc_connection_conf.properties");
            ds = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     *  断开数据库资源连接
     * @param conn Connection
     * @param statement PreparedStatement
     */
    public static void closeResource(Connection conn, Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
