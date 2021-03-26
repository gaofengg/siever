package com.huanhe_tech.siever;

import com.huanhe_tech.cli.AllSymbolsQueue2;
import com.huanhe_tech.cli.ConsumeAllSymbols;
import com.huanhe_tech.cli.ProduceAllSymbols;
import com.huanhe_tech.handler.*;

public class Main {
    private final static MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;


    public static void main(String[] args) {
        AllSymbolsQueue2 allSymbolsQueue2 = new AllSymbolsQueue2(5);
        ProduceAllSymbols produceAllSymbols = new ProduceAllSymbols("test.txt", allSymbolsQueue2);
        ConsumeAllSymbols consumeAllSymbols = new ConsumeAllSymbols(allSymbolsQueue2);

//        MConnectHandler.INSTANCE.connect();
//        MIterator mIterator = new MIterator();
//        mIterator.getSymbolFromFile();

//        mIterator.echoHistoricalData();

//        synchronized (mGlobalSettings) {
//
//            MReqContractDetailsController aapl = new MReqContractDetailsController("AA");
//
//            aapl.reqContractDetails();
//            try {
//                mGlobalSettings.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

//        System.out.println("Main: " + MGlobalSettings.INSTANCE.hasNN());
//        MConnectHandler.INSTANCE.disconnect();

        new Thread(produceAllSymbols::putFlowingSymbolsToQueue).start();
        for (int i = 0; i < 3; i++) {
            new Thread(consumeAllSymbols::takeFlowingSymbolFormQueue).start();
        }
    }
}
