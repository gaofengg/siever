package com.huanhe_tech.siever.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcCUD {
    /**
     *  通用的增删改工具类
     * @return 返回一个 int 类型的值，如果操作成功，返回操作对数据库产生影响的项数，
     * 如果操作失败，则返回 0。在设计 clp 应用时，可通过判断该值是否大于 0，来向终端输出是否操作成功的状态日志信息
     * @param conn 从外面传入的连接对象，针对事物需求的业务是，需要在事物完成后再关闭资源，此对象只需关闭 ps 连接
     * @param sql 预编译 sql 语句（带有占位符的 sql 语句）
     * @param args 填充占位符的内容
     */
    public static int CRU(Connection conn, String sql, Object ...args) {
        PreparedStatement ps = null;
        try {
            // 预编译 sql 语句
            ps = conn.prepareStatement(sql);
            // 填充 sql 语句中的占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行
            // 使用 executeUpdate 方法，返回操作成功后对数据库产生影响的行数或列数。
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接资源
            IJdbcUtils.closeResource(null, ps);
        }
        return 0;
    }
}
