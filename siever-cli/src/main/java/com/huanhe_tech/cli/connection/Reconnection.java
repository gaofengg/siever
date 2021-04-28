package com.huanhe_tech.cli.connection;

import com.huanhe_tech.cli.InstancePool;

public class Reconnection {
    public Reconnection() {
        if (InstancePool.getConnectionController().client().isConnected()) {
            InstancePool.getConnectionController().disconnect();
        }
        InstancePool.getConnectionController().connect();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
