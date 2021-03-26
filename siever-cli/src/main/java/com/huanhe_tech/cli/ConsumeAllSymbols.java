package com.huanhe_tech.cli;

public class ConsumeAllSymbols {
    private final AllSymbolsQueue2 allSymbolsQueue2;

    public ConsumeAllSymbols(AllSymbolsQueue2 allSymbolsQueue2) {
        this.allSymbolsQueue2 = allSymbolsQueue2;
    }

    public void takeFlowingSymbolFormQueue() {
        while (true) {
            FlowingSymbol flowingSymbol = allSymbolsQueue2.take();

//            System.out.println("消费者消费了 " + flowingSymbol);
        }
    }
}
