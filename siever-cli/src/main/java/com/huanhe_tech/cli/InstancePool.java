package com.huanhe_tech.cli;

import com.huanhe_tech.cli.connection.ConnectionController;
import com.huanhe_tech.cli.connection.ConnectionHandler;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.huanhe_tech.cli.queue.FiltrateBySymbolTypeQueue;
import com.huanhe_tech.cli.req.ContractDetailsHandler;
import com.huanhe_tech.cli.req.HistDataHandler;

public final class InstancePool {

    private InstancePool() {
    }

    private static String fileName = null;
    private static final HistDataHandler histDataHandler = new HistDataHandler();

    private static class LazyLoad {
        static final AllSymbolsQueue instanceAllSymbolsQueue = new AllSymbolsQueue(200);
        static final FiltrateBySymbolTypeQueue instanceFiltrateBySymbolTypeQueue = new FiltrateBySymbolTypeQueue(200);
        static final ProduceAllSymbols instanceProduceAllSymbols = new ProduceAllSymbols(fileName, instanceAllSymbolsQueue);
        static final ConsumeAllSymbols instanceConsumeAllSymbols = new ConsumeAllSymbols(instanceAllSymbolsQueue);
        static final ConsumeFilteredByTypeSymbolObj instanceConsumeFilteredByTypeSymbolObj = new ConsumeFilteredByTypeSymbolObj(instanceFiltrateBySymbolTypeQueue);
        static final ServiceSet instanceServiceSet = new ServiceSet();
        static final ConnectionHandler instanceConnectionHandler = new ConnectionHandler();
        static final ConnectionController instanceConnectionController = new ConnectionController(instanceConnectionHandler, instanceConnectionHandler.m_inLogger, instanceConnectionHandler.m_outLogger);
        static final ContractDetailsHandler instanceContractDetailsHandler = new ContractDetailsHandler();
    }

    public static AllSymbolsQueue getAllSymbolsQueue() {
        return LazyLoad.instanceAllSymbolsQueue;
    }

    public static FiltrateBySymbolTypeQueue getFiltrateBySymbolTypeQueue() {
        return LazyLoad.instanceFiltrateBySymbolTypeQueue;
    }

    public static ProduceAllSymbols getProduceAllSymbols(String fileName) {
        InstancePool.fileName = fileName;
        return LazyLoad.instanceProduceAllSymbols;
    }

    public static ConsumeAllSymbols getConsumeAllSymbols() {
        return LazyLoad.instanceConsumeAllSymbols;
    }

    public static ConsumeFilteredByTypeSymbolObj getConsumeFilteredByTypeSymbolObj() {
        return LazyLoad.instanceConsumeFilteredByTypeSymbolObj;
    }

    public static ServiceSet getServiceSet() {
        return LazyLoad.instanceServiceSet;
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

    public static HistDataHandler getHistDataHandler() {
        return histDataHandler;
    }

}
