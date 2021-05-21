package com.huanhe_tech.cli.connection;

import com.huanhe_tech.cli.InstancePool;

public class Reconnection {
    public Reconnection() {
        if (InstancePool.getConnectionController().client().isConnected()) {
            InstancePool.getConnectionController().disconnect();
        }
        try {
            Thread.sleep(5000);
            InstancePool.getConnectionController().connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
