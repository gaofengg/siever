package com.huanhe_tech.cli.queue;

final public class AllFlowingSymbol {
    private final int id;
    private final String symbol;

    public AllFlowingSymbol(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "FlowingSymbol{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
