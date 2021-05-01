package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.connection.Reconnection;
import com.huanhe_tech.siever.utils.ColorSOP;
import com.huanhe_tech.siever.utils.IntervalDaysCalc;
import com.huanhe_tech.cli.reqAndHandler.ReqSingleHistData;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 校验 symbol 表数据的完整性
 * 需更新的数据为零，否则输出数据未更新提示
 * 数据最少是否有30条，如果没有，忽略该 symbol 的计算
 */

public class StrategyApply {

    public void getHistDataAndStrategyApply(Strategy<List<BeanOfHistData>> strategy) {
        int histMinSize = 30;
        if (!InstancePool.getConnectionController().client().isConnected()) {
            System.out.println("Reconnecting ...");
            new Reconnection();
        }

        try {
            Connection conn = IJdbcUtils.getConnection();
            QueryRunner qr = new QueryRunner();
            String sql = "select id, conid, symbol from symbols_list_tbl limit 200";
            MapListHandler mlh = new MapListHandler();

            List<Map<String, Object>> idAndConidAndSymbolMapList = qr.query(conn, sql, mlh);
            continueOUt:
            for (Map<String, Object> item : idAndConidAndSymbolMapList) {
                try {
                    if (conn.isClosed()) {
                        conn = IJdbcUtils.getConnection();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String symbol = item.get("symbol").toString();
                // forEach 拿的是 symbols_list_tbl 的数据, {id=1, conid=1715006, symbol=A}
                int intervalDays;
                String conid = item.get("conid").toString();


                // 判断当前conid的历史数据表是否存在
                String sql_find_tbl = "select count(*) from sqlite_master where type='table' and name='" + conid + "'";
                ScalarHandler<Integer> sh = new ScalarHandler<>();
                int tableExistsCount = 0;
                try {
                    tableExistsCount = qr.query(conn, sql_find_tbl, sh);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (tableExistsCount == 0) {
                    // 如果表不存在，便创建表，并插入90天历史数据
                    new ReqSingleHistData(item, 90);
                    if (!GlobalFlags.UpdateHistCompleteness.STATE.getB()) {
                        ColorSOP.e(symbol + " -> " + conid + " Failed to create data table.");
                        continue continueOUt;
                    }
                }
                    // 否则表存在，查找表的最近日期与今日的天数间隔，按照间隔天数插入相应的历史数据
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
                    if (intervalDays == 0) {
                        if (histBeanList.size() >= histMinSize) {
                            ColorSOP.i(symbol + " -> No need to update data in " + symbol + ", start calculating.");
                            strategy.run(histBeanList);
                        } else {
                            ColorSOP.w(symbol + " * -> Insufficient target symbol data. Data size < 30. Calculation is ignored.");
                        }
                    } else {
                        ColorSOP.i(histBeanList.get(0).getSymbol() + " -> " + intervalDays + " daily data of " + histBeanList.get(0).getSymbol() + " needs to be updated.");
                        new ReqSingleHistData(item, intervalDays);
                        if (GlobalFlags.UpdateHistCompleteness.STATE.getB()) {
                            if (histBeanList.size() >= histMinSize) {
                                ColorSOP.i(symbol + " -> Data update is done, start calculating.");
                                // 重新获取数据库中的数据
                                String sql_hist_reacquire = "select * from " + "'" + conid + "'";
                                List<BeanOfHistData> re_histBeanList = null;
                                BeanListHandler<BeanOfHistData> re_blh = new BeanListHandler<>(BeanOfHistData.class);
                                try {
                                    re_histBeanList = qr.query(conn, sql_hist_reacquire, re_blh);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                strategy.run(re_histBeanList);
                            } else {
                                ColorSOP.w(symbol + " * -> Insufficient target symbol data. Data size < 30. Calculation is ignored.");
                            }
                            GlobalFlags.UpdateHistCompleteness.STATE.setB(false);
                        } else {
                            ColorSOP.w(symbol + " * -> Incomplete data update, Calculation is ignored.");
                        }
                    }
//                IJdbcUtils.closeResource(conn, null);
            }

            InstancePool.getConnectionController().disconnect();
            IJdbcUtils.closeResource(conn, null);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
