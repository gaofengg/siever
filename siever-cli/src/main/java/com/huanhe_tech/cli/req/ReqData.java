package com.huanhe_tech.cli.req;

import com.huanhe_tech.cli.ContractSet;
import com.huanhe_tech.cli.ContractWithSTK;
import com.huanhe_tech.cli.InstancePool;
import com.ib.client.Types;

public enum ReqData {
    REQ_TYPE(null, 0),
    REQ_HIST(null, 0);

    private String symbol;
    private int conid;

    ReqData(String symbol, int conid) {
        this.symbol = symbol;
        this.conid = conid;
    }

    public ReqData setSymbol(String symbol) {
        this.symbol = symbol;
        return ReqData.this;
    }

    public ReqData setConid(int conid) {
        this.conid = conid;
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
                2,
                Types.DurationUnit.DAY,
                Types.BarSize._1_day,
                Types.WhatToShow.TRADES,
                true,
                false,
                new HistDataHandler(symbol, conid)
        );

    }

}
