package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.beans.BeanOfHistData;
import com.huanhe_tech.cli.beans.BeanOfHistListInQueue;
import com.huanhe_tech.siever.utils.LLoger;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;

import java.util.ArrayList;
import java.util.List;

public class HistDataHandler implements ApiController.IHistoricalDataHandler {
    private long conid;
    private String symbol;
    private int intervalDays;
    private int id;
    private int queueIndexId;
    private final List<BeanOfHistData> beanOfHistDataList = new ArrayList<>();

    public HistDataHandler() {
    }

    public HistDataHandler(long conid, String symbol, int intervalDays) {
        this.conid = conid;
        this.symbol = symbol;
        this.intervalDays = intervalDays;
    }

    @Override
    public void historicalData(Bar bar) {
        BeanOfHistData beanOfHistData = new BeanOfHistData(nextId(), symbol, conid,
                bar.formattedTime(),
                bar.open(),
                bar.high(),
                bar.low(),
                bar.close(),
                bar.volume(),
                bar.wap(),
                bar.count());
        beanOfHistDataList.add(beanOfHistData);

    }

    @Override
    public void historicalDataEnd() {
        InstancePool.getQueueWithHistDataBean().put(new BeanOfHistListInQueue(nextQueueIndexId(),beanOfHistDataList));
        LLoger.logger.trace("{} -> {} {} days of historical data inserted.", symbol, conid, intervalDays);

        synchronized (GlobalFlags.UpdateHistDone.STATE) {
            GlobalFlags.UpdateHistDone.STATE.setB(true);
            GlobalFlags.UpdateHistDone.STATE.notifyAll();
        }
    }

    private synchronized int nextId() {
        return id++;
    }

    private synchronized int nextQueueIndexId() {
        return queueIndexId++;
    }

}
