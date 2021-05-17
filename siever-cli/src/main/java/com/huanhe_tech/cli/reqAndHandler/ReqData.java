package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.ContractSet;
import com.huanhe_tech.cli.ContractWithSTK;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.siever.utils.ILocalDataTime;
import com.ib.client.Types;

/**
 *  关于向数据库中补充新历史数据的逻辑
 *  1，查询数据库中的指定5个标的的数据最后插入日期，取最近的日期，减去近日日期，可得出
 *  reqHistAndHandleData 方法中第三个参数的值，并代入。
 *      （为什么是5个标的？假如只查询一个标的，如果该标的近日停牌，则需插入的历史数据的起始日期就是停牌日，
 *      从而导致数据收集不准确，使用5个标的，可以减少该情况的发生率）
 */

public enum ReqData {
    REQ_TYPE(null, 0),
    REQ_HIST(null, 0);

    private String symbol;
    private long conid;

    ReqData(String symbol, long conid) {
        this.symbol = symbol;
        this.conid = conid;
    }


    public ReqData setSymbol(String symbol) {
        this.symbol = symbol;
        return ReqData.this;
    }

    public ReqData setConid(long conid) {
        this.conid = conid;
        return ReqData.this;
    }

    public void reqContractDetails() {
        InstancePool.getConnectionController().reqContractDetails(
                ContractWithSTK.NNContractForReqDetails(symbol),
                new ContractDetailsHandler(symbol)
        );
    }

    public void reqHistAndPersistenceHandle(int intervalDays) {
        InstancePool.getConnectionController().reqHistoricalData(
                ContractSet.USStockWithPrimaryExch(symbol),
                new ILocalDataTime().getLocalDataTime(),
                intervalDays,
                Types.DurationUnit.DAY,
                Types.BarSize._1_day,
                Types.WhatToShow.TRADES,
                true,
                false,
                new HistDataHandler(conid, symbol, intervalDays)
        );

    }

}
