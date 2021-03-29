package com.huanhe_tech.cli.connection;

import com.huanhe_tech.cli.InstancePool;
import com.ib.controller.ApiConnection;
import com.ib.controller.ApiController;

public class ConnectionController extends ApiController {

    public ConnectionController(IConnectionHandler iConnectionHandler, ApiConnection.ILogger iLogger, ApiConnection.ILogger iLogger1) {
        super(iConnectionHandler, iLogger, iLogger1);
    }

    public void connect() {
        InstancePool.getConnectionController().connect("127.0.0.1", 7496, 0, "");
    }

    public void disconnect() {
//        InstancePool.getConnectionController().disconnect();
        super.disconnect();
    }

    public boolean checkConnection() {
        return super.checkConnection();
    }

}
