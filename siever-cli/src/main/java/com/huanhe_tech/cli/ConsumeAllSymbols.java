package com.huanhe_tech.cli;

import com.huanhe_tech.cli.connection.Reconnection;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.huanhe_tech.cli.queue.FilteredByTypeFlowingSymbol;
import com.huanhe_tech.cli.queue.FlowingSymbolObj;
import com.huanhe_tech.cli.reqAndHandler.ReqData;
import com.huanhe_tech.siever.utils.SymbolsSourceHandler;

public class ConsumeAllSymbols {
    private final AllSymbolsQueue allSymbolsQueue;
    private FlowingSymbolObj flowingSymbol;
    private final GlobalFlags.ReqTypeFlag reqTypeFlag = GlobalFlags.ReqTypeFlag.STATE;

    public ConsumeAllSymbols(AllSymbolsQueue allSymbolsQueue) {
        this.allSymbolsQueue = allSymbolsQueue;
    }

    public void takeFlowingSymbolFormQueue() {

        synchronized (reqTypeFlag) {
            do {
                if (!InstancePool.getConnectionController().client().isConnected()) {
                    System.out.println("Reconnecting ...");
                    new Reconnection();
                }
                if (reqTypeFlag.getState()) {
                    flowingSymbol = allSymbolsQueue.takeSymbol();
                    reqTypeFlag.setState(false);
                    reqSymbolDetails();
                    reqTypeFlag.notifyAll();
                } else {
                    if (!flowingSymbol.getSymbol().equals("#EOF")) {
                        try {
                            reqTypeFlag.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        SymbolsSourceHandler.setNewSlmt();
                        InstancePool.getFiltrateBySymbolTypeQueue().putSymbolObj(new FilteredByTypeFlowingSymbol(20000, "#EOF", 0));
                        InstancePool.getConnectionController().disconnect();
                        break;
                    }
                }
            } while (true);

        }
    }


    private synchronized void reqSymbolDetails() {

        InstancePool.getServiceSet().filtrateBy(flowingSymbol, fs ->
                ReqData.REQ_TYPE.setSymbol(fs.getSymbol()).reqContractDetails()
        );
    }

}
