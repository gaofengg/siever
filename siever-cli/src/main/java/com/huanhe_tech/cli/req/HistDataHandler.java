package com.huanhe_tech.cli.req;

import com.huanhe_tech.cli.GlobalFlags;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;

public class HistDataHandler implements ApiController.IHistoricalDataHandler {
    private final GlobalFlags.ReqHistoricalFlag reqHistoricalFlag = GlobalFlags.ReqHistoricalFlag.STATE;
    private String symbol;

    public HistDataHandler setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    @Override
    public void historicalData(Bar bar) {
        System.out.println("aa");
        synchronized (reqHistoricalFlag) {
            if (!reqHistoricalFlag.getState()) {
                    System.out.printf("%10s %10s %10s %22s", symbol, bar.high(), bar.low(), bar.formattedTime() + Thread.currentThread().getName() + "\n");
                    reqHistoricalFlag.setState(true);
                    reqHistoricalFlag.notifyAll();
            } else {
                try {
                    reqHistoricalFlag.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void historicalDataEnd() {
        System.out.println("\nExtraction Finish!");
    }
}
