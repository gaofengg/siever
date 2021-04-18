package com.huanhe_tech.cli.controller;

import com.huanhe_tech.cli.req.ReqData;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {
    private int weekValue;

    public boolean needUpdate() throws SQLException {
        int i = 0;
        // 用于存放 6 个 symbol 的 conid
        int[] conids = {265598, 272093, 274105, 44475946, 4762, 8894};
        // 用于存放结果集
        ScalarHandler<String> sh = new ScalarHandler<>();
        // 用于存放间隔天数的数组
        String[] dateArray = new String[6];
        // 连接数据库
        Connection conn = IJdbcUtils.getConnection();

        QueryRunner qr = new QueryRunner();

        for (int conid : conids) {
            String sql = "select `time` from " + "'" + conid + "'" + " order by `time` desc limit 0,1";
            String time = qr.query(conn, sql, sh);
            System.out.println("=========" + time);
            dateArray[i] = time;
            i++;
        }

        Arrays.sort(dateArray);
        // 获得最小间隔天数
        int minIntervalDays = intervalDays(dateArray[5]);
        System.out.println("需要获取" + minIntervalDays + "天数据。");

        if (minIntervalDays == 0) {
            return true;
        } else {
            ReqData.INSTANCE.setIntervalDays(minIntervalDays);
            return false;
        }
    }

    /**
     * 代入日期字符串，字符串转换成 LocalDate，计算代入的日期与现在日期的天数间隔，并返回
     *
     * @param dateStr 日期字符串
     * @return 返回代入的日期与现在日期的天数间隔
     */
    private int intervalDays(String dateStr) {
        List<LocalDate> daysList = new ArrayList<>();

        String date = dateStr.substring(0, dateStr.indexOf(" "));
        LocalDate lastDate = LocalDate.parse(date);
        LocalDate nowDate = LocalDate.now(ZoneId.of("GMT-4"));
        weekValue = lastDate.getDayOfWeek().getValue();

        // 获取其实日期到截止日期之间的工作日数
        int intervalDays = nowDate.compareTo(lastDate);
        for (int i = 1; i < intervalDays; i++) {
            LocalDate nextDate = lastDate.plusDays(i);
            if (nextDate.getDayOfWeek().getValue() != 6 && nextDate.getDayOfWeek().getValue() != 7) {
                daysList.add(nextDate);
            }
        }

        return daysList.size();
    }
}
