package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.*;

public class StrategyExtreme implements Strategy<List<BeanOfHistData>> {
    private int extremeNum = 2;
    private int calcArea = 29;

    public StrategyExtreme() {
    }

    /**
     * @param extremeNum 获取的极值数量，例如，如果该值为2，则取最高价和次高价，以及最低价和次低价。
     * @param calcArea   执行计算的天数，从最近日期向过去推算。
     */
    public StrategyExtreme(int extremeNum, int calcArea) {
        this.extremeNum = extremeNum;
        this.calcArea = calcArea;
    }

    @Override
    public void run(List<BeanOfHistData> list) {
        String symbol = list.get(0).getSymbol();
        List<BeanOfHistData> sortByLowList = new ArrayList<>();
        List<BeanOfHistData> sortByHighList = new ArrayList<>();
        System.out.println("策略运行了");

        Collections.reverse(list);
        System.out.println(list.get(0).getTime() + " -> " + list.get(0).getLow());
        System.out.println(list.get(calcArea).getTime() + " -> " + list.get(calcArea).getLow());
        int direction = Double.compare(list.get(0).getLow(), list.get(29).getLow());
        // 计算标的的趋势方向
        String orientation;
        if (direction > 0) {
            orientation = "UP";
        } else if (direction < 0) {
            orientation = "DOWN";
        } else {
            orientation = "PEER";
        }
        System.out.println(symbol + " -> Orientaion: " + orientation);
//        List<BeanOfHistData> sortByLowListSub = list.subList(0, extremeNum);
//        sortByLowListSub.forEach(System.out::println);


    }

}
