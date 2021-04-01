package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.FlowingSymbolObj;
import com.ib.client.Bar;

import java.util.function.Consumer;
import java.util.function.Function;

public class ServiceSet {

    public void filtrateBy(FlowingSymbolObj flowingSymbolObjSub, Consumer<FlowingSymbolObj> consumer) {

        consumer.accept(flowingSymbolObjSub);
    }

    public void reqHistData(FlowingSymbolObj flowingSymbolObj, Consumer<FlowingSymbolObj> consumer) {
        consumer.accept(flowingSymbolObj);
    }

}