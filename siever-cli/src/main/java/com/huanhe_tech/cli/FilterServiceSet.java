package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.AllFlowingSymbol;

import java.util.function.Consumer;

public class FilterServiceSet{

    public void filtrateByPrimaryExchWithNN(AllFlowingSymbol flowingSymbol, Consumer<AllFlowingSymbol> consumer) {
        consumer.accept(flowingSymbol);
    }
}
