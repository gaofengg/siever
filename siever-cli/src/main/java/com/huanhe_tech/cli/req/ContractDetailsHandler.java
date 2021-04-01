package com.huanhe_tech.cli.req;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.queue.FilteredByTypeFlowingSymbol;
import com.ib.client.ContractDetails;
import com.ib.controller.ApiController;

import java.util.List;

public class ContractDetailsHandler implements ApiController.IContractDetailsHandler {
    private static int id = 0;
    private final GlobalFlags.ReqTypeFlag reqTypeFlag = GlobalFlags.ReqTypeFlag.STATE;

    @Override
    public synchronized void contractDetails(List<ContractDetails> list) {

        synchronized (reqTypeFlag) {
            if (!reqTypeFlag.getState()) {

                boolean hasNN = list.stream().anyMatch(item -> {
                    String s = item.contract().primaryExch();
                    boolean b = s.contains("NASDAQ") || s.contains("NYSE");
                    if (b) {
                        FilteredByTypeFlowingSymbol filteredByTypeFlowingSymbol = new FilteredByTypeFlowingSymbol(nextId(), item.contract().symbol());
                        InstancePool.getFiltrateBySymbolTypeQueue().putSymbolObj(filteredByTypeFlowingSymbol);
//                        System.out.println(Thread.currentThread().getName() + "------" + nextId() + ": " + item.contract().symbol());
                    }
                    return b;
                });

                reqTypeFlag.setState(true);
                reqTypeFlag.notifyAll();

            } else {
                try {
                    reqTypeFlag.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    private static synchronized int nextId() {
        return id++;
    }

}
