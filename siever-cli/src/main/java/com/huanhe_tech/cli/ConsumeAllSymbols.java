package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.huanhe_tech.cli.queue.FlowingSymbolObj;
import com.huanhe_tech.cli.req.ReqData;

public class ConsumeAllSymbols {
    private final AllSymbolsQueue allSymbolsQueue;
    private FlowingSymbolObj flowingSymbol;
    private final GlobalFlags.ReqTypeFlag reqTypeFlag = GlobalFlags.ReqTypeFlag.STATE;

    public ConsumeAllSymbols(AllSymbolsQueue allSymbolsQueue) {
        this.allSymbolsQueue = allSymbolsQueue;
    }

    public void takeFlowingSymbolFormQueue() {
        InstancePool.getConnectionController().connect();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (reqTypeFlag) {
            do {
                if (!InstancePool.getConnectionController().client().isConnected()) {
                    System.out.println("重新链接 ...");
                    reConnection();
                }
                if (reqTypeFlag.getState()) {
                    flowingSymbol = allSymbolsQueue.takeSymbol();
                    reqTypeFlag.setState(false);
                    reqSymbolDetails();
                    reqTypeFlag.notifyAll();
                } else {
                    try {
                        reqTypeFlag.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } while (true);
        }
    }


    public synchronized void reqSymbolDetails() {

        InstancePool.getServiceSet().filtrateBy(flowingSymbol, fs ->
                ReqData.REQ_TYPE.setSymbol(fs.getSymbol()).reqContractDetails()
        );
    }


    public void reConnection() {
        if (InstancePool.getConnectionController().client().isConnected()) {
            InstancePool.getConnectionController().disconnect();
        }
        InstancePool.getConnectionController().connect();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
