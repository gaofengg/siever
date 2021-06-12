package crawler;

import us.codecraft.webmagic.Spider;

public class CrawlerExecutor {
    private final String symbol1;
    private final CrawlerPage crawlerPage;
    private final IPipeline iPipeline;

    public CrawlerExecutor(String symbol1) {
        this.symbol1 = symbol1;
        crawlerPage = new CrawlerPage();
        iPipeline = new IPipeline(symbol1);
    }

    public CrawlerExecutor execute() {
        Spider.create(crawlerPage)
                .addUrl("https://cn.tradingview.com/symbols/NASDAQ-AAPL/")
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
