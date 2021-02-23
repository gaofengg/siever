package com.huanhe_tech.cli;

import com.ib.controller.ApiController;
import com.ib.controller.Bar;

public class HistoricalDataHandler implements ApiController.IHistoricalDataHandler {

    @Override
    public void historicalData(Bar bar) {
        System.out.println("open: $" + bar.high());
        System.out.println("close: $" + bar.low());
    }

    @Override
    public void historicalDataEnd() {

    }
}
