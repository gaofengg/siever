package com.huanhe_tech.cli.DAO;

import com.huanhe_tech.siever.utils.JdbcCRU;
import com.huanhe_tech.siever.utils.JdbcConnection;

import java.sql.Connection;

public class JdbcController {
    public static void main(String[] args) {
        String sql = "insert into symbols_list_tbl ('conid') values(?)";
        JdbcCRU.CRU(sql, 6667);
    }
}
