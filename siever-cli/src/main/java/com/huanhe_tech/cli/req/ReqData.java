package com.huanhe_tech.cli.req;

import com.huanhe_tech.cli.ContractSet;
import com.huanhe_tech.cli.ContractWithSTK;
import com.huanhe_tech.cli.InstancePool;
import com.ib.client.Types;

public enum ReqData {
    REQ_TYPE(null),
    REQ_HIST(null);

    private String symbol;

    ReqData(String symbol) {
        this.symbol = symbol;
    }

    public ReqData setSymbol(String symbol) {
        this.symbol = symbol;
        return ReqData.this;
    }

    public void reqContractDetails() {
        InstancePool.getConnectionController().reqContractDetails(
                ContractWithSTK.NNContractForReqDetails(symbol),
                InstancePool.getContractDetailsHandler()
        );
    }

    public void reqHistAndHandleData() {

        InstancePool.getConnectionController().reqHistoricalData(
                ContractSet.USStockWithPrimaryExch(symbol),
                "",
                1,
                Types.DurationUnit.DAY,
                Types.BarSize._1_day,
                Types.WhatToShow.BID,
                true,
                false,
//                InstancePool.getHistDataHandler(symbol)
                InstancePool.getHistDataHandler().setSymbol(symbol)
        );

    }

}