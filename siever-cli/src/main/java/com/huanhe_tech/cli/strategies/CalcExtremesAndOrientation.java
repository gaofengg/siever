package com.huanhe_tech.cli.strategies;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.ArrayList;
import java.util.List;

public class CalcExtremesAndOrientation {
    private final List<BeanOfHistData> list;
    List<BeanOfHistData> lowsList = new ArrayList<>();

    public CalcExtremesAndOrientation(List<BeanOfHistData> list) {
        this.list = list;
    }

    /**
     * @param pileNumbers    计算极值时，极值左右两边桩值的数量（单边数量）
     *                       桩值：计算 low 极值时，两边的桩值必须大于 low 极值，计算 high 极值反之
     * @param extremeNumbers 极值数列里最多保存的极值对象数量
     * @return 返回保存了极值对象的 list
     */
    public List<BeanOfHistData> findLows(int pileNumbers, int extremeNumbers) {

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

}
