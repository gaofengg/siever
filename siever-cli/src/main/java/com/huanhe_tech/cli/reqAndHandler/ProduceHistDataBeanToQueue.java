package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.beans.EndOfHistBeanQueue;
import com.huanhe_tech.cli.beans.InterruptOfHistBeanQueue;
import com.huanhe_tech.cli.strategies.Strategy;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import com.huanhe_tech.siever.utils.LLoger;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProduceHistDataBeanToQueue {
    private final Strategy<List<BeanOfHistData>> strategy;
    // 查表 symbols_list_tbl，遍历 symbol，请求历史数据，并生成 Bean， put 到队列 QueueWithHistDataBean
    // 判断库中有没有对应 conid 的表

    public ProduceHistDataBeanToQueue(Strategy<List<BeanOfHistData>> strategy) {
        this.strategy = strategy;
            run();
    }

    public void run() {
        Connection conn = null;
        QueryRunner qr = null;
        List<Map<String, Object>> idAndConidAndSymbolMapList = null;
        try {
            conn = IJdbcUtils.getConnection();
            qr = new QueryRunner();

            String sql = "select id, conid, symbol from symbols_list_tbl where id > 5000 and id < 5050";
            MapListHandler mlh = new MapListHandler();
            idAndConidAndSymbolMapList = qr.query(conn, sql, mlh);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // forEach 拿的是 symbols_list_tbl 的数据, {id=1, conid=1715006, symbol=A}
        if (idAndConidAndSymbolMapList != null) {
            Iterator<Map<String, Object>> icsMapList = idAndConidAndSymbolMapList.iterator();
            synchronized (GlobalFlags.UpdateHistDone.STATE) {
                while (GlobalFlags.UpdateHistDone.STATE.getB()) {
                    GlobalFlags.UpdateHistDone.STATE.setB(false);
                    Map<String, Object> icsMap;
                    if (icsMapList.hasNext()) {
                        icsMap = icsMapList.next();
                    } else {
                        LLoger.logger.trace("NO GOODS!");
                        InstancePool.getQueueWithHistDataBean().put(new InterruptOfHistBeanQueue());
                        break;
                    }
                    String symbol = icsMap.get("symbol").toString();
                    String conid = icsMap.get("conid").toString();

                    if (symbol.equals("#EOF")) {
                        InstancePool.getQueueWithHistDataBean().put(new EndOfHistBeanQueue());
                        break;
                    }

                    int tableExistsCount = 0;
                    try {
                        // 查询数据库，判断是否有对应的 conid 历史数据表
                        String sql_find_tbl = "select count(*) from sqlite_master where type='table' and name='" + conid + "'";
                        ScalarHandler<Integer> sh = new ScalarHandler<>();
                        tableExistsCount = qr.query(conn, sql_find_tbl, sh);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    if (tableExistsCount == 0) {
                        // 没表，请求90天，并 put
                        new ReqSingleHistData(icsMap, 90);
                        try {
                            GlobalFlags.UpdateHistDone.STATE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        int intervalDays = new OpHistData().intervalDays(conid, symbol, conn, qr);
                        if (intervalDays == 0) {
                            LLoger.logger.trace("{} -> {} no updates are available.", symbol, conid);
                            new OpHistData().queryAndApplyStrategy(conid, symbol, conn, qr, 30, strategy);
                            GlobalFlags.UpdateHistDone.STATE.setB(true);
                        } else {
                            new ReqSingleHistData(icsMap, intervalDays);
                            try {
                                GlobalFlags.UpdateHistDone.STATE.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            LLoger.logger.error("ERROR: symbols_list_tbl seems to be null.");
        }
    }
}
