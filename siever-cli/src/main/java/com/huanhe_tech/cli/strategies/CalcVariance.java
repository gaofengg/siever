package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class CalcVariance {
    private final double[] array;
    private int durationDays;

    public CalcVariance(double[] array, int durationDays) {
        this.array = array;
        this.durationDays = durationDays;
    }
    public CalcVariance(double[] array) {
        this.array = array;
    }

    public double getVarWithLimit() {
        DoubleStream limit = Arrays.stream(array).limit(durationDays);
        double[] doubles = limit.toArray();
        return new Variance().evaluate(doubles);
    }
    public double getVar() {
        return new Variance().evaluate(array);
    }
}
