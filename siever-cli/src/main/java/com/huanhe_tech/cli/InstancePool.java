package com.huanhe_tech.cli;

import com.huanhe_tech.cli.connection.ConnectionController;
import com.huanhe_tech.cli.connection.ConnectionHandler;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.huanhe_tech.cli.queue.FiltrateBySymbolTypeQueue;

public final class InstancePool {

    private InstancePool() {
    }

//    private static String fileName = null;
//    private static final HistDataHandler histDataHandler = new HistDataHandler(symbol);

    private static class LazyLoad {
        // 所有队列对象必须单例
        static final AllSymbolsQueue instanceAllSymbolsQueue = new AllSymbolsQueue(200);
        static final FiltrateBySymbolTypeQueue instanceFiltrateBySymbolTypeQueue = new FiltrateBySymbolTypeQueue(200);
        static final ServiceSet instanceServiceSet = new ServiceSet();
        static final ConnectionHandler instanceConnectionHandler = new ConnectionHandler();
        static final ConnectionController instanceConnectionController = new ConnectionController(instanceConnectionHandler, instanceConnectionHandler.m_inLogger, instanceConnectionHandler.m_outLogger);
    }

    public static AllSymbolsQueue getAllSymbolsQueue() {
        return LazyLoad.instanceAllSymbolsQueue;
    }

    public static FiltrateBySymbolTypeQueue getFiltrateBySymbolTypeQueue() {
        return LazyLoad.instanceFiltrateBySymbolTypeQueue;
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

}
