package com.huanhe_tech.cli.queue;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ArrayBlockingQueue;

public class HistDataQueue extends ArrayBlockingQueue<HistDataObjForIn> {
    public HistDataQueue(int capacity) {
        super(capacity);
    }

    @Override
    public HistDataObjForIn take() throws InterruptedException {
        return super.take();
    }

    @Override
    public void put(HistDataObjForIn histDataObjForIn) throws InterruptedException {
        super.put(histDataObjForIn);
    }
}
