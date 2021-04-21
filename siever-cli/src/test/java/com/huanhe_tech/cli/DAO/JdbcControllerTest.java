package com.huanhe_tech.cli.DAO;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.huanhe_tech.cli.req.ReqData;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

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

    @Test
    public void test04() throws ParseException {
        ZonedDateTime zdt = Instant.now().atZone(ZoneId.of("GMT-4"));
        LocalDate lastDate = LocalDate.parse("2021-04-16");
        LocalDate nowDate = LocalDate.now(ZoneId.of("GMT-4"));
        int i = lastDate.compareTo(nowDate);
        System.out.println(i);

    }

    @Test
    public void test05() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT-4"));
        System.out.println("now: " + now);
//        LocalDate tomorrow = now.plusDays(1);
//        System.out.println(tomorrow);
        System.out.println(now.getDayOfWeek().getValue());

    }

    @Test
    public void test06() {
        int intervalDays = ReqData.INSTANCE.getIntervalDays();
        System.out.println(intervalDays);
    }
}