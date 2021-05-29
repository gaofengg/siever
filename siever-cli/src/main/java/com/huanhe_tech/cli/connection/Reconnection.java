package com.huanhe_tech.cli.connection;

import com.huanhe_tech.cli.InstancePool;

public class Reconnection {
    public Reconnection() {
            try {
                Thread.sleep(3000);
                InstancePool.getConnectionController().connect();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
