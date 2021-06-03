package com.huanhe_tech.siever.utils;

import java.util.Properties;

public class CliParam {
    private static final PropertiesHandler cliParamPath = new PropertiesHandler("resources", "conf/cli_param.properties");

    public static String getParam(String param) {
        Properties pp = cliParamPath.getProps();
        return pp.getProperty(param);
    }
}
