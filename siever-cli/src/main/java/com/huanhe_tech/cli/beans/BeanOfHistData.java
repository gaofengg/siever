package com.huanhe_tech.cli.beans;

import com.huanhe_tech.cli.queue.FlowingSymbolObj;

/**
 *  数据结构：
 *  从 HistDataHandler 获得的历史数据结果，add 到一个 ArrayList 中，
 *  再将这个 ArrayList 打包成一个带有 id 和 symbol 属性的对象，
 *  将打包好的对象 put 到队列的尾部。
 *  这是一个 ArrayList 中的一个对象单元模板类。
 *
 */
public class BeanOfHistData extends FlowingSymbolObj {
    private final long conid;
    private final String time;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final long volume;
    private final double wap;
    private final int count;

    public BeanOfHistData(int id, String symbol, long conid, String time, double open, double high, double low, double close, long volume, double wap, int count) {
        super(id, symbol);
        this.conid = conid;
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.wap = wap;
        this.count = count;
    }

    public long getConid() {
        return conid;
    }

    public String getTime() {
        return time;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }

    public double getWap() {
        return wap;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "HistDataTemplate{" +

                "time='" + time + '\'' +
                ", conid= " + conid +
                ", id=" + getId() +
                ", symbol=" + getSymbol() +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", wap=" + wap +
                ", count=" + count +
                '}';
    }
}
