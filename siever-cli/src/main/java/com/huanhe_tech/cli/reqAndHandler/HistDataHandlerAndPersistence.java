package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class HistDataHandlerAndPersistence implements ApiController.IHistoricalDataHandler {
    private long conid;
    private String symbol;
    private int intervalDays;
    private Connection conn;
    private int addTablesCount;
    private QueryRunner qr;
    private String sql_inset_hist;
    private int insertRecord;
    public int insertCount;

    public HistDataHandlerAndPersistence() {
    }

    public HistDataHandlerAndPersistence(long conid, String symbol, int intervalDays) {
        this.conid = conid;
        this.symbol = symbol;
        this.intervalDays = intervalDays;
        int addTables = 0;
        try {
            conn = IJdbcUtils.getConnection();
            qr = new QueryRunner();

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
            addTables = qr.update(conn, sql_create_table);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (addTables > 0) {
            addTablesCount = addTablesCount + addTables;
            System.out.println(addTablesCount + " tables has been created." + " Create table: " + conid + " of " + symbol + ".");
        }

        sql_inset_hist = "insert or ignore into " +
                "'" + conid + "'" +
                " (time, conid, symbol, open, high, low, close, volume, wap, count)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public int getInsertCount() {
        return insertCount;
    }

    @Override
    public void historicalData(Bar bar) {
        try {
            insertRecord = qr.update(conn, sql_inset_hist,
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
        insertCount = insertRecord + insertCount;
    }

    @Override
    public void historicalDataEnd() {
        if (insertRecord > 0) {
            System.out.println(conid + " table has inserted " + insertCount + " piece of daily data for symbol " + symbol + ".");
        }
        synchronized (GlobalFlags.UpdateHistDone.STATE) {
            GlobalFlags.UpdateHistDone.STATE.setB(true);
            GlobalFlags.UpdateHistCompleteness.STATE.setB(insertCount == intervalDays);
            GlobalFlags.UpdateHistDone.STATE.notifyAll();
        }

//        IJdbcUtils.closeResource(conn, null);
    }
}
