package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.beans.BeanOfExtremeResult;
import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.siever.utils.DoubleDecimalDigits;
import com.huanhe_tech.siever.utils.IntervalDaysCalc;
import com.huanhe_tech.siever.utils.LLoger;

import java.util.*;

/*
 * 0、收集 Low 的最值 list
 * 1、判断是否有序
 * 2、收集 High 的最值 list
 * 3、判断是否有序
 * 4、合并两个 list
 * 5、判断 list.getTime() 是否有序
 * 6、确定趋势方向
 * 7、找趋势突破
 */

public class StrategyExtreme implements Strategy<List<BeanOfHistData>> {
    private int extremeNumbers = 3;
    private int pileNumbers = 5;
    private int redundancy = 2;
    private int durationDays = 30;
    private int id = 0;
    private int extremeDurationDays = 1;
    private int openToEndpointPercent = 100;
    private double extremeQuoteChange = 5.0;

    public StrategyExtreme() {
    }

    /**
     * @param pileNumbers    计算极值时，极值左右两边桩值的数量（单边数量）
     *                       桩值：计算 low 极值时，两边的桩值必须大于 low 极值，计算 high 极值反之
     * @param extremeNumbers 极值数列里最多保存的极值对象数量
     */
    public StrategyExtreme(int pileNumbers, int extremeNumbers, int redundancy, int durationDays, int extremeDurationDays, int openToEndpointPercent, double extremeQuoteChange) {
        this.pileNumbers = pileNumbers;
        this.extremeNumbers = extremeNumbers;
        this.redundancy = redundancy;
        this.durationDays = durationDays;
        this.extremeDurationDays = extremeDurationDays;
        this.openToEndpointPercent = openToEndpointPercent;
        this.extremeQuoteChange = extremeQuoteChange;
    }

    @Override
    public void run(List<BeanOfHistData> list) {

        String symbol = list.get(0).getSymbol();
        long conid = list.get(0).getConid();

        // 按照日期从最近日期开始排序
        list.sort(Comparator.comparing(BeanOfHistData::getTime).reversed());
        // 按照日期倒序，生成低点 list 和高点 list
        List<BeanOfHistData> lowsList = new CalcExtremes(list, pileNumbers, extremeNumbers).findLows();
        List<BeanOfHistData> highList = new CalcExtremes(list, pileNumbers, extremeNumbers).findHigh();

        if (lowsList.size() >= extremeNumbers && highList.size() >= extremeNumbers) {
            // 判断低点 list 和高点 list 是否有序
            boolean orderedByLow = isOrderedByLow(lowsList);
            boolean orderedByHigh = isOrderedByHigh(highList);
            if (orderedByLow && orderedByHigh) { // 如果两个 list 都有序
                // 合并两个 list
                List<BeanOfHistData> mergedBeanOfHistDataList = mergeList(lowsList, highList);
                // 判断合并后的 list 按照日期规则是否有序
                boolean orderedByTime = isOrderedByTime(mergedBeanOfHistDataList);
                // 判断合并后的 list 极值之间的最小间隔是否符合要求
                boolean isFullDuration = new CalcExtremesDurationDays(mergedBeanOfHistDataList, extremeDurationDays).isFullDuration();
                // 单元极值之间的振幅是否符合要求
                boolean isExtremeQuoteChange = this.hasExtremeQuoteChange(mergedBeanOfHistDataList, extremeQuoteChange);
                if (orderedByTime && isFullDuration && isExtremeQuoteChange) {
                    FilterOpenToEndpoint filterOpenToEndpoint = new FilterOpenToEndpoint(list, mergedBeanOfHistDataList, openToEndpointPercent);
                    String orientation = orientate(mergedBeanOfHistDataList);
                    // 如果方向是上涨，则找到最低点离今日 redundancy 天的标的。
                    if (orientation.equals("UP") &&
                            extremeHeader(lowsList, mergedBeanOfHistDataList).equals("LOW") &&
                            firstBreakthrough(list, mergedBeanOfHistDataList, orientation, redundancy) &&
                            filterOpenToEndpoint.whenUp()
                    ) { // 【过滤条件太苛刻】
                        CalcVarianceSet cvsUp = new CalcVarianceSet(list, mergedBeanOfHistDataList, durationDays, symbol);
                        LLoger.logger.info("{} -> Orientation: {}", symbol, orientation);
                        mergedBeanOfHistDataList.forEach(item -> LLoger.logger.debug(item.toString()));

                        InstancePool.getQueueWithExtremeResultBean().put(new BeanOfExtremeResult(nextId(),
                                conid,
                                symbol,
                                orientation,
                                DoubleDecimalDigits.transition(2, cvsUp.getQuoteChangeVar()),
                                DoubleDecimalDigits.transition(2, cvsUp.getVolumeVar()),
                                DoubleDecimalDigits.transition(2, cvsUp.getVolumeBreak()),
                                DoubleDecimalDigits.transition(2, cvsUp.getFirstOrderQuoteChangeVar()),
                                DoubleDecimalDigits.transition(2, cvsUp.getFirstOrderVolumeVar()),
                                DoubleDecimalDigits.transition(2, cvsUp.getHighExtremeVariance())
                        ));
                    } else if (orientation.equals("DOWN") &&
                            extremeHeader(lowsList, mergedBeanOfHistDataList).equals("HIGH") &&
                            firstBreakthrough(list, mergedBeanOfHistDataList, orientation, redundancy) &&
                            filterOpenToEndpoint.whenDown()
                    ) { // 【过滤条件太苛刻】
                        CalcVarianceSet cvsDown = new CalcVarianceSet(list, mergedBeanOfHistDataList, durationDays, symbol);
                        LLoger.logger.info("{} -> Orientation: {}", symbol, orientation);
                        mergedBeanOfHistDataList.forEach(item -> LLoger.logger.debug(item.toString()));
                        InstancePool.getQueueWithExtremeResultBean().put(new BeanOfExtremeResult(nextId(),
                                conid,
                                symbol,
                                orientation,
                                DoubleDecimalDigits.transition(2, cvsDown.getQuoteChangeVar()),
                                DoubleDecimalDigits.transition(2, cvsDown.getVolumeVar()),
                                DoubleDecimalDigits.transition(2, cvsDown.getVolumeBreak()),
                                DoubleDecimalDigits.transition(2, cvsDown.getFirstOrderQuoteChangeVar()),
                                DoubleDecimalDigits.transition(2, cvsDown.getFirstOrderVolumeVar()),
                                DoubleDecimalDigits.transition(2, cvsDown.getLowExtremeVariance())
                        ));

                    }
                }
            }
        }
    }

    private synchronized int nextId() {
        return id++;
    }

    /**
     * 第一次出现突破标识日为最近一个交易日
     * 当 orientation 为 DOWN 时，最近一个交易日的低点第一次小于近期高点的最低点（突破日），反之亦然
     * 【过滤条件太苛刻，不容易找到结果，应适当增加 redundancy 冗余值】
     *
     * @param list        按照日期倒序排序的原始 list
     * @param mergedList  合并后的 list
     * @param orientation 趋势方向
     * @param redundancy  可冗余的条件，即最近一个交易日前允许存在几个突破日
     * @return 返回
     */
    public boolean firstBreakthrough(List<BeanOfHistData> list, List<BeanOfHistData> mergedList, String orientation, int redundancy) {
        boolean flag = false;
        String mergedListLastTime = mergedList.get(0).getTime();
        int latestExtremeIntervalDays = new IntervalDaysCalc().intervalDays(mergedListLastTime);
        if (orientation.equals("DOWN")
                && list.get(0).getLow() < mergedList.get(0).getLow()
                && list.get(0).getHigh() < mergedList.get(0).getHigh()
                && list.get(0).getOpen() > list.get(0).getClose()
                && latestExtremeIntervalDays <= redundancy
                && latestExtremeIntervalDays >= 1
        ) {
            return true;
        } else return orientation.equals("UP")
                && list.get(0).getHigh() > mergedList.get(0).getHigh()
                && list.get(0).getLow() > mergedList.get(0).getLow()
                && list.get(0).getOpen() < list.get(0).getClose()
                && latestExtremeIntervalDays <= redundancy
                && latestExtremeIntervalDays >= 1;
//        return flag;
    }

    /**
     * 判断 mergeList 的第一个元素是高点极值还是低点极值的 list
     *
     * @param lowsList  低点极值的 list
     * @param mergeList 合并后极值 list
     * @return 极值列表头表示
     */
    public String extremeHeader(List<BeanOfHistData> lowsList, List<BeanOfHistData> mergeList) {
        if (mergeList.get(0) == lowsList.get(0)) {
            return "LOW";
        } else return "HIGH";
    }

    public boolean isOrderedByLow(List<BeanOfHistData> list) {
        if (list.get(0).getLow() > list.get(list.size() - 1).getLow()) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getLow() > list.get(i + 1).getLow()) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getLow() < list.get(i + 1).getLow()) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean isOrderedByHigh(List<BeanOfHistData> list) {
        if (list.get(0).getHigh() > list.get(list.size() - 1).getHigh()) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getHigh() > list.get(i + 1).getHigh()) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getHigh() < list.get(i + 1).getHigh()) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    public List<BeanOfHistData> mergeList(List<BeanOfHistData> list1, List<BeanOfHistData> list2) {
        List<BeanOfHistData> list = new ArrayList<>();
        int count1 = 0;
        int count2 = 0;
        if (list1.get(0).getTime().compareTo(list2.get(0).getTime()) > 0) {
            for (int i = 0; i < list1.size() + list2.size(); i++) {
                if (i % 2 == 0) {
                    list.add(list1.get(count1));
                    count1++;
                } else {
                    list.add(list2.get(count2));
                    count2++;
                }
            }
            return list;
        } else {
            for (int i = 0; i < list1.size() + list2.size(); i++) {
                if (i % 2 == 0) {
                    list.add(list2.get(count2));
                    count2++;
                } else {
                    list.add(list1.get(count1));
                    count1++;
                }
            }
            return list;
        }
    }

    public boolean isOrderedByTime(List<BeanOfHistData> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getTime().compareTo(list.get(i + 1).getTime()) > 0) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public String orientate(List<BeanOfHistData> list) {
        if (list.get(0).getLow() > list.get(list.size() - 2).getLow()) {
            return "UP";
        } else {
            return "DOWN";
        }
    }

    /**
     * 这是一个判断每个单元极值间（一个高点极值到一个低点极值间）的振幅是否大于或等于配置文件里设定的期望振幅值的方法。
     * @param mergeList 合并后的极值列表
     * @param expectedValue 期望单元极值高点和低点键的振幅
     * @return 如果振幅大于或等于期望的振幅值，返回真，否则返回假
     */
    public boolean hasExtremeQuoteChange(List<BeanOfHistData> mergeList, double expectedValue) {
        List<List<BeanOfHistData>> doubleElementList = new ArrayList<>();
        for(int i = 0; i <= mergeList.size() / 2 + 1; i = i + 2) {
            List<BeanOfHistData> tempList = mergeList.subList(i, i + 2);
            doubleElementList.add(tempList);
        }

        for(List<BeanOfHistData> list: doubleElementList) {
            BeanOfHistData b1 = list.get(0);
            BeanOfHistData b2 = list.get(1);
            if (b1.getLow() > b2.getLow()) {
                // 下跌趋势
                double quoteChangeValue = (b1.getHigh() - b2.getLow()) / b2.getOpen();
                if (quoteChangeValue < expectedValue / 100) {
                    break;
                } else {
                    return true;
                }
            } else if(b1.getLow() < b2.getLow()) {
                // 上涨趋势
                double quoteChangeValue = (b2.getHigh() - b1.getLow()) / b1.getOpen();
                if (quoteChangeValue < expectedValue / 100) {
                    break;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}

