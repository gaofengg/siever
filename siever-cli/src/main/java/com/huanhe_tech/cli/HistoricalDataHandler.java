package com.huanhe_tech.cli;

import com.ib.controller.ApiController;
import com.ib.controller.Bar;

public class HistoricalDataHandler implements ApiController.IHistoricalDataHandler {
    private final String symbol;

    public HistoricalDataHandler(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void historicalData(Bar bar) {
//        System.out.println("SYMBOL" + "\t" + "OPEN" + "\t" + "CLOSE" + "\t" + "TIME               " + "\t" + "aa");
//        System.out.println(symbol + "\t" + bar.high() + "\t" + bar.low() + "\t" + bar.formattedTime() + "\t" + "bb");
        System.out.printf("%10s %10s %10s %22s", "SYMBOL", "OPEN", "CLOSE", "TIME\n");
        System.out.printf("%10s %10s %10s %22s", symbol, bar.high(), bar.low(), bar.formattedTime());
    }

    @Override
    public void historicalDataEnd() {

    }
}
