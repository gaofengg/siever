package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.FilteredByTypeFlowingSymbol;
import com.huanhe_tech.cli.queue.FlowingSymbolObj;
import com.ib.client.Bar;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ServiceSet {

    public void filtrateBy(FlowingSymbolObj flowingSymbolObjSub, Consumer<FlowingSymbolObj> consumer) {

        consumer.accept(flowingSymbolObjSub);
    }

    public void reqHistData(FilteredByTypeFlowingSymbol flowingSymbolObj, Consumer<FilteredByTypeFlowingSymbol> consumer) {
        consumer.accept(flowingSymbolObj);
    }

    public void reqSingleHistData(Map<String, Object> idAndConidAndSymbolMapList, Consumer<Map<String, Object>> consumer) {
        consumer.accept(idAndConidAndSymbolMapList);
    }

}
