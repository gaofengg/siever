package com.huanhe_tech.cli.crawler;

import us.codecraft.webmagic.Spider;

public class CrawlerExecutor {
    private final String symbol;
    private final CrawlerPage crawlerPage;
    private final IPipeline iPipeline;

    public CrawlerExecutor(String symbol) {
        this.symbol = symbol;
        crawlerPage = new CrawlerPage();
        iPipeline = new IPipeline(symbol);
    }

    public CrawlerExecutor execute() {
        Spider.create(crawlerPage)
                .addUrl("https://finance.yahoo.com/quote/" + symbol + "/key-statistics?p=" + symbol)
                .addPipeline(iPipeline)
                .run();
        return this;
    }

    public String getMarketCap() {
        return crawlerPage.getMarketCap();
    }

    public String getUrl() {
        return iPipeline.getUrl();
    }


}
