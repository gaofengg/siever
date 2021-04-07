package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.HistDataObjForIn;

public class HistDataAnalyzerAndHandler {

    public void getHistDataFromQueue() {
        try {
            while (true) {
                HistDataObjForIn take = InstancePool.getHistDataQueue().take();
                System.out.println(take.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
