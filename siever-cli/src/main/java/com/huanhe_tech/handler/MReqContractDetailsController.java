package com.huanhe_tech.handler;

enum MReqContractDetailsController {
    INSTANCE(null);
    private String symbol;

    MReqContractDetailsController(String symbol) {
        this.symbol = symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public synchronized void reqContractDetails() {
        MConnectHandler.INSTANCE.controller().reqContractDetails(
                ContractWithSTK.NNContractForReqDetails(symbol),
                MContractDetailsHandler.INSTANCE
        );
    }
}
