package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.siever.utils.ColorSOP;
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

        try {
            conn = IJdbcUtils.getConnection();
            qr = new QueryRunner();

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
            try {
                qr.update(conn, sql_create_table);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            sql_inset_hist = "insert or ignore into " +
                    "'" + conid + "'" +
                    " (time, conid, symbol, open, high, low, close, volume, wap, count)" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
//        if (insertCount == intervalDays) {
            ColorSOP.i(symbol + " -> " + conid + " data table is created. " + insertCount + " days of historical data inserted.");
//        } else {
//            String deleteTable = "drop table " + "'" + conid + "'";
//            try {
//                qr.update(conn, deleteTable);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            ColorSOP.w(symbol + " -> " + conid + " Failed to insert historical data, this table has been deleted.");
//        }

        synchronized (GlobalFlags.UpdateHistDone.STATE) {
            GlobalFlags.UpdateHistDone.STATE.setB(true);
//            GlobalFlags.UpdateHistCompleteness.STATE.setB(insertCount == intervalDays);
            GlobalFlags.UpdateHistCompleteness.STATE.setB(true);
            GlobalFlags.UpdateHistDone.STATE.notifyAll();
        }

        IJdbcUtils.closeResource(conn, null);
    }
}
