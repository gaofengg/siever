package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.queue.FlowingSymbolObj;

import java.util.List;

public class CalcExtremesDurationDays {
    private final List<BeanOfHistData> mergedBeanOfHistDataList;
    private final int extremeDurationDays;
    public CalcExtremesDurationDays(List<BeanOfHistData> mergedBeanOfHistDataList, int extremeDurationDays) {
        this.mergedBeanOfHistDataList = mergedBeanOfHistDataList;
        this.extremeDurationDays = extremeDurationDays;
    }

    public boolean isFullDuration() {
        int[] ints = mergedBeanOfHistDataList.stream().mapToInt(FlowingSymbolObj::getId).toArray();
        for (int i = 1; i < mergedBeanOfHistDataList.size(); i++) {
            int abs = Math.abs(ints[i - 1] - ints[i]);
            if (abs < extremeDurationDays) return false;
        }
        return true;
    }

}
