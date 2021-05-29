package com.huanhe_tech.cli.crawler;

import com.huanhe_tech.siever.utils.LLoger;
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
        LLoger.logger.trace("https://finance.yahoo.com/chart/" + symbol);
    }

    public String getUrl() {
        return "https://finance.yahoo.com/chart/" + symbol;
    }
}
