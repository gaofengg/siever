package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.ArrayList;
import java.util.List;

public class CalcExtremes {
    private final List<BeanOfHistData> list;
    private final int pileNumbers, extremeNumbers;
    List<BeanOfHistData> lowsList = new ArrayList<>();
    List<BeanOfHistData> highList = new ArrayList<>();

    /**
     * @param pileNumbers    计算极值时，极值左右两边桩值的数量（单边数量）
     *                       桩值：计算 low 极值时，两边的桩值必须大于 low 极值，计算 high 极值反之
     * @param extremeNumbers 极值数列里最多保存的极值对象数量
     */
    public CalcExtremes(List<BeanOfHistData> list, int pileNumbers, int extremeNumbers) {
        this.list = list;
        this.pileNumbers = pileNumbers;
        this.extremeNumbers = extremeNumbers;
    }

    /**
     *
     * @return 返回保存了极值对象的 list
     */
    public List<BeanOfHistData> findLows() {

        continueOUt:
        for (int i = 0; i < list.size()- pileNumbers; i++) {
            int j = 1;
            int k = 1;
            if (i <= pileNumbers) {
                if (i == 0) {
                    // 左侧
                    while (j <= pileNumbers) {
                        if (list.get(i + j).getLow() < list.get(i).getLow()) {
                            continue continueOUt;
                        } else {
                            j++;
                        }
                    }
                } else {
                    // 左侧
                    while (j <= pileNumbers) {
                        if (list.get(i + j).getLow() < list.get(i).getLow()) {
                            continue continueOUt;
                        } else {
                            j++;
                        }
                    }
                    // 变量右侧
                    while (k <= i && k > 0) {
                        if (list.get(k - 1).getLow() < list.get(i).getLow()) {
                            continue continueOUt;
                        } else {
                            k++;
                        }
                    }
                }
            } else {

                // 左侧
                while (j <= pileNumbers) {
                    if (list.get(i + j).getLow() < list.get(i).getLow()) {
                        continue continueOUt;
                    } else {
                        j++;
                    }
                }

                // 右侧
                while (k <= pileNumbers) {
                    if (list.get(i - k).getLow() < list.get(i).getLow()) {
                        continue continueOUt;
                    } else {
                        k++;
                    }
                }
            }

            lowsList.add(list.get(i));
            if (lowsList.size() >= extremeNumbers) break;
        }

        return lowsList;
    }

    public List<BeanOfHistData> findHigh() {

        continueOUt:
        for (int i = 0; i < list.size() - pileNumbers; i++) {
            int j = 1;
            int k = 1;
            if (i <= pileNumbers) {
                if (i == 0) {
                    // 左侧
                    while (j <= pileNumbers) {
                        if (list.get(i + j).getHigh() > list.get(i).getHigh()) {
                            continue continueOUt;
                        } else {
                            j++;
                        }
                    }
                } else {
                    // 左侧
                    while (j <= pileNumbers) {
                        if (list.get(i + j).getHigh() > list.get(i).getHigh()) {
                            continue continueOUt;
                        } else {
                            j++;
                        }
                    }
                    // 变量右侧
                    while (k <= i && k > 0) {
                        if (list.get(k - 1).getHigh() > list.get(i).getHigh()) {
                            continue continueOUt;
                        } else {
                            k++;
                        }
                    }
                }
            } else {

                // 左侧
                while (j <= pileNumbers) {
                    if (list.get(i + j).getHigh() > list.get(i).getHigh()) {
                        continue continueOUt;
                    } else {
                        j++;
                    }
                }

                // 右侧
                while (k <= pileNumbers) {
                    if (list.get(i - k).getHigh() > list.get(i).getHigh()) {
                        continue continueOUt;
                    } else {
                        k++;
                    }
                }
            }

            highList.add(list.get(i));
            if (highList.size() >= extremeNumbers) break;
        }

        return highList;
    }

}
