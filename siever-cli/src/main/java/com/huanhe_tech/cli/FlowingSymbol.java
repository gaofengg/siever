package com.huanhe_tech.cli;

public enum FlowingSymbol {
    INSTANCE(0, null);
    private int id;
    private String symbol;

    FlowingSymbol(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int setIdIncrement() {
       return id++;
    }
}
