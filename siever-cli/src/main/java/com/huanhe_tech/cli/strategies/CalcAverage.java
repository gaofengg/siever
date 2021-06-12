package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.Arrays;
import java.util.List;

public class CalcAverage {
    private final double[] array;
    private final int durationDays;

    public CalcAverage(double[] array, int durationDays) {
        this.array = array;
        this.durationDays = durationDays;
    }

    public double getAvgOfVolume() {
        double[] array1 = array;
        Arrays.sort(array1);
        double sum = 0;
        for (int i = 1; i < array1.length - 1; i++) {
            sum = sum + array1[i];
        }
        return sum / (array1.length - 2);
    }

    public double[] getArray() {
        return array;
    }

    public int getDurationDays() {
        return durationDays;
    }
}
