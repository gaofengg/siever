package com.huanhe_tech.handler;

public class MReqContractDetailsController {
    private final String symbol;

    public MReqContractDetailsController(String symbol) {
        this.symbol = symbol;
    }

    public synchronized void reqContractDetails() {
        MConnectHandler.INSTANCE.controller().reqContractDetails(ContractWithSTK.TempContractForReqDetails(symbol), new MContractDetailsHandler());
    }
}
