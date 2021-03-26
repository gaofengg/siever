package com.huanhe_tech.handler;

import com.ib.client.ContractDetails;
import com.ib.controller.ApiController;

import java.util.List;

enum MContractDetailsHandler implements ApiController.IContractDetailsHandler {
    INSTANCE;
    private final MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;
    @Override
    public void contractDetails(List<ContractDetails> list) {
        synchronized (mGlobalSettings) {

            boolean hasNN = list.stream().anyMatch(item -> {
                String s = item.contract().primaryExch();
                boolean b = s.contains("NASDAQ") || s.contains("NYSE");
                MGlobalSettings.INSTANCE.setNN(b);

                return b;
            });

            mGlobalSettings.notifyAll();
        }


//        System.out.println("MG: " + MGlobalSettings.INSTANCE.hasNN());
//        System.out.println(Thread.currentThread());
    }

}
