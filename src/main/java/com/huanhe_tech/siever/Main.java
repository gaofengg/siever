package com.huanhe_tech.siever;

import com.huanhe_tech.handler.*;

public class Main {
    private final static MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;

    public static void main(String[] args) {

        MConnectHandler.INSTANCE.connect();
        MIterator mIterator = new MIterator();
        mIterator.getSymbolFromFile();
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
    }
}
