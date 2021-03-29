package com.huanhe_tech.cli;

import com.huanhe_tech.cli.connection.ConnectionController;
import com.huanhe_tech.cli.connection.ConnectionHandler;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;

public final class InstancePool {
    private static String fileName = null;
    private static String symbol = null;

    private static class LazyLoad {
        static final AllSymbolsQueue instanceAllSymbolsQueue = new AllSymbolsQueue(200);
        static final ProduceAllSymbols instanceProduceAllSymbols = new ProduceAllSymbols(fileName, instanceAllSymbolsQueue);
        static final ConsumeAllSymbols instanceConsumeAllSymbols = new ConsumeAllSymbols(instanceAllSymbolsQueue);
        static final FilterServiceSet instanceFilterServiceSet = new FilterServiceSet();
        static final ConnectionHandler instanceConnectionHandler = new ConnectionHandler();
        static final ConnectionController instanceConnectionController = new ConnectionController(instanceConnectionHandler, instanceConnectionHandler.m_inLogger, instanceConnectionHandler.m_outLogger);
        static final ContractDetailsHandler instanceContractDetailsHandler = new ContractDetailsHandler();
        static final ReqContractDetailsController instanceContractDetailsController = new ReqContractDetailsController(symbol);
        static final MyExecutor instanceMyExecutor = new MyExecutor();
    }

    public static AllSymbolsQueue getAllSymbolsQueue() {
        return LazyLoad.instanceAllSymbolsQueue;
    }

    public static ProduceAllSymbols getProduceAllSymbols(String fileName) {
        InstancePool.fileName = fileName;
        return LazyLoad.instanceProduceAllSymbols;
    }

    public static ConsumeAllSymbols getConsumeAllSymbols() {
        return LazyLoad.instanceConsumeAllSymbols;
    }

    public static FilterServiceSet getFilterServiceSet() {
        return LazyLoad.instanceFilterServiceSet;
    }

    public static ConnectionHandler getConnectionHandler() {
        return LazyLoad.instanceConnectionHandler;
    }

    public static ConnectionController getConnectionController() {
        return LazyLoad.instanceConnectionController;
    }

    public static ContractDetailsHandler getContractDetailsHandler() {
        return LazyLoad.instanceContractDetailsHandler;
    }

    public static ReqContractDetailsController getReqContractDetailsController(String symbol) {
        InstancePool.symbol = symbol;
        return LazyLoad.instanceContractDetailsController;
    }

    public static MyExecutor getMainExecutor() {
        return LazyLoad.instanceMyExecutor;
    }
}
