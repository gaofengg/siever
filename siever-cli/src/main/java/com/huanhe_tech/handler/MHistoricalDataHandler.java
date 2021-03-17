package com.huanhe_tech.handler;

import com.ib.controller.ApiController;
import com.ib.controller.Bar;

public class MHistoricalDataHandler implements ApiController.IHistoricalDataHandler {
    private String symbol;

    public MHistoricalDataHandler(String symbol) {
        this.symbol = symbol;
    }

    public MHistoricalDataHandler() {
    }

    @Override
    public void historicalData(Bar bar) {
        System.out.printf("%10s %10s %10s %22s", symbol, bar.high(), bar.low(), bar.formattedTime() + "\n");
    }

    @Override
    public void historicalDataEnd() {
        System.out.println("\nExtraction Finish!");
        MGlobalSettings.INSTANCE.setReqHistoricalComplete(true);
        MGlobalSettings.INSTANCE.setSymbolListIndex(1);
    }

}
