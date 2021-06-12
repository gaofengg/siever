package crawler;

public class TestCraw {
    public static void main(String[] args) {
        CrawlerExecutor aapl = new CrawlerExecutor("AAPL");
        CrawlerExecutor execute = aapl.execute();
        System.out.println(execute.getMarketCap());
    }
}
