package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.siever.utils.IntervalDaysCalc;

import java.util.List;

/**
 * 功能说明；（在符合其他过滤条件满足的情况下）
 * 过滤对象：第一个极值到今日之间允许的最大条柱数量的每一个条柱
 * 过滤条件：当趋势是 up 时，综合过滤条件查找 long 的标的
 * 每个条柱的开盘价 - 最低价 <= 振幅 * 20%
 * 当趋势时 down 时，综合过滤条件查找 short 的标的
 * 最高价 - 每个条柱的开盘价 <= 振幅 * 20%
 * <p>
 * │               │20%
 * │               ├─
 * │               │
 * │               │
 * ├─              │
 * │               │
 * │               │
 * │               │
 * │              ─┤
 * │               │
 * ─┤               │
 * 20%│               │
 * <p>
 * LONG            SHORT
 */
public class FilterOpenToEndpoint {
    private final List<BeanOfHistData> list;
    private final double openToEndpointPercent;
    private final int latestExtremeIntervalDays;

    public FilterOpenToEndpoint(List<BeanOfHistData> list, List<BeanOfHistData> mergedList, double openToEndpointPercent) {
        this.list = list;
        this.openToEndpointPercent = openToEndpointPercent;
        String mergedListLastTime = mergedList.get(0).getTime();
        latestExtremeIntervalDays = new IntervalDaysCalc().intervalDays(mergedListLastTime);
    }
    // 开盘价 list

    public boolean whenUp() {
        if (list.stream().limit(latestExtremeIntervalDays).anyMatch(l -> l.getLow() == l.getHigh())) {
            return false;
        } else {
            return list.stream().limit(latestExtremeIntervalDays).noneMatch(l ->
                    (l.getOpen() - l.getLow()) / (l.getHigh() - l.getLow()) > (openToEndpointPercent / 100)
            );
        }
    }

    public boolean whenDown() {
        if (list.stream().limit(latestExtremeIntervalDays).anyMatch(l -> l.getLow() == l.getHigh())) {
            return false;
        } else {
            return list.stream().limit(latestExtremeIntervalDays).allMatch(l ->
                    (l.getHigh() - l.getOpen()) / (l.getHigh() - l.getLow()) < (openToEndpointPercent / 100)
            );
        }
    }

}
