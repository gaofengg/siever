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
    private long conid;
    private String time;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private double wap;
    private int count;

    public BeanOfHistData() {
    }

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

    public void setId(int id) {
        super.setId(id);
    }

    public void setSymbol(String symbol) {
        super.setSymbol(symbol);
    }

    public void setConid(long conid) {
        this.conid = conid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public void setWap(double wap) {
        this.wap = wap;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {return super.getId();}

    public String getSymbol() {return super.getSymbol();}

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
