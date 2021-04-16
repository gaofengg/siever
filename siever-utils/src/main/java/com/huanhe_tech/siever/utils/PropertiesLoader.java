package com.huanhe_tech.siever.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static @org.jetbrains.annotations.NotNull
    Properties getProperties(String uriHeader, String uriAnchor) {
        String projectDir = System.getProperty("user.dir");
        uriHeader = projectDir + "/" + uriHeader + "/";
        String uri = uriHeader + uriAnchor;

        Properties props = new Properties();
        try {
            props.load(new FileInputStream(uri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
   }
}
