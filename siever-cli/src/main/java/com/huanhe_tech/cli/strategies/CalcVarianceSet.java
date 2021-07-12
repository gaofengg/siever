package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.siever.utils.IntervalDaysCalc;

import java.util.List;

public class CalcVarianceSet {
    private final List<BeanOfHistData> list;
    private final List<BeanOfHistData> mergedBeanOfHistDataList;
    private final int durationDays;
    private final double[] volume;
    private final String symbol;
    private double[] quoteChange;

    public CalcVarianceSet(List<BeanOfHistData> list, List<BeanOfHistData> mergedBeanOfHistDataList, int durationDays, String symbol) {
        this.list = list;
        this.mergedBeanOfHistDataList = mergedBeanOfHistDataList;
        this.durationDays = durationDays;
        this.symbol = symbol;
        volume = list.stream().mapToDouble(BeanOfHistData::getVolume).toArray();
    }

    // 最后一个极值到今日的天数间隔
    public int getIntervalDaysFromExtremeToToday() {
        return new IntervalDaysCalc().intervalDays(mergedBeanOfHistDataList.get(0).getTime());
    }

    // 求涨跌幅取样方差
    public double getQuoteChangeVar() {
        quoteChange = list.stream().mapToDouble(item -> item.getHigh() - item.getLow()).toArray();
        return new CalcVariance(quoteChange, durationDays).getVarWithLimit();
    }

    // 求成交量突破，成交量取样方差
    public double getVolumeBreak() {
        return new CalcAvgBreak(volume, durationDays, getIntervalDaysFromExtremeToToday(), symbol).getOnBreak();
    }

    public double getVolumeVar() {
        return new CalcVariance(volume, durationDays).getVarWithLimit();
    }

    // 求极值方差
    public double getLowExtremeVariance() {
        double[] lowArray = mergedBeanOfHistDataList.stream().mapToDouble(BeanOfHistData::getLow).toArray();
        return new CalcVariance(lowArray).getVar();
    }

    public double getHighExtremeVariance() {
        double[] highArray = mergedBeanOfHistDataList.stream().mapToDouble(BeanOfHistData::getHigh).toArray();
        return new CalcVariance(highArray).getVar();
    }

    // 求最近一个极值到今日的涨跌幅方差
    public double getFirstOrderQuoteChangeVar() {
        return new CalcVariance(quoteChange, getIntervalDaysFromExtremeToToday() + 1).getVarWithLimit();
    }
    // 求最近一个极值到今日的成交量方差
    public double getFirstOrderVolumeVar() {
        return new CalcVariance(volume, getIntervalDaysFromExtremeToToday() + 1).getVarWithLimit();
    }
}
