package com.huanhe_tech.cli.beans;

import java.util.ArrayList;
import java.util.List;

public class InterruptOfHistBeanQueue extends BeanOfHistListInQueue{
    private final int id = 20001;
    private final List<BeanOfHistData> list = new ArrayList<>();
    public InterruptOfHistBeanQueue() {
        list.add(new BeanOfHistData(
                20001,
                "#EOF",
                0,
                "0",
                0.0,
                0.0,
                0.0,
                0.0,
                0L,
                0.0,
                0
        ));
    }

    @Override
    public List<BeanOfHistData> getList() {
        return list;
    }

    @Override
    public int getId() {
        return id;
    }
}
