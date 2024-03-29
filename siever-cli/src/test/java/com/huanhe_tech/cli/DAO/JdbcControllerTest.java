package com.huanhe_tech.cli.DAO;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.huanhe_tech.cli.beans.EndOfHistBeanQueue;
import com.huanhe_tech.siever.utils.*;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;
import com.sun.source.tree.ContinueTree;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collector;

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

    @Test
    public void test13() {
        String str1 = "2021-05-17 00:00:00";
        String str2 = "2021-05-10 00:00:00";

    }

    @Test
    public void test14() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));
        List<Integer> list1 = new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10));
        list.forEach(System.out::println);
        System.out.println(isOrdered(list));
        mergeLists(list, list1).forEach(System.out::println);
    }

    public boolean isOrdered(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i + 1)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public List<Integer> mergeLists(List<Integer> list, List<Integer> list1) {
        List<Integer> mergedList = new ArrayList<>();
        int count = 0;
        int count1 = 0;
        for (int i = 0; i < list.size() + list1.size(); i++) {
            if (i % 2 == 0) {
                mergedList.add(list.get(count));
                count++;
            } else {
                mergedList.add(list1.get(count1));
                count1++;
            }
        }
        return mergedList;
    }

    @Test
    public void test11() {
        double a = 12.45633455;
        System.out.println(DoubleDecimalDigits.transition(2, a));
    }

    @Test
    public void test23() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 5, 3, 4, 3, 8, 10, 6, 9));
        System.out.println(list.get(2) + " " + list.get(7));
        System.out.println(list.indexOf(5));

    }

    @Test
    public void test24() {

    }

    class MyHistDataHandler implements ApiController.IHistoricalDataHandler {

        @Override
        public void historicalData(Bar bar) {

        }

        @Override
        public void historicalDataEnd() {
        }
    }

    @Test
    public void test25() {
        String str = "DLNG PRA";
        String str1 = str.replace(" ", "_");
        String str11 = str1.substring(0, str1.length() - 2);
        String str12 = str1.substring(str1.length() - 2, str1.length() - 1);
        String str13 = str11.concat(str12);
        System.out.println(str13);

    }

    @Test
    public void test26() {
        String str = "20.397B";
        String num = str.substring(0, str.length() - 1);
        long l;
        String unit = str.substring(str.length() - 1);
        switch (unit) {
            case "M":
                l = 1_000_000L;
                break;
            case "B":
                l = 1_000_000_000L;
                break;
            case "T":
                l = 1_000_000_000_000L;
                break;
            default:
                l = 0;
        }
        double d = Double.parseDouble(num);
        System.out.println(d);
        BigDecimal bigDecimal = new BigDecimal(d);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal(l));
        BigDecimal divide = multiply.divide(new BigDecimal(1_000_000_000), 4, RoundingMode.HALF_UP);
        System.out.println(bigDecimal);
        System.out.println(divide);

    }

    @Test
    void test27() {
        int i = new IntervalDaysCalc().intervalDays("2021-05-27 00:00:00");
        System.out.println(i);

    }


    @Test
    void test29() {
        LLoger.logger.warn(getClass().toString());
    }

    @Test
    void test30() {
        List<Integer> list = new ArrayList<>(Arrays.asList(8, 5, 9, 7, 6));
        double[] doubles = list.stream().mapToDouble(x -> x).toArray();
        double evaluate = new Variance().evaluate(doubles);
        System.out.println(evaluate);


    }

    @Test
    void test31() {
        double[] array = {1.9, 4.3, 2.2, 6.5, 1.1};
        List<Double> list = new ArrayList<>();
        for (double v : array) {
            list.add(v);
        }
//        list.remove(0);
//        list.add(0, 5.5);

        list.forEach(System.out::println);
    }

    @Test
    void test32() {
        List<String> list = Arrays.asList("2021-07-08 00:00:00", "2021-06-21 00:00:00");
        List<ZonedDateTime> zdt = new ArrayList<>();
        for (String str : list) {
            ZonedDateTime z = StrToZonedDateTime.newYork(str);
            zdt.add(z);
        }
        for (int i = 1; i < zdt.size(); i++) {
            long between = Duration.between(zdt.get(i - 1), zdt.get(i)).abs().toDays();
            System.out.println(between);
        }
    }

    @Test
    void test33() {
        System.out.println(test34());
    }


    boolean test34() {
        List<Integer> list = Arrays.asList(3, 5, 1, 6, 9);
        list.stream().limit(10).forEach(System.out::println);
        return list.stream().noneMatch(l -> l % 2 == 0);
    }

}