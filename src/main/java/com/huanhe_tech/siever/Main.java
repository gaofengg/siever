package com.huanhe_tech.siever;

import com.huanhe_tech.cli.*;
import com.huanhe_tech.cli.reqAndHandler.ProduceHistDataBeanToQueue;
import com.huanhe_tech.cli.strategies.StrategyApply;
import com.huanhe_tech.cli.strategies.StrategyExtreme;
import com.huanhe_tech.siever.utils.LLoger;
import com.huanhe_tech.siever.utils.SymbolsSourceHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        int pileNumber = 3;
        int extremeNumber = 2;
        int redundancy = 1;
        String nowDateTime = ZonedDateTime.now(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LLoger.logger.info("Current Time in New York: {}", nowDateTime);
        String uri = "resources/usa.txt";
        // 将 symbol list 的源文件头的日期与配置文件里值对比，判断源文件是否更新过？
        if (SymbolsSourceHandler.needUpdate(uri) > 0) {
            System.out.println(SymbolsSourceHandler.needUpdate(uri));
            // 从本地文件读取 symbol，生成数据对象，put 到队列
            new Thread(() -> new ProduceAllSymbols("usa.txt", InstancePool.getAllSymbolsQueue()).putFlowingSymbolsToQueue(), "Get all symbol thread").start();
            // 从前序队列取出对象数据, 并过滤出 NASDAQ 和 NYSE 的标的，put 到 FiltrateBySymbolTypeQueue 队列
            new Thread(() -> new ConsumeAllSymbols(InstancePool.getAllSymbolsQueue()).takeFlowingSymbolFormQueue(), "Filtrate type thread").start();
            // 从 FiltrateBySymbolTypeQueue 队列取出对象，写入数据库中的 symbols_list_tbl 表
            Thread updateSymbolList = new Thread(() -> new ConsumeFilteredByTypeSymbolObj().takeAndPersistence());
            updateSymbolList.start();
            try {
                updateSymbolList.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(() -> new ProduceHistDataBeanToQueue(new StrategyExtreme(pileNumber, extremeNumber, redundancy))).start();
            new Thread(() -> new StrategyApply().getHistDataAndStrategyApply(new StrategyExtreme(pileNumber, extremeNumber, redundancy))).start();
            new Thread(ConsumeExtremeResult::new).start();
            // 查表 symbols_list_tbl，请求历史数据，put 到 QueueWithHistDataBean
            // 从 QueueWithHistDataBean 队列 take HistDataBean 创表或更新数据，并计算
        } else if (SymbolsSourceHandler.needUpdate(uri) == 0) {
            new Thread(() -> new ProduceHistDataBeanToQueue(new StrategyExtreme(pileNumber, extremeNumber, redundancy))).start();
            new Thread(() -> new StrategyApply().getHistDataAndStrategyApply(new StrategyExtreme(pileNumber, extremeNumber, redundancy))).start();
            new Thread(ConsumeExtremeResult::new).start();
        } else {
            LLoger.logger.error("You seem to be using expired symbol source data, please update it to " + uri + ".");
        }

    }
}
