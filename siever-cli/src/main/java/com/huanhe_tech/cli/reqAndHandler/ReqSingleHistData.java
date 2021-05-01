package com.huanhe_tech.cli.reqAndHandler;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.cli.InstancePool;
import java.util.Map;

public class ReqSingleHistData {
    public ReqSingleHistData(Map<String, Object> idAndConidAndSymbolMapList, int intervalDays) {

        InstancePool.getServiceSet().reqSingleHistData(idAndConidAndSymbolMapList, icsm -> ReqData.REQ_HIST.setSymbol(icsm.get("symbol").toString())
                .setConid(Long.parseLong(icsm.get("conid").toString()))
                .reqHistAndPersistenceHandle(intervalDays));

        synchronized (GlobalFlags.UpdateHistDone.STATE) {
            while (true) {
                if (GlobalFlags.UpdateHistDone.STATE.getB()) {
                    GlobalFlags.UpdateHistDone.STATE.setB(false);
                    break;
                } else {
                    try {
                        GlobalFlags.UpdateHistDone.STATE.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
