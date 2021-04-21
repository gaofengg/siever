package com.huanhe_tech.cli.req;

import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.queue.HistDataObjForIn;
import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;

import java.util.ArrayList;
import java.util.List;

public class HistDataHandler implements ApiController.IHistoricalDataHandler {
    private final String symbol;
    private final long conid;
    private volatile int dataId; // listOfHistDataIn 里的 id
    private static volatile int objectId; // 加入到 queue 里的 id
    List<BeanOfHistData> listOfHistDataIn = new ArrayList<>();

    public HistDataHandler(String symbol, long conid) {
        this.symbol = symbol;
        this.conid = conid;
    }

    @Override
    public void historicalData(Bar bar) {
        listOfHistDataIn.add(new BeanOfHistData(
                nextDataId(),
                symbol,
                conid,
                bar.formattedTime(),
                bar.open(),
                bar.high(),
                bar.low(),
                bar.close(),
                bar.volume(),
                bar.wap(),
                bar.count()
        ));

//        System.out.printf("%10s %10s %10s %22s %10s", symbol, bar.high(), bar.low(), bar.formattedTime(), bar.count() + "----" + nextDataId() + "\n");
    }

    @Override
    public void historicalDataEnd() {
//        System.out.println("\nExtraction Finish!\t" + symbol);

        HistDataObjForIn histDataObjForIn = new HistDataObjForIn(nextObjectId(), conid, listOfHistDataIn);

        try {
            InstancePool.getHistDataQueue().put(histDataObjForIn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private synchronized int nextDataId() {
        return dataId++;
    }

    private synchronized int nextObjectId() {
        return objectId++;
    }

}