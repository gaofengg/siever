package com.huanhe_tech.cli.queue;

import com.huanhe_tech.cli.beans.BeanOfExtremeResult;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueWithExtremeResultBean extends ArrayBlockingQueue<BeanOfExtremeResult> {
    private BeanOfExtremeResult beanOfExtremeResult;

    public QueueWithExtremeResultBean(int capacity) {
        super(capacity);
    }

    @Override
    public void put(@NotNull BeanOfExtremeResult beanOfExtremeResult) {
        try {
            super.put(beanOfExtremeResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BeanOfExtremeResult take() {
        try {
            beanOfExtremeResult = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return beanOfExtremeResult;
    }
}
