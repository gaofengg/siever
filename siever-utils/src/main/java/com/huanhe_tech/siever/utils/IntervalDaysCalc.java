package com.huanhe_tech.siever.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IntervalDaysCalc {

    /**
     * 代入日期字符串，字符串转换成 ZonedDateTime，计算代入的日期与现在日期的天数间隔，并返回
     *
     * @param dateStr 日期字符串
     * @return 返回代入的日期与现在日期的天数间隔
     */
    public int intervalDays(String dateStr) {
        // 将从外面传入的数据库中历史数据的最近时间转换成可以转换成 ZonedDateTime 类型的字符串实参
        // 获取历史数据最近时间（ZonedDateTime 格式）
        ZonedDateTime lastDateTime = StrToZonedDateTime.newYork(dateStr);
        // 获取纽约当前时间
        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("GMT-4"));
        // 获取当天下午6点的时间点
        // 需求说明：如果时间过了当天的下午6点，便将当前时间改成后一天的 00:00:00
        // 作用：纽约时间的当天下午6点已收盘，当天的历史数据应该被获取。
        ZonedDateTime todayPoint = StrToZonedDateTime.newYork(nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 18:00:00");

        if (nowTime.compareTo(todayPoint) > 0) {
            nowTime = StrToZonedDateTime.newYork(nowTime.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00");
        }

        long intervalDays = Duration.between(nowTime, lastDateTime).abs().toDays();
        List<ZonedDateTime> daysList = new ArrayList<>();

        // 获取起始日期到截止日期之间的工作日数
        for (int i = 1; i < intervalDays; i++) {
            ZonedDateTime nextDate = lastDateTime.plusDays(i);
            if (nextDate.getDayOfWeek().getValue() != 6 && nextDate.getDayOfWeek().getValue() != 7) {
                daysList.add(nextDate);
            }
        }

        return daysList.size();
    }
}
