package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.strategies.Strategy;
import com.huanhe_tech.siever.utils.ColorSOP;
import com.huanhe_tech.siever.utils.IntervalDaysCalc;
import com.ib.controller.Bar;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OpHistData {
    public void queryAndApplyStrategy(String conid, String symbol, Connection conn, QueryRunner qr, int histMinSize, Strategy<List<BeanOfHistData>> strategy) {
        String sql_hist = "select * from " + "'" + conid + "'";
        BeanListHandler<BeanOfHistData> blh = new BeanListHandler<>(BeanOfHistData.class);
        List<BeanOfHistData> histBeanList = null;
        // histBeanList 里是每个 conid 表中的数据组，组成的List
        // [HistDataBean{time='2021-01-19 00:00:00', conid= 1715006, id=1, symbol=A, open=...}, HistDataBean{time='2021-01-20 00:00:00', ...}]
        try {
            histBeanList = qr.query(conn, sql_hist, blh);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (histBeanList == null) {
            ColorSOP.e("ERROR: -> " + symbol + " " + conid + "table seems to be null.");
        } else if (noMovement(histBeanList, 30, 2)){
            ColorSOP.w("IGNORE -> " + symbol + " " + conid + " Inactive Market Data");
        } else {
            if (histBeanList.size() >= histMinSize) {
                strategy.run(histBeanList);
            } else {
                ColorSOP.w(symbol + " * -> Insufficient target symbol data. Data size < 30. Calculation is ignored.");
            }
        }
    }

    /**
     * 判断 30 个日历史数据中，是否有死盘的交易日，即开盘价等于收盘价，振幅为零的数据，如果该数据大于指定的数量，返回 false，否则返回 true
     * @param list 一个 symbol 的历史数据
     * @param filterSize 过滤数据的长度，即按照条件查找，最长查找 filterSize条数据
     * @param countNumber 指定符合过滤条件的日历史数据数量
     * @return 返回
     */
    public boolean noMovement (List<BeanOfHistData> list, int filterSize, int countNumber) {
        int count = 0;
        int filterCount = 0;
        boolean flag = false;

        for (BeanOfHistData beanOfHistData : list) {
            if (beanOfHistData.getLow() == beanOfHistData.getHigh() && beanOfHistData.getOpen() == beanOfHistData.getClose()) {
                count++;
            }
            if (count > countNumber) {
                flag = true;
                break;
            } else if (filterCount >= filterSize) {
                break;
            }
        }
        return flag;
    }

    public void createTableAndInsertData(String conid, Connection conn, QueryRunner qr) {
        // 创表
        String sql_create_table = "create table " + "'" + conid + "'" + " ( id integer unique primary key, " +
                "`time` text unique not null, " +
                "conid integer not null, " +
                "symbol text not null, " +
                "open real, " +
                "high real, " +
                "low real, " +
                "close real, " +
                "volume integer, " +
                "wap real, " +
                "count integer)";
        try {
            qr.update(conn, sql_create_table);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        ColorSOP.i(symbol + " -> " + conid + " data table is created. " + insertCount + " days of historical data inserted.");
    }

    public void updateData(String conid, String symbol, Connection conn, QueryRunner qr, Bar bar, int insertCount) {

        String sql_inset_hist = "insert or ignore into " +
                "'" + conid + "'" +
                " (time, conid, symbol, open, high, low, close, volume, wap, count)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            qr.update(conn, sql_inset_hist,
                    bar.formattedTime(),
                    conid,
                    symbol,
                    bar.open(),
                    bar.high(),
                    bar.low(),
                    bar.close(),
                    bar.volume(),
                    bar.wap(),
                    bar.count()
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateData(String conid, String symbol, Connection conn, QueryRunner qr, BeanOfHistData beanOfHistData) {
        String sql_inset_hist = "insert or ignore into " +
                "'" + conid + "'" +
                " (time, conid, symbol, open, high, low, close, volume, wap, count)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            qr.update(conn, sql_inset_hist,
                    beanOfHistData.getTime(),
                    beanOfHistData.getConid(),
                    beanOfHistData.getSymbol(),
                    beanOfHistData.getOpen(),
                    beanOfHistData.getHigh(),
                    beanOfHistData.getLow(),
                    beanOfHistData.getClose(),
                    beanOfHistData.getVolume(),
                    beanOfHistData.getWap(),
                    beanOfHistData.getCount()
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        ColorSOP.i(symbol + " -> " + conid + " data table is created. " + insertCount + " days of historical data inserted.");
    }

    public int intervalDays(String conid, String symbol, Connection conn, QueryRunner qr) {
        int intervalDays;
        String sql_hist = "select * from " + "'" + conid + "'";
        BeanListHandler<BeanOfHistData> blh = new BeanListHandler<>(BeanOfHistData.class);
        List<BeanOfHistData> histBeanList = null;
        // histBeanList 里是每个 conid 表中的数据组，组成的List
        // [HistDataBean{time='2021-01-19 00:00:00', conid= 1715006, id=1, symbol=A, open=...}, HistDataBean{time='2021-01-20 00:00:00', ...}]
        try {
            histBeanList = qr.query(conn, sql_hist, blh);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert histBeanList != null;
        histBeanList.sort(Comparator.comparing(BeanOfHistData::getTime));
        String time = histBeanList.get(histBeanList.size() - 1).getTime();
        intervalDays = new IntervalDaysCalc().intervalDays(time);
        return intervalDays;
    }
}
