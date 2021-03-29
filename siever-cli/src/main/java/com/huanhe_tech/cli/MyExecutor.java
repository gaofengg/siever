package com.huanhe_tech.cli;

public class MyExecutor {
    private final ProduceAllSymbols produceAllSymbols = InstancePool.getProduceAllSymbols("usa.txt");
    private final ConsumeAllSymbols consumeAllSymbols = InstancePool.getConsumeAllSymbols();

    public void run() {
        // 多线程队列优化测试
        InstancePool.getConnectionController().connect();
        new Thread(produceAllSymbols::putFlowingSymbolsToQueue).start();
        new Thread(consumeAllSymbols::takeFlowingSymbolFormQueue).start();
    }

}
