package com.huanhe_tech.cli;

import com.huanhe_tech.handler.MGlobalSettings;
import com.ib.client.ContractDetails;
import com.ib.controller.ApiController;

import java.util.List;

public class ContractDetailsHandler implements ApiController.IContractDetailsHandler {
    private static int id = 0;
    private final MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;

    @Override
    public void contractDetails(List<ContractDetails> list) {

        boolean hasNN = list.stream().anyMatch(item -> {
            String s = item.contract().primaryExch();
            boolean b = s.contains("NASDAQ") || s.contains("NYSE");
            if (b) {
                System.out.println(Thread.currentThread().getName() + "------" + nextId() + ": " + item.contract().symbol());
            }
            return b;
        });

    }

    private static synchronized int nextId() {
        return id++;
    }

}
