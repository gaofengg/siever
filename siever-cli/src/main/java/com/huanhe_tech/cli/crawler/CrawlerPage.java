package com.huanhe_tech.cli.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class CrawlerPage implements PageProcessor {
    private final Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
    private String marketCap;

    @Override
    public void process(Page page) {
        marketCap = page.getHtml().xpath("//td[@data-reactid='21']/text()").toString();
    }

    @Override
    public Site getSite() {
        return site;
    }

    public String getMarketCap() {
        return marketCap;
    }
}
