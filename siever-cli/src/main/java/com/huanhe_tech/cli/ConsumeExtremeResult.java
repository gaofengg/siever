package com.huanhe_tech.cli;

import com.huanhe_tech.cli.beans.BeanOfExtremeResult;
import com.huanhe_tech.cli.crawler.CrawlerExecutor;
import com.huanhe_tech.cli.crawler.MarketCapStrToDecimal;
import com.huanhe_tech.cli.crawler.SymbolRename;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class ConsumeExtremeResult {
    private Connection conn;

    {
        try {
            conn = IJdbcUtils.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ConsumeExtremeResult() {
        String sql_insert_result = null;
        QueryRunner qr = new QueryRunner();
        try {
            // 查询表是否存在
            String sql_find_result_tbl = "select count(*) from sqlite_master where type='table' and name='extreme_result'";
            // 创建表 extreme_result
            String create_result_tbl = "create table extreme_result (id integer unique primary key," +
                    "conid integer not null," +
                    "symbol text not null," +
                    "orientation text not null," +
                    "quote_change_var real," +
                    "volume_var real," +
                    "volume_break real," +
                    "extreme_var real," +
                    "market_cap NUMERIC," +
                    "url text)";
            // 清除 extreme_result 表中的数据
            String sql_clear_tbl = "delete from extreme_result";
            // 写表
            sql_insert_result = "insert or ignore into extreme_result (" +
                    "conid, " +
                    "symbol, " +
                    "orientation," +
                    "quote_change_var , " +
                    "volume_var, " +
                    "volume_break, " +
                    "extreme_var, " +
                    "market_cap," +
                    " url) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ScalarHandler<Integer> shResult = new ScalarHandler<>();
            int resultTableExistsCount = qr.query(conn, sql_find_result_tbl, shResult);

            if (resultTableExistsCount == 0) {
                qr.update(conn, create_result_tbl);
            } else {
                qr.update(conn, sql_clear_tbl);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        while (true) {
            BeanOfExtremeResult beanOfExtremeResult = InstancePool.getQueueWithExtremeResultBean().take();
            if (beanOfExtremeResult.getSymbol().equals("#EOF")) break;
            String yahooFormatSymbol = beanOfExtremeResult.getSymbol();
            String tradingViewSymbol = beanOfExtremeResult.getSymbol();
            if (beanOfExtremeResult.getSymbol().contains(" ")) {
                yahooFormatSymbol = new SymbolRename(yahooFormatSymbol, "-").translate();
                tradingViewSymbol = new SymbolRename(tradingViewSymbol, "/").translate();
            }
            CrawlerExecutor crawlerExecutor = new CrawlerExecutor(yahooFormatSymbol, tradingViewSymbol).execute();
            BigDecimal marketCap = new MarketCapStrToDecimal(crawlerExecutor.getMarketCap()).translate();
            String url = crawlerExecutor.getUrl();
            try {
                qr.update(conn, sql_insert_result,
                        beanOfExtremeResult.getConid(),
                        beanOfExtremeResult.getSymbol(),
                        beanOfExtremeResult.getOrientation(),
                        beanOfExtremeResult.getQuoteChangeVariance(),
                        beanOfExtremeResult.getVolumeVariance(),
                        beanOfExtremeResult.getVolumeBreak(),
                        beanOfExtremeResult.getExtremeVariance(),
                        marketCap,
                        url);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
