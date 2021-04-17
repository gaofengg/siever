package com.huanhe_tech.cli.queue;

final public class FilteredByTypeFlowingSymbol extends FlowingSymbolObj{
    private final int conid;

    public FilteredByTypeFlowingSymbol(int id, String symbol, int conid) {
        super(id, symbol);
        this.conid = conid;
    }

    public int getConid() {
        return conid;
    }
}
