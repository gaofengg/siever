package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.*;

public class StrategyExtreme implements Strategy<List<BeanOfHistData>> {
    private int extremeNumbers = 3;
    private int pileNumbers = 2;

    public StrategyExtreme() {
    }

    /**
     * @param pileNumbers 计算极值时，极值左右两边桩值的数量（单边数量）
     *                    桩值：计算 low 极值时，两边的桩值必须大于 low 极值，计算 high 极值反之
     * @param extremeNumbers 极值数列里最多保存的极值对象数量
     *
     */
    public StrategyExtreme(int pileNumbers, int extremeNumbers) {
        this.pileNumbers = pileNumbers;
        this.extremeNumbers = extremeNumbers;
    }

    @Override
    public void run(List<BeanOfHistData> list) {
        String symbol = list.get(0).getSymbol();

        list.sort(Comparator.comparing(BeanOfHistData::getTime).reversed());
        List<BeanOfHistData> lowsList = new CalcExtremesAndOrientation(list).findLows(pileNumbers, extremeNumbers);
        if (lowsList.get(0).getLow() > lowsList.get(lowsList.size() - 1).getLow()) {
            System.out.println(symbol + " -> Orientation: UP");
        } else {
            System.out.println(symbol + " -> Orientation: DOWN");
        }
//        String orientation = new CalcExtremesAndOrientation(list).getOrientation();
        lowsList.forEach(System.out::println);
//        System.out.println(orientation);


    }

}
