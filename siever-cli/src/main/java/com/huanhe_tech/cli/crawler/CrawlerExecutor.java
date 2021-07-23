package com.huanhe_tech.cli.crawler;

import us.codecraft.webmagic.Spider;

public class CrawlerExecutor {
    private final String symbol1;
    private final String symbol2;
    private final CrawlerPage crawlerPage;
    private final IPipeline iPipeline;

    public CrawlerExecutor(String symbol1, String symbol2) {
        this.symbol1 = symbol1;
        this.symbol2 = symbol2;
        crawlerPage = new CrawlerPage();
        iPipeline = new IPipeline(symbol1);
    }

    public CrawlerExecutor execute() {
        Spider.create(crawlerPage)
                .addUrl("https://finance.yahoo.com/quote/" + symbol1 + "/key-statistics?p=" + symbol1)
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
