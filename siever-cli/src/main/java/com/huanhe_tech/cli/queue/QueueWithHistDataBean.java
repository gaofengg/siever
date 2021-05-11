package com.huanhe_tech.cli.queue;

import com.huanhe_tech.cli.beans.BeanOfHistListInQueue;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueWithHistDataBean extends ArrayBlockingQueue<BeanOfHistListInQueue> {
    private BeanOfHistListInQueue beanOfHistListInQueue;

    public QueueWithHistDataBean(int capacity) {
        super(capacity);
    }

    @Override
    public void put(@NotNull BeanOfHistListInQueue beanOfHistListInQueue) {
        try {
            super.put(beanOfHistListInQueue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull BeanOfHistListInQueue take() {
        try {
            beanOfHistListInQueue = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return beanOfHistListInQueue;
    }
}
