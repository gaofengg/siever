package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.AllFlowingSymbol;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.huanhe_tech.handler.MGlobalSettings;

public class ConsumeAllSymbols {
    private final AllSymbolsQueue allSymbolsQueue;
    private final MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;
    private AllFlowingSymbol flowingSymbol;

    public ConsumeAllSymbols(AllSymbolsQueue allSymbolsQueue) {
        this.allSymbolsQueue = allSymbolsQueue;
    }

    public void takeFlowingSymbolFormQueue() {
        if (flowingSymbol.getId() % 200 != 0) {
            while (InstancePool.getConnectionController().checkConnection()) {

                flowingSymbol = allSymbolsQueue.takeSymbol();
                InstancePool.getFilterServiceSet().filtrateByPrimaryExchWithNN(flowingSymbol, fs -> {
                    new ReqContractDetailsController(fs.getSymbol()).reqContractDetails();
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            InstancePool.getConnectionController().disconnect();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            InstancePool.getMainExecutor().run();
        }
    }

}
