package com.huanhe_tech.cli.queue;

final public class FilteredByTypeFlowingSymbol extends FlowingSymbolObj{
    private final long conid;

    public FilteredByTypeFlowingSymbol(int id, String symbol, long conid) {
        super(id, symbol);
        this.conid = conid;
    }

    public long getConid() {
        return conid;
    }
}
