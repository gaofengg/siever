package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.SymbolsList;
import com.huanhe_tech.cli.controller.MainController;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 校验 symbol 表数据的完整性
 * 需更新的数据为零，否则输出数据未更新提示
 * 数据最少是否有60条，如果没有，忽略该 symbol 的计算
 */

public class StrategyOfExtremum {
    private Connection conn = null;
    private QueryRunner qr = new QueryRunner();
    // 从数据库中逐表拿出 symbol 的历史数据，变成流
    public void getHistData() throws SQLException {
        List<Long> conidList = new ArrayList<>();
        conn = IJdbcUtils.getConnection();
        ScalarHandler<Long> shConid = new ScalarHandler<>();
        String sql = "select conid from symbols_list_tbl";
        // 使用MapListHandler 获取 symbols_list_tbl 表数据
        // 使用 BeanListHandler 获取 symbols 表的数据
    }

    /**
     *
     * @param tableName 数据表的名称，与 conid 同名，不用带反引号
     * @return 根据数据表的最近日期判断数据是否更新到最新，如果数据最新，并且数据表中最少存在60条数据，返回 true
     */
    private boolean checkDataAndCount(int tableName, Connection conn){
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
            System.out.println("Last time is no up to date, " + intervalDays + " daily data of " + symbol + "needs to be updated.");
        } else return count >= 60;
        return false;
    }


    // 校验 symbol 表数据的完整性
    // 需更新的数据为零，否则输出数据未更新提示

    // 数据最少是否有60条，如果没有，忽略该 symbol 的计算
}
