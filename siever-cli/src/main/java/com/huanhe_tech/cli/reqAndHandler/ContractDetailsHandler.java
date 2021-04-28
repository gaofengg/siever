package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.queue.FilteredByTypeFlowingSymbol;
import com.ib.client.ContractDetails;
import com.ib.controller.ApiController;

import java.util.List;

public class ContractDetailsHandler implements ApiController.IContractDetailsHandler {
    private static int id = 1;
    private final String symbol;
    private final GlobalFlags.ReqTypeFlag reqTypeFlag = GlobalFlags.ReqTypeFlag.STATE;

    public ContractDetailsHandler(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public synchronized void contractDetails(List<ContractDetails> list) {

        synchronized (reqTypeFlag) {
            if (!reqTypeFlag.getState()) {

                boolean hasNN = list.stream().anyMatch(item -> {
                    String s = item.contract().primaryExch();
                    boolean b = s.contains("NASDAQ") || s.contains("NYSE");
                    if (b) {
                        FilteredByTypeFlowingSymbol filteredByTypeFlowingSymbol = new FilteredByTypeFlowingSymbol(nextId(), item.contract().symbol(), item.contract().conid());
                        InstancePool.getFiltrateBySymbolTypeQueue().putSymbolObj(filteredByTypeFlowingSymbol);
                    }
                    return b;
                });

                reqTypeFlag.setState(true);
                reqTypeFlag.notifyAll();

            } else {
                if (!symbol.equals("#EOF")) {
                    try {
                        reqTypeFlag.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    reqTypeFlag.notifyAll();
                }
            }
        }
    }

    private static synchronized int nextId() {
        return id++;
    }

}
