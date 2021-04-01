package com.huanhe_tech.cli.req;

import com.huanhe_tech.cli.GlobalFlags;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;

public class HistDataHandler implements ApiController.IHistoricalDataHandler {
    private final String symbol;

    public HistDataHandler(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void historicalData(Bar bar) {
        System.out.printf("%10s %10s %10s %22s", symbol, bar.high(), bar.low(), bar.formattedTime() + "\n");
    }

    @Override
    public void historicalDataEnd() {
//        System.out.println("\nExtraction Finish!");
    }

}
