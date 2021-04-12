package com.huanhe_tech.siever.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcCRU {
    /**
     *  通用的增删改工具类
     * @param sql 预编译 sql 语句（带有占位符的 sql 语句）
     * @param args 填充占位符的内容
     */
    public static void CRU(String sql, Object ...args) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            // 获取数据库连接
            con = JdbcConnection.jdbcConnect();
            // 预编译 sql 语句
            ps = con.prepareStatement(sql);
            // 填充 sql 语句中的占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接资源
            JdbcConnection.jdbcClose(con, ps);
        }
    }
}
