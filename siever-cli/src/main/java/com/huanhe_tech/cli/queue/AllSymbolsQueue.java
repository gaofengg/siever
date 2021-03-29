package com.huanhe_tech.cli.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class AllSymbolsQueue extends ArrayBlockingQueue<AllFlowingSymbol> {
    private AllFlowingSymbol flowingSymbol;

    public AllSymbolsQueue(int capacity) {
        super(capacity);
    }

    // 存入 Symbol
    public void putSymbol(AllFlowingSymbol flowingSymbol) {
        try {
            super.put(flowingSymbol);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取 Symbol
    public AllFlowingSymbol takeSymbol() {
        try {
            flowingSymbol = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flowingSymbol;
    }
}
