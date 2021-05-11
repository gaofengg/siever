package com.huanhe_tech.cli.beans;

import java.util.List;

public class BeanOfHistListInQueue {
    private List<BeanOfHistData> list;

    public BeanOfHistListInQueue() {
    }

    public BeanOfHistListInQueue(int id, List<BeanOfHistData> list) {
        this.list = list;
    }

    public List<BeanOfHistData> getList() {
        return list;
    }
}
