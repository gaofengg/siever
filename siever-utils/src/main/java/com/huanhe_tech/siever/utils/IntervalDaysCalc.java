package com.huanhe_tech.siever.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class IntervalDaysCalc {

    /**
     * 代入日期字符串，字符串转换成 LocalDate，计算代入的日期与现在日期的天数间隔，并返回
     *
     * @param dateStr 日期字符串
     * @return 返回代入的日期与现在日期的天数间隔
     */
    public int intervalDays(String dateStr) {
        List<LocalDate> daysList = new ArrayList<>();

        String date = dateStr.substring(0, dateStr.indexOf(" "));
        LocalDate lastDate = LocalDate.parse(date);
        LocalDate nowDate = LocalDate.now(ZoneId.of("GMT-4"));

        // 获取其实日期到截止日期之间的工作日数
        int intervalDays = nowDate.compareTo(lastDate) + 1;
        for (int i = 1; i < intervalDays; i++) {
            LocalDate nextDate = lastDate.plusDays(i);
            if (nextDate.getDayOfWeek().getValue() != 6 && nextDate.getDayOfWeek().getValue() != 7) {
                daysList.add(nextDate);
            }
        }

        return daysList.size();
    }
}
