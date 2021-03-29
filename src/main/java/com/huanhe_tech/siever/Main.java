package com.huanhe_tech.siever;

import com.huanhe_tech.cli.ConsumeAllSymbols;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.ProduceAllSymbols;
import com.huanhe_tech.handler.MConnectHandler;
import com.huanhe_tech.handler.MIterator;

public class Main {
//    private final static MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;


    public static void main(String[] args) {
//        ProduceAllSymbols produceAllSymbols = InstancePool.getProduceAllSymbols("usa.txt");
//        ConsumeAllSymbols consumeAllSymbols = InstancePool.getConsumeAllSymbols();

        // 单线程测试
//        MConnectHandler.INSTANCE.connect();
//        MIterator mIterator = new MIterator();
//        mIterator.getSymbolFromFile();


        // 多线程队列优化测试
//        InstancePool.getConnectionController().connect();
//        new Thread(produceAllSymbols::putFlowingSymbolsToQueue).start();
//        new Thread(consumeAllSymbols::takeFlowingSymbolFormQueue).start();

        InstancePool.getMainExecutor().run();

    }
}
