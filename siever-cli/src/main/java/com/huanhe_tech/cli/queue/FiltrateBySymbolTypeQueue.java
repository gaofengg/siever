package com.huanhe_tech.cli.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *  该类的作用：
 *  从前一道过滤队列（如过滤出仅包含 NASDAQ 和 NYSE 的股票）输出的 symbol 对象，
 *  输入到当前队列中，等待获取历史数据和计算处理
 */
public class FiltrateBySymbolTypeQueue extends ArrayBlockingQueue<FilteredByTypeFlowingSymbol> {
    private FilteredByTypeFlowingSymbol filteredByTypeFlowingSymbol;

    public FiltrateBySymbolTypeQueue(int capacity) {
        super(capacity);
    }

    public void putSymbolObj(FilteredByTypeFlowingSymbol filteredByTypeFlowingSymbol) {
        try {
            super.put(filteredByTypeFlowingSymbol);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FilteredByTypeFlowingSymbol takeSymbolObj() {
        try {
            filteredByTypeFlowingSymbol = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return filteredByTypeFlowingSymbol;
    }
}