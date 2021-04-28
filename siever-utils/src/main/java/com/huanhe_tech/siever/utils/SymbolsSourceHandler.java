package com.huanhe_tech.siever.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Properties;

public class SymbolsSourceHandler {
    private static final PropertiesHandler ph = new PropertiesHandler("resources", "conf/settings.properties");
    private static String newSlmt;

    public static boolean isUpdate(String uri) {
        // slmt: Symbol source file last modified timestamp.
        Properties inputProps = ph.getProps();
        String oldSlmt = inputProps.getProperty("slmt");

        File file = new File(uri);
        newSlmt = String.valueOf(file.lastModified());
        if (!newSlmt.equals(oldSlmt)) {
            System.out.println("Symbol list source file has been updated. The database will be updated");
            return true;
        } else {
            return false;
        }
    }

    public static void setNewSlmt() {
        ph.setProps("slmt", newSlmt);
    }
}
