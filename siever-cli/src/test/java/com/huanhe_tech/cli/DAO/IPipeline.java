package com.huanhe_tech.cli.DAO;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class IPipeline implements Pipeline {
    private final String symbol;
    public IPipeline(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("https://finance.yahoo.com/chart/" + symbol);
    }
}
