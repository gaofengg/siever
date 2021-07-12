package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.beans.BeanOfExtremeResult;
import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.beans.BeanOfHistListInQueue;
import com.huanhe_tech.cli.reqAndHandler.OpHistData;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 校验 symbol 表数据的完整性
 * 需更新的数据为零，否则输出数据未更新提示
 * 数据最少是否有30条，如果没有，忽略该 symbol 的计算
 */

public class StrategyApply {

    public void getHistDataAndStrategyApply(Strategy<List<BeanOfHistData>> strategy) {
        int histMinSize = 30;
        try {
            Connection conn = IJdbcUtils.getConnection();
            QueryRunner qr = new QueryRunner();

            // 从 QueueWithHistDataBean 队列取数据
            while (true) {
                BeanOfHistListInQueue beanOfHistListInQueue = InstancePool.getQueueWithHistDataBean().take();
                if (beanOfHistListInQueue.getId() == 20000 || beanOfHistListInQueue.getId() == 20001) {
                    InstancePool.getQueueWithExtremeResultBean().put(new BeanOfExtremeResult(
                            20000,
                            0L,
                            "#EOF",
                            "0",
                            0.0,
                            0.0,
                            0.0,
                            0.0,
                            0.0,
                            0.0
                    ));
                    while (true) {
                        if (InstancePool.getConnectionController().client().isConnected()) {
                            InstancePool.getConnectionController().disconnect();
                        } else {
                            break;
                        }
                    }
                    break;
                }
                List<BeanOfHistData> list = beanOfHistListInQueue.getList();
                String conid = null;
                String symbol = null;
                for (BeanOfHistData item : list) {
                    conid = String.valueOf(item.getConid());
                    symbol = item.getSymbol();
                    int tableCount = tablesCount(conid, conn, qr);
                    if (tableCount == 0) {
                        new OpHistData().createTableAndInsertData(conid, conn, qr);
                    }
                    new OpHistData().updateData(conid, symbol, conn, qr, item);
                }
                // 重新从数据库中拿数据，计算
                new OpHistData().queryAndApplyStrategy(conid, symbol, conn, qr, histMinSize, strategy);
            }

            IJdbcUtils.closeResource(conn, null);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public int tablesCount(String conid, Connection conn, QueryRunner qr) {
        int tableExistsCount = 0;
        try {
            // 查询数据库，判断是否有对应的 conid 历史数据表
            String sql_find_tbl = "select count(*) from sqlite_master where type='table' and name='" + conid + "'";
            ScalarHandler<Integer> sh = new ScalarHandler<>();
            tableExistsCount = qr.query(conn, sql_find_tbl, sh);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tableExistsCount;
    }
}
