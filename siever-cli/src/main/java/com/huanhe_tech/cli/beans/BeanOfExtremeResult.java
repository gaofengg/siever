package com.huanhe_tech.cli.beans;

public class BeanOfExtremeResult {
    private int id;
    private long conid;
    private String symbol;
    private String orientation;
    private double high_avg;
    private double low_avg;
    private double ad;

    public BeanOfExtremeResult(int id, long conid, String symbol, String orientation, double high_avg, double low_avg, double ad) {
        this.id = id;
        this.conid = conid;
        this.symbol = symbol;
        this.orientation = orientation;
        this.high_avg = high_avg;
        this.low_avg = low_avg;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getConid() {
        return conid;
    }

    public void setConid(long conid) {
        this.conid = conid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public double getHigh_avg() {
        return high_avg;
    }

    public void setHigh_avg(double high_avg) {
        this.high_avg = high_avg;
    }

    public double getLow_avg() {
        return low_avg;
    }

    public void setLow_avg(double low_avg) {
        this.low_avg = low_avg;
    }

    public double getAd() {
        return ad;
    }

    public void setAd(double ad) {
        this.ad = ad;
    }

    @Override
    public String toString() {
        return "BeanOfExtremeResult{" +
                "id=" + id +
                ", conid=" + conid +
                ", symbol='" + symbol + '\'' +
                ", orientation='" + orientation + '\'' +
                ", high_ave=" + high_avg +
                ", low_ave=" + low_avg +
                ", ad=" + ad +
                '}';
    }
}
