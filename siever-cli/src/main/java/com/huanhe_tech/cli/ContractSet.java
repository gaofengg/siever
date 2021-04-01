package com.huanhe_tech.cli;

import com.ib.client.Contract;

public class ContractSet {
    public static Contract USStockWithPrimaryExch(String symbol) {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("STK");
        contract.currency("USD");
        contract.exchange("SMART");
        contract.primaryExch("ISLAND");
        return contract;
    }
}
