package com.huanhe_tech.cli.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *  这是一个队列容器，
 *  输入：从 usa.txt 文件中逐行读取的全部 symbol 数据
 *  输出：完整 symbol 数据从头部输出，并过滤出 NASDAQ 和 NYSE 的股票，
 *  然后输出到下一个队列 (FiltrateBySymbolTypeQueue)
 */
public class AllSymbolsQueue extends ArrayBlockingQueue<FlowingSymbolObj> {
    private FlowingSymbolObj flowingSymbol;

    public AllSymbolsQueue(int capacity) {
        super(capacity);
    }

    // 存入 Symbol
    public void putSymbol(FlowingSymbolObj flowingSymbol) {
        try {
            super.put(flowingSymbol);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取 Symbol
    public FlowingSymbolObj takeSymbol() {
        try {
            flowingSymbol = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flowingSymbol;
    }
}
