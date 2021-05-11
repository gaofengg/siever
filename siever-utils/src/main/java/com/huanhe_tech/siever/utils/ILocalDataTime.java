package com.huanhe_tech.siever.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 判断当前时间有没有到下午6点，如果没有，时间定在前一天的下午6点
 */
public class ILocalDataTime {
    public String getLocalDataTime() {
        String nowDate = LocalDate.now(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return nowDate + " 18:00:00";
    }
}
