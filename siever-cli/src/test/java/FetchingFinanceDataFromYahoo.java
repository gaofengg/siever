import com.huanhe_tech.cli.DAO.IPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class FetchingFinanceDataFromYahoo implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    @Override
    public void process(Page page) {
        String aa = page.getHtml().xpath("//td[@data-reactid='21']/text()").toString();
        System.out.println(aa);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String symbol = "NOK";
        Spider.create(new FetchingFinanceDataFromYahoo())
                .addUrl("https://finance.yahoo.com/quote/" + symbol + "/key-statistics?p=" + symbol)
                .addPipeline(new IPipeline(symbol))
                .run();
    }
}
