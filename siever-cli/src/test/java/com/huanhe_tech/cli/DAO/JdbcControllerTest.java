package com.huanhe_tech.cli.DAO;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.huanhe_tech.cli.beans.EndOfHistBeanQueue;
import com.huanhe_tech.cli.reqAndHandler.ReqData;
import com.huanhe_tech.siever.utils.ColorSOP;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import com.huanhe_tech.siever.utils.IntervalDaysCalc;
import com.huanhe_tech.siever.utils.StrToZonedDateTime;
import com.sun.source.tree.ContinueTree;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.TableHeaderUI;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class JdbcControllerTest {
    @Test
    public void test01() {

    }

    @Test
    public void test02() {
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

    @Test
    public void test09() {
        System.out.println(StrToZonedDateTime.newYork("2021-05-07 00:00:00"));
        String dateStr = "2021-05-07 00:00:00";
        dateStr = dateStr.replace(" ", "T");
        String parseZonedDateStr = dateStr + "-04:00[GMT-04:00]";
        ZonedDateTime lastTimeInDatabase = ZonedDateTime.parse(parseZonedDateStr);
        System.out.println(lastTimeInDatabase);
        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("GMT-4"));
        System.out.println("------------" + nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z")));
        System.out.println(nowTime.compareTo(lastTimeInDatabase));
        System.out.println(Duration.between(nowTime, lastTimeInDatabase).abs().toDays());


        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("GMT-4")); // 纽约但钱时间
        System.out.println(zonedDateTime);
        // 数据库中最近时间，转换成 ZonedDateTime


//        ZonedDateTime.parse("")
        // 比较两个 ZoneDateTime

// Default pattern
        ZonedDateTime today = ZonedDateTime.parse("2021-05-10T22:49:23.531598700-04:00[GMT-04:00]");
        System.out.println(today);

// Custom pattern

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");
//        ZonedDateTime dateTime = ZonedDateTime.parse("2019-03-27 10:15:30 -04:00", formatter);
//        System.out.println(dateTime);

    }

    @Test
    public void Test10() {
        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("GMT-4"));
        // 获取当天下午6点的时间点
        // 需求说明：如果时间过了当天的下午6点，便将当前时间改成后一天的 00:00:00
        // 作用：纽约时间的当天下午6点已收盘，当天的历史数据应该被获取。
        ZonedDateTime todayPoint = StrToZonedDateTime.newYork(nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 18:00:00");
        System.out.println(nowTime.compareTo(todayPoint));
    }

    @Test
    public void Test11() {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            System.out.println("t1 started");
            Thread t2 = new Thread(() -> {
                try {
                    if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            System.out.println("t2 started");
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("t1 waited 5 second");

                        } finally {
                            lock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "thread-son");
            t2.start();

        }, "Thread-parent");
        t1.start();

        lock.lock();
        System.out.println("the main thread continue.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test12() {
        System.out.println(new EndOfHistBeanQueue().getList());
    }
}