package com.huanhe_tech.cli.DAO;

//import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcControllerTest {
    @Test
    public void test01() {
        System.out.println("test01");
        try {
            DruidDataSourceFactory.createDataSource(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02(){
        Connection conn = null;
        try {
            conn = IJdbcUtils.getConnection();
            QueryRunner qr = new QueryRunner();
            String sql = "insert into symbols_list_tbl (conid) values(?)";
            int updateCount = qr.update(conn, sql, 9355448);
            System.out.println("插入了" + updateCount + "条数据。");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            IJdbcUtils.closeResource(conn, null);
        }
    }

    @Test
    public void test03() {
        System.out.println(System.getProperty("user.dir"));
    }
}