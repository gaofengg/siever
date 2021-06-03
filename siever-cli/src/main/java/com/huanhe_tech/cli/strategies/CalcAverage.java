package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.Arrays;
import java.util.List;

public class CalcAverage {
    private final double[] arry;
    private final int durationDays;

    public CalcAverage(double[] array, int durationDays) {
        this.arry = array;
        this.durationDays = durationDays;
    }

    public double getAvgOfVolume() {
        return Arrays.stream(arry).skip(1)
                .limit(durationDays)
                .average().orElse(0);
    }

    public double[] getArry() {
        return arry;
    }

    public int getDurationDays() {
        return durationDays;
    }
}
