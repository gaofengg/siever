package com.huanhe_tech.cli.DAO;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.huanhe_tech.cli.reqAndHandler.ReqData;
import com.huanhe_tech.siever.utils.ColorSOP;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import com.sun.source.tree.ContinueTree;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

class JdbcControllerTest {
    @Test
    public void test01() {

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
    public void test04() {
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

    @Test
    public void test07() {
        String str = "235436567";
        System.out.println(Long.parseLong(str));
    }

    @Test
    public void test08() {
        List<Integer> lists = new ArrayList<>();
        lists.add(2);
        lists.add(4);
        lists.add(1);
        lists.add(9);
        lists.add(22);
        lists.add(41);

        continueOUt:
        for (int list : lists) {
           if (list == 1) {
//               System.out.println(list);
               continue continueOUt;
           }
            System.out.println(list);
        }

        lists.forEach(item -> {

        });
    }
}