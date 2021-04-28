package com.huanhe_tech.siever.utils;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.util.Properties;

public class PropertiesHandler {
    private final String uri;
    private final Properties props;

    public PropertiesHandler(String uriHeader, String uriAnchor) {
        String projectDir = System.getProperty("user.dir");
        uriHeader = projectDir + "/" + uriHeader + "/";
        this.uri = uriHeader + uriAnchor;
        this.props = new Properties();

    }

    public Properties getProps() {

        try {
            props.load(new FileInputStream(uri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public void setProps(String key, String value) {
        // 没有使用java原生的方法，是因为 props.store 的第二个参数会覆盖配置文件里的所有注释。
//        try {
//            props.setProperty(key, value);
//            props.store(new FileOutputStream(uri), "");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 使用 commons-configuration 库解决配置文件的写
        try {
            Configurations configs = new Configurations();
            PropertiesConfiguration config = configs.properties(new File(uri));
            config.setProperty(key, value);
            config.write(new OutputStreamWriter(new FileOutputStream(uri)));
        } catch (ConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

}
