package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.HistDataObjForIn;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsumeHistData {
    private long conid;
    private static int addConidsCount;
    private static int addTablesCount;
    private HistDataObjForIn take = null;
    private Connection conn = null;

    public void getHistDataFromQueue() {
        try {
            while (true) {
                take = InstancePool.getHistDataQueue().take();
//                System.out.println(take.toString());
                conid = take.getConid();
                NNHistOp();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 操作 NN_hist.db 数据库
    public void NNHistOp() {
        // 向表 symbol_list_tbl 插入数据
        try {
            QueryRunner qr = new QueryRunner();
            conn = IJdbcUtils.getConnection();
            String sql = "insert or ignore into symbols_list_tbl (conid) values(?)";
            int addConids = qr.update(conn, sql, conid);
            if (addConids > 0) {
                addConidsCount = addConidsCount + addConids;
                System.out.println(addConidsCount + " symbols has been added." + " Add conid: " + conid);
            }

            // 以 symbol_list_tbl 表中的 conid 作为表名创建历史数据表
            String sql_create_table = "create table if not exists " + "'" + conid + "'" + " ( id integer unique primary key, " +
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
            int addTables = qr.update(conn, sql_create_table);
            if (addTables > 0) {
                addTablesCount = addTablesCount + addTables;
                System.out.println(addTablesCount + " tables has been created." + " Create table: " + conid);
            }

            // 向历史数据表中插入历史数据
            take.getList().forEach(
                    item -> {
                        String sql_inset_hist = "insert or ignore into " +
                                "'" + conid + "'" +
                                " (time, conid, symbol, open, high, low, close, volume, wap, count)" +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try {
                            qr.update(conn, sql_inset_hist,
                                    item.getTime(),
                                    item.getConid(),
                                    item.getSymbol(),
                                    item.getOpen(),
                                    item.getHigh(),
                                    item.getLow(),
                                    item.getClose(),
                                    item.getVolume(),
                                    item.getWap(),
                                    item.getCount()
                                    );
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
            );

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            IJdbcUtils.closeResource(conn, null);
        }
    }

}
