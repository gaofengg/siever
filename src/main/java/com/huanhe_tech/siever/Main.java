package com.huanhe_tech.siever;

import com.huanhe_tech.cli.*;

public class Main {
//    private final static MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;


    public static void main(String[] args) {
        ProduceAllSymbols produceAllSymbols = InstancePool.getProduceAllSymbols("usa.txt");
        ConsumeAllSymbols consumeAllSymbols = InstancePool.getConsumeAllSymbols();
        ConsumeFilteredByTypeSymbolObj consumeFilteredByTypeSymbolObj = InstancePool.getConsumeFilteredByTypeSymbolObj();

        // 单线程测试
//        MConnectHandler.INSTANCE.connect();
//        MIterator mIterator = new MIterator();
//        mIterator.getSymbolFromFile();


        // 多线程队列优化测试
//        InstancePool.getConnectionController().connect();
        new Thread(produceAllSymbols::putFlowingSymbolsToQueue, "Get all symbol thread").start();
        new Thread(consumeAllSymbols::takeFlowingSymbolFormQueue, "Filtrate type thread").start();
        new Thread(consumeFilteredByTypeSymbolObj::takeFilteredByTypeSymbolObj, "Req historical date thread").start();
        new Thread(() -> {
            new HistDataAnalyzerAndHandler().getHistDataFromQueue();
        }).start();

    }
}
