package com.huanhe_tech.cli.DAO;

import com.huanhe_tech.cli.ContractWithSTK;
import com.huanhe_tech.cli.InstancePool;

public class MainTest {
    public static void main(String[] args) {
        InstancePool.getConnectionController().connect();
        reqContractDetails();
    }

    public static void reqContractDetails() {
        InstancePool.getConnectionController().reqContractDetails(
                ContractWithSTK.NNContractForReqDetails("AAPL"),
                new ContractDetailsHanderTest()
        );
    }
}
