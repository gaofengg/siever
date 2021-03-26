package com.huanhe_tech.cli;

import java.util.concurrent.ArrayBlockingQueue;

public class AllSymbolsQueue extends ArrayBlockingQueue<FlowingSymbol> {
    private FlowingSymbol flowingSymbol;

    public AllSymbolsQueue(int capacity) {
        super(capacity);
    }

    // 存入 Symbol
    public void putSymbol(FlowingSymbol flowingSymbol) {
        try {
            super.put(flowingSymbol);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取 Symbol
    public FlowingSymbol takeSymbol() {
        try {
            flowingSymbol = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flowingSymbol;
    }
}
