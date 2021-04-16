package com.huanhe_tech.siever.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRetrieve {
    public static <T> List<T> retrieveList(Class<T> clazz, Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            // 获取结果集中的元数据
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            try {
                while (rs.next()) {
                    T t = clazz.getDeclaredConstructor().newInstance();
                    for (int i = 0; i < columnCount; i++) {
                        // 从结果集中获取列值
                        Object columnValue = rs.getObject(i + 1);
                        // 从结果集的元数据中获取列名
                        String columnLabel = rsMetaData.getColumnLabel(i + 1);
                        Field field = clazz.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(t, columnValue);
                    }
                    list.add(t);
                }
            } catch
            (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException
                            e) {
                e.printStackTrace();
            }
            return list;
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            IJdbcUtils.closeResource(null, ps);
        }
        return null;
    }
}
