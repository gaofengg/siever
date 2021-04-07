package com.huanhe_tech.cli.queue;

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
    private final String symbol;
    private final List<HistDataTemplate> list;

    public HistDataObjForIn(int id, String symbol, List<HistDataTemplate> list) {
        this.id = id;
        this.symbol = symbol;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<HistDataTemplate> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "HistDataObjForIn{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", list=" + list +
                '}';
    }
}
