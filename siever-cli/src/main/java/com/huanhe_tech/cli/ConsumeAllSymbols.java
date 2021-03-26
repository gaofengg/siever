package com.huanhe_tech.cli;

public class ConsumeAllSymbols {
    private final AllSymbolsQueue allSymbolsQueue;

    public ConsumeAllSymbols(AllSymbolsQueue allSymbolsQueue) {
        this.allSymbolsQueue = allSymbolsQueue;
    }

    public void takeFlowingSymbolFormQueue() {
        while (true) {
            FlowingSymbol flowingSymbol = allSymbolsQueue.takeSymbol();
            System.out.println("消费者消费了 {" + flowingSymbol.getId() + ": " + flowingSymbol.getSymbol() + "}");
        }
    }
}
