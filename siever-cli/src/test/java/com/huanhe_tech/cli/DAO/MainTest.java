package com.huanhe_tech.cli.DAO;

import com.huanhe_tech.cli.ContractSet;
import com.huanhe_tech.cli.ContractWithSTK;
import com.huanhe_tech.cli.InstancePool;
import com.ib.client.TickAttrib;
import com.ib.client.TickType;
import com.ib.controller.ApiController;

public class MainTest {
    public static void main(String[] args) {
        InstancePool.getConnectionController().connect();
//        reqContractDetails();
        reqMarketCap();
    }

    public static void reqContractDetails() {
        InstancePool.getConnectionController().reqContractDetails(
                ContractWithSTK.NNContractForReqDetails("AAPL"),
                new ContractDetailsHanderTest()
        );
    }

    public static void reqMarketCap() {
        InstancePool.getConnectionController().reqTopMktData(ContractSet.USStockWithPrimaryExch("AAPL"), "", false, false, new TopMarketDataHandler());
    }
}

class TopMarketDataHandler implements ApiController.ITopMktDataHandler {

    @Override
    public void tickPrice(TickType tickType, double v, TickAttrib tickAttrib) {
        System.out.println(tickAttrib + ": " + v + "\t" + tickAttrib);
    }

    @Override
    public void tickSize(TickType tickType, int i) {

    }

    @Override
    public void tickString(TickType tickType, String s) {
//        if (tickType.toString().equals("MKT_CAP")) {
//            System.out.println(s);
//        }
        System.out.println(tickType + ": " + s);
    }

    @Override
    public void tickSnapshotEnd() {

    }

    @Override
    public void marketDataType(int i) {
        System.out.println(i);
    }

    @Override
    public void tickReqParams(int i, double v, String s, int i1) {

    }
}
