package com.huanhe_tech.cli.DAO;

import com.huanhe_tech.siever.utils.LoadReSrc;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public void connectionToDao() {
        Configurations configs = new Configurations();
        Configuration config = null;

        // 读取配置文件
        try {
            config = configs.properties(new File(new LoadReSrc("resources", "conf/jdbc_connection_conf.properties").getUri()));
            System.out.println(config.getString("sqlite_driver"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
            System.out.println("配置文件加载失败。");
        }

        if (config != null) {
            // 加载驱动
            try {
                Class.forName(config.getString("sqlite_driver"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("未找到相关配置项。");
            }

            // 获取链接
            try {
                Connection con = DriverManager.getConnection(config.getString("url"));
                System.out.println(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("未找到相关数据库。");
            }
        } else {
            System.out.println("配置文件读取失败。");
        }

    }

}
