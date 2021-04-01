package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.FiltrateBySymbolTypeQueue;
import com.huanhe_tech.cli.queue.FlowingSymbolObj;
import com.huanhe_tech.cli.req.HistDataHandler;
import com.huanhe_tech.cli.req.ReqData;
import com.ib.controller.Bar;

import java.util.function.Function;
import java.util.stream.Stream;

public class ConsumeFilteredByTypeSymbolObj {
    private final FiltrateBySymbolTypeQueue filtrateBySymbolTypeQueue;
    private FlowingSymbolObj flowingSymbolObj;
    private final GlobalFlags.ReqHistoricalFlag reqHistoricalFlag = GlobalFlags.ReqHistoricalFlag.STATE;

    public ConsumeFilteredByTypeSymbolObj(FiltrateBySymbolTypeQueue filtrateBySymbolTypeQueue) {
        this.filtrateBySymbolTypeQueue = filtrateBySymbolTypeQueue;
    }

    public void takeFilteredByTypeSymbolObj() {

        while (true) {
            synchronized (reqHistoricalFlag) {
                if (reqHistoricalFlag.getState()) {
                    flowingSymbolObj = filtrateBySymbolTypeQueue.takeSymbolObj();
                    reqHistoricalFlag.setState(false);
                    System.out.println(Thread.currentThread().getName() + "Type 拿到了：" + flowingSymbolObj.toString());
                    reqHistData();
                    reqHistoricalFlag.notifyAll();
                } else {
                    System.out.println("****");
                    try {
                        reqHistoricalFlag.wait(3000);
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public synchronized void reqHistData() {
        InstancePool.getServiceSet().reqHistData(flowingSymbolObj, fs -> {
            ReqData.REQ_HIST.setSymbol(fs.getSymbol()).reqHistAndHandleData();
        });
    }

}
