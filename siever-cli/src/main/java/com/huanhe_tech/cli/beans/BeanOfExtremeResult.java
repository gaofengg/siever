package com.huanhe_tech.cli.beans;

public class BeanOfExtremeResult {
    private int id;
    private long conid;
    private String symbol;
    private String orientation;
    private double quoteChangeVariance;
    private double volumeVariance;
    private double volumeBreak;
    private double extremeVariance;

    /**
     *
     * @param id id
     * @param conid conid
     * @param symbol symbol
     * @param orientation orientation UP or Down
     * @param quoteChangeVariance 涨跌幅的取样方差，数值越大，说明波动越大，即大涨大跌
     * @param volumeVariance 成交量的取样方差，数值越大，说明资金波动越大，即大进大出
     * @param volumeBreak 成交量突破，计算指定周期内的平均成交量 av，使用 今日成交量 / av
     * @param extremeVariance 极点的取样方差，数值越大，说明极值范围内的涨跌幅越大
     *                        按照上涨的标的开盘或单日不会跌太深，下跌的标的开盘或单日不会涨太高的原则
     *                        如果标的上涨，应该使用高点的数组求极值取样方差
     *                        反之亦然
     */
    public BeanOfExtremeResult(int id, long conid, String symbol, String orientation, double quoteChangeVariance, double volumeVariance, double volumeBreak, double extremeVariance) {
        this.id = id;
        this.conid = conid;
        this.symbol = symbol;
        this.orientation = orientation;
        this.quoteChangeVariance = quoteChangeVariance;
        this.volumeVariance = volumeVariance;
        this.volumeBreak = volumeBreak;
        this.extremeVariance = extremeVariance;
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

    public double getQuoteChangeVariance() {
        return quoteChangeVariance;
    }

    public void setQuoteChangeVariance(double quoteChangeVariance) {
        this.quoteChangeVariance = quoteChangeVariance;
    }

    public double getVolumeVariance() {
        return volumeVariance;
    }

    public void setVolumeVariance(double volumeVariance) {
        this.volumeVariance = volumeVariance;
    }

    public double getVolumeBreak() {
        return volumeBreak;
    }

    public void setVolumeBreak(double volumeBreak) {
        this.volumeBreak = volumeBreak;
    }

    public double getExtremeVariance() {
        return extremeVariance;
    }

    public void setExtremeVariance(double extremeVariance) {
        this.extremeVariance = extremeVariance;
    }

    @Override
    public String toString() {
        return "BeanOfExtremeResult{" +
                "id=" + id +
                ", conid=" + conid +
                ", symbol='" + symbol + '\'' +
                ", orientation='" + orientation + '\'' +
                ", high_ave=" + quoteChangeVariance +
                ", low_ave=" + volumeVariance +
                ", ad=" + volumeBreak +
                '}';
    }
}
