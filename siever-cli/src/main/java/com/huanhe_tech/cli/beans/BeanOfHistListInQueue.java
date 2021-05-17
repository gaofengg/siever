package com.huanhe_tech.cli.beans;

import java.util.List;

public class BeanOfHistListInQueue {
    private List<BeanOfHistData> list;
    private int id;

    public BeanOfHistListInQueue() {
    }

    public BeanOfHistListInQueue(int id, List<BeanOfHistData> list) {
        this.id = id;
        this.list = list;
    }

    public List<BeanOfHistData> getList() {
        return list;
    }

    public int getId() {
        return id;
    }
}
