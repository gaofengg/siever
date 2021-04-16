package com.huanhe_tech.cli.DAO;

import com.huanhe_tech.siever.utils.*;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcController {
//    public static void main(String[] args) throws IOException, SQLException {
//        Connection conn = null;
//        try {
//            conn = JdbcConnection.jdbcConnect();
//            // 取消自动提交
//            conn.setAutoCommit(false);
//            String sql = "insert into symbols_list_tbl ('conid') values(?)";
//            JdbcCUD.CRU(conn, sql, "677777");
//            // 完成数据更改后提交
//            conn.commit();
//        } catch (SQLException e) {
//            try {
//                // 如果数据更改失败，回滚
//                conn.rollback();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            e.printStackTrace();
//        } finally {
//            // 关闭连接
//            JdbcConnection.jdbcClose(conn, null);
//        }

//        Connection conn = null;
//        try {
//            conn = IJdbcUtils.getConnection();
//
//            String sql = "select * from symbols_list_tbl where id < ?";
//            List<SymbolsList> symbolsLists = JdbcRetrieve.retrieveList(SymbolsList.class, conn, sql, 50);
//            if (symbolsLists != null) {
//                symbolsLists.forEach(System.out::println);
//            } else {
//                System.out.println("查询失败");
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            IJdbcUtils.closeResource(conn, null);
//        }

    public void updateData(int content) {
        Connection conn = null;
        try {
            conn = IJdbcUtils.getConnection();
            QueryRunner qr = new QueryRunner();
            String sql = "insert into symbols_list_tbl (conid) values(?)";
            int updateCount = qr.update(conn, sql, content);
            System.out.println("插入了" + updateCount + "条数据。");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            IJdbcUtils.closeResource(conn, null);
        }
    }
}

//}
