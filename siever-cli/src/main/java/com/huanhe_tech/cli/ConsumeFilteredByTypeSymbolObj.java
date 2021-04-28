package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.FilteredByTypeFlowingSymbol;
import com.huanhe_tech.cli.queue.FiltrateBySymbolTypeQueue;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsumeFilteredByTypeSymbolObj {
    private Connection conn;
    private QueryRunner qr;

    public void takeAndPersistence() {
        try {
            conn = IJdbcUtils.getConnection();
            qr = new QueryRunner();
            String sql_clear_tbl = "delete from symbols_list_tbl";
            qr.update(conn, sql_clear_tbl);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        FiltrateBySymbolTypeQueue filtrateBySymbolTypeQueue = InstancePool.getFiltrateBySymbolTypeQueue();

        while (true) {
            FilteredByTypeFlowingSymbol flowingSymbolObj = filtrateBySymbolTypeQueue.takeSymbolObj();
            persistenceSymbolList(conn, qr, flowingSymbolObj);
            System.out.println(flowingSymbolObj);
            if (flowingSymbolObj.getSymbol().equals("#EOF")) {
                IJdbcUtils.closeResource(conn, null);
                break;
            }
        }
    }

    private void persistenceSymbolList(Connection conn, QueryRunner qr, FilteredByTypeFlowingSymbol flowingSymbol) {
        try {
            String sql_insert = "insert into symbols_list_tbl (id, conid, symbol) values(?, ?, ?)";
            qr.update(conn, sql_insert, flowingSymbol.getId(), flowingSymbol.getConid(), flowingSymbol.getSymbol());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
