package com.huanhe_tech.cli.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EndOfHistBeanQueue extends BeanOfHistListInQueue{
    private final int id = 20000;
    private final List<BeanOfHistData> list = new ArrayList<>();
    public EndOfHistBeanQueue() {
        list.add(new BeanOfHistData(
                20000,
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
