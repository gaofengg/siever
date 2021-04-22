package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.controller.MainController;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 校验 symbol 表数据的完整性
 * 需更新的数据为零，否则输出数据未更新提示
 * 数据最少是否有60条，如果没有，忽略该 symbol 的计算
 */

public class StrategySource {
    private final QueryRunner qr = new QueryRunner();

    // 从数据库中逐表拿出 symbol 的历史数据，变成流
    public void getHistData() throws SQLException {
        Connection conn = IJdbcUtils.getConnection();
        String sql = "select id, conid from symbols_list_tbl limit 2";
        MapListHandler mlh = new MapListHandler();

        List<Map<String, Object>> idAndConidMapList = qr.query(conn, sql, mlh);
        idAndConidMapList.forEach(item -> {
            int intervalDays;
            String conid = item.get("conid").toString();
            String sql_hist = "select * from " + "'" + conid + "'";
            BeanListHandler<BeanOfHistData> blh = new BeanListHandler<>(BeanOfHistData.class);
            try {
                List<BeanOfHistData> histBeanList = qr.query(conn, sql_hist, blh);
                if (histBeanList.size() >= 60) {
//                    System.out.println(histBeanList.size()); // 数据条数
                    histBeanList.sort(Comparator.comparing(BeanOfHistData::getTime));
                    String time = histBeanList.get(histBeanList.size() - 1).getTime();
//                    System.out.println(time); // 最近日期
                    intervalDays = new MainController().intervalDays(time);
                    if (intervalDays == 0) {
                        System.out.println("无需更新数据");
                    } else {
                        System.out.println("The most recent date is " + time); // 最近日期
                        System.out.println("TARGET CALCULATION IGNORED! " + intervalDays + " daily data of " + histBeanList.get(1).getSymbol() + "needs to be updated.");
                    }

                } else {
                    System.out.println("Insufficient target symbol data。");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

    }

    /**
     * @param tableName 数据表的名称，与 conid 同名，不用带反引号
     * @return 根据数据表的最近日期判断数据是否更新到最新，如果数据最新，并且数据表中最少存在60条数据，返回 true
     */
    private boolean checkDataAndCount(String tableName, Connection conn) {
        String symbol = null;
        int count = 0;
        int intervalDays = 0;
        try {
            ScalarHandler<String> shTime = new ScalarHandler<>();
            ScalarHandler<String> shSymbol = new ScalarHandler<>();
            ScalarHandler<String> shCount = new ScalarHandler<>();
            String sql = "select max(`time`) from " + "'" + tableName + "'";
            String lastTime = qr.query(conn, sql, shTime);
            String sql1 = "select symbol from " + "'" + tableName + "'" + "limit 1";
            symbol = qr.query(conn, sql1, shSymbol);
            // 获取数据表中存在多少行数据
            String sql2 = "select count(id) from" + "'" + tableName + "'";
            count = Integer.parseInt(qr.query(conn, sql2, shCount));
            intervalDays = new MainController().intervalDays(lastTime);
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
        }

        if (intervalDays != 0) {
            System.out.println("CALCULATION IGNORED! Last time is no up to date, " + intervalDays + " daily data of " + symbol + "needs to be updated.");
        } else return count >= 60;
        return false;
    }


    // 校验 symbol 表数据的完整性
    // 需更新的数据为零，否则输出数据未更新提示

    // 数据最少是否有60条，如果没有，忽略该 symbol 的计算
}
