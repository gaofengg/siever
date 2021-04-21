package com.huanhe_tech.cli.queue;

import com.huanhe_tech.cli.beans.BeanOfHistData;

import java.util.List;

/**
 *  数据结构：
 *  从 HistDataHandler 获得的历史数据结果，add 到一个 ArrayList 中，
 *  再将这个 ArrayList 打包成一个带有 id 和 symbol 属性的对象 this，
 *  将打包好的对象 put 到队列 HistDataQueue 的尾部。
 *
 */

public class HistDataObjForIn {
    private final int id;
    private final long conid;
    private final List<BeanOfHistData> list;

    public HistDataObjForIn(int id, long conid, List<BeanOfHistData> list) {
        this.id = id;
        this.conid = conid;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public long getConid() {
        return conid;
    }

    public List<BeanOfHistData> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "HistDataObjForIn{" +
                "id=" + id +
                ", conid ='" + conid + '\'' +
                ", list=" + list +
                '}';
    }
}
