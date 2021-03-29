package com.huanhe_tech.cli;

public class ReqContractDetailsController {
    private String symbol;

    public ReqContractDetailsController(String symbol) {
        this.symbol = symbol;
    }

    public ReqContractDetailsController() {
    }

    public void reqContractDetails() {
        InstancePool.getConnectionController().reqContractDetails(
                ContractWithSTK.NNContractForReqDetails(symbol),
                new ContractDetailsHandler()
        );
    }

}
