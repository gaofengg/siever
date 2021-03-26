package com.huanhe_tech.handler;

import com.ib.client.Types;
import com.ib.controller.ApiController;

public enum MReqHistoricalDataController{
    INSTANCE(null);
    private MHistoricalDataHandler my_historicalDataHandler;
    private String symbol;
    MReqHistoricalDataController(String symbol) {
        this.symbol = symbol;
    }

//    public MDateTickController dateTickController(int i) {
//        if (my_dateTickController == null) {
//            my_dateTickController = new MDateTickController(i);
//        }
//        return my_dateTickController;
//    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public MHistoricalDataHandler historicalDataHandler(String symbol) {
        if (my_historicalDataHandler == null) {
            my_historicalDataHandler = new MHistoricalDataHandler(symbol);
        }
        return my_historicalDataHandler;
    }

    public void reqAndHandlerHistoricalData() {

        MConnectHandler.INSTANCE.controller().reqHistoricalData(
                ContractSet.USStockWithPrimaryExch(symbol),
                "",
                1,
                Types.DurationUnit.WEEK,
                Types.BarSize._1_day,
                Types.WhatToShow.BID,
                true,
                false,
                historicalDataHandler(symbol)
        );

    }
}
