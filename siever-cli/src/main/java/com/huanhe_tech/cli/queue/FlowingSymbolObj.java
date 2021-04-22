package com.huanhe_tech.cli.queue;

public class FlowingSymbolObj {
    private int id;
    private String symbol;

    public FlowingSymbolObj() {
    }

    public FlowingSymbolObj(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
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
