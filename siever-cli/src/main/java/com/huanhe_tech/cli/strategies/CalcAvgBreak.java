package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.siever.utils.LLoger;

import java.util.*;

public class CalcAvgBreak extends CalcAverage{
    private final int intervalDaysFromLatestExtremeToToday;
    private final String symbol;

    /**
     * 最近一个极值到今天的时间范围内，最大的交易量与指定周期内的平均交易量的比
     * @param array 指定周期内的交易量数组
     * @param durationDays 指定统计交易量的天数
     * @param intervalDaysFromLatestExtremeToToday 最近一个极值到今天的天数间隔
     */
    public CalcAvgBreak(double[] array, int durationDays, int intervalDaysFromLatestExtremeToToday, String symbol) {
        super(array, durationDays);
        this.intervalDaysFromLatestExtremeToToday = intervalDaysFromLatestExtremeToToday;
        this.symbol = symbol;
    }

    public double getOnBreak() {
        return durationMaxVolume() / super.getAvgOfVolume();
    }

    private double durationMaxVolume() {
        return Arrays.stream(super.getArray()).limit(intervalDaysFromLatestExtremeToToday + 1)
                .max()
                .orElse(0.0);
    }
}
