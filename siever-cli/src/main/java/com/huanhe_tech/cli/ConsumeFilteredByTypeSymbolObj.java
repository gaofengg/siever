package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.FilteredByTypeFlowingSymbol;
import com.huanhe_tech.cli.queue.FiltrateBySymbolTypeQueue;
import com.huanhe_tech.cli.queue.FlowingSymbolObj;
import com.huanhe_tech.cli.req.HistDataHandler;
import com.huanhe_tech.cli.req.ReqData;
import com.ib.controller.Bar;

import java.util.function.Function;
import java.util.stream.Stream;

public class ConsumeFilteredByTypeSymbolObj {
    private final FiltrateBySymbolTypeQueue filtrateBySymbolTypeQueue;
    private FilteredByTypeFlowingSymbol flowingSymbolObj;
    private final GlobalFlags.ReqHistoricalFlag reqHistoricalFlag = GlobalFlags.ReqHistoricalFlag.STATE;

    public ConsumeFilteredByTypeSymbolObj(FiltrateBySymbolTypeQueue filtrateBySymbolTypeQueue) {
        this.filtrateBySymbolTypeQueue = filtrateBySymbolTypeQueue;
    }

    public void takeFilteredByTypeSymbolObj() {

        synchronized (reqHistoricalFlag) {
            while (reqHistoricalFlag.getState()) {
                flowingSymbolObj = filtrateBySymbolTypeQueue.takeSymbolObj();
                reqHistoricalFlag.setState(false);
                reqHistData();
                reqHistoricalFlag.notifyAll();
            }
            try {
                reqHistoricalFlag.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void reqHistData() {
        synchronized (reqHistoricalFlag) {
            if (!reqHistoricalFlag.getState()) {
                InstancePool.getServiceSet().reqHistData(flowingSymbolObj, fs -> {
                    ReqData.REQ_HIST.setSymbol(fs.getSymbol()).setConid(fs.getConid()).reqHistAndHandleData();
                    reqHistoricalFlag.setState(true);
                });
            } else {
                try {
                    reqHistoricalFlag.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
