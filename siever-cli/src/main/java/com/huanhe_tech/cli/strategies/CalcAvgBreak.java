package com.huanhe_tech.cli.strategies;

public class CalcAvgBreak extends CalcAverage{
    public CalcAvgBreak(double[] array, int durationDays) {
        super(array, durationDays);
    }

    public double getOnBreak() {
        return super.getArry()[0] / super.getAvgOfVolume();
    }
}
