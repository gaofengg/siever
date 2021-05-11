package com.huanhe_tech.siever.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * 1，查找配置文件 ./resources/conf/settings.properties 的 BOF 项的配置（日期）
 * 将 ./resources/usa.txt 文件中的 BOF 项日期与之比较，
 * 如果 ./resources/usa.txt中的 BOF 项日期与之相等，不更新 symbols_list_tbl 表，然后执行计算
 * 否则，如果比配置文件中的 BOF 日期旧，打印错误信息
 * 如果比配置文件中的 BOF 日期新，则更新 symbols_list_tbl 表，然后执行计算
 */

public class SymbolsSourceHandler {
    private static final PropertiesHandler ph = new PropertiesHandler("resources", "conf/settings.properties");
    private static String newBOF;
    private static String BOF;
    private static int flag;

    /**
     * @param uri 以项目目录为根目录，不已 / 开始。如 "resources/usa.txt"
     * @return 如果文件中的 BOM 项时间与配置文件中的 BOM 项时间不相等，则返回 ture， 否则返回 false
     */
    public static int needUpdate(String uri) {
        Properties inputProps = ph.getProps();
        BOF = inputProps.getProperty("BOF");

        try {
            File file = new File(uri);
            Stream<String> lines = Files.lines(file.toPath());
            lines.forEach(line -> {
                if (line.matches("^#BOF.*$")) {
                    String substring = line.substring(0, line.length() - 1);
                    String[] splits = substring.split("\\|");
                    newBOF = splits[1];
                    flag = newBOF.compareTo(BOF);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void setBOF() {
        ph.setProps("BOF", newBOF);
    }
}
