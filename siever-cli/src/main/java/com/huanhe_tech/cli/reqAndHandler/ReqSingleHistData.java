package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import com.huanhe_tech.cli.connection.Reconnection;

import java.util.Map;

public class ReqSingleHistData {
    public ReqSingleHistData(Map<String, Object> idAndConidAndSymbolMapList, int intervalDays) {
        if (!InstancePool.getConnectionController().client().isConnected()) {
            System.out.println("Reconnecting ...");
            new Reconnection();
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InstancePool.getServiceSet().reqSingleHistData(idAndConidAndSymbolMapList, icsm -> ReqData.REQ_HIST.setSymbol(icsm.get("symbol").toString())
                .setConid(Long.parseLong(icsm.get("conid").toString()))
                .reqHistAndPersistenceHandle(intervalDays));

    }
}
