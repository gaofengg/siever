package com.huanhe_tech.siever;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.siever.utils.IJdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) throws SQLException {
        int id = 0;
        Connection conn = IJdbcUtils.getConnection();
        QueryRunner qr = new QueryRunner();
        // 取得 conid
        String sql_symbol_list = "select id, conid, symbol from symbols_list_tbl";
        MapListHandler mlh = new MapListHandler();
        List<Map<String, Object>> idAndConidAndSymbolList = qr.query(conn, sql_symbol_list, mlh);
        System.out.println(idAndConidAndSymbolList);
        idAndConidAndSymbolList.forEach(item -> {
            String conid = item.get("conid").toString();
            String sql_delete_tbl = "drop table " + "'" + conid + "'";
            try {
                qr.update(conn, sql_delete_tbl);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        // 找表
//        String sql_find_tbl = "select count(*) from sqlite_master where type='table' and name='202225021'";
//        BeanListHandler<BeanOfHistData> blh = new BeanListHandler<>(BeanOfHistData.class);
//        ScalarHandler<Integer> sh = new ScalarHandler<>();
//        int isHasCount = qr.query(conn, sql_find_tbl, sh);
//        System.out.println(isHasCount);

//        List<BeanOfHistData> HistDataList = qr.query(conn, sql_find_tbl, blh);
//        System.out.println(HistDataList);

        // if 有表，查最后一天与今日间隔

        // if 无表，创建表，间隔时间改为90，并获取数据

    }
}
