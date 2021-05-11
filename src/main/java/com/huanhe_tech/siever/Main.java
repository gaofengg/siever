package com.huanhe_tech.siever;

import com.huanhe_tech.cli.*;
import com.huanhe_tech.cli.reqAndHandler.ProduceHistDataBeanToQueue;
import com.huanhe_tech.cli.strategies.StrategyApply;
import com.huanhe_tech.cli.strategies.StrategyExtreme;
import com.huanhe_tech.siever.utils.ColorSOP;
import com.huanhe_tech.siever.utils.SymbolsSourceHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        int pileNumber = 4;
        int extremeNumber = 3;
        String nowDateTime = LocalDateTime.now(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss"));
        String uri = "resources/usa.txt";
        System.out.println("Current Time in New York: " + nowDateTime);
        // 将 symbol list 的源文件头的日期与配置文件里值对比，判断源文件是否更新过？
        if (SymbolsSourceHandler.needUpdate(uri) > 0) {
            // 从本地文件读取 symbol，生成数据对象，put 到队列
            new Thread(() -> new ProduceAllSymbols("usa.txt", InstancePool.getAllSymbolsQueue()).putFlowingSymbolsToQueue(), "Get all symbol thread").start();
            // 从前序队列取出对象数据, 并过滤出 NASDAQ 和 NYSE 的标的，put 到 FiltrateBySymbolTypeQueue 队列
            new Thread(() -> new ConsumeAllSymbols(InstancePool.getAllSymbolsQueue()).takeFlowingSymbolFormQueue(), "Filtrate type thread").start();
            // 从 FiltrateBySymbolTypeQueue 队列取出对象，写入数据库中的 symbols_list_tbl 表
            new Thread(() -> new ConsumeFilteredByTypeSymbolObj().takeAndPersistence()).start();
            new ProduceHistDataBeanToQueue(new StrategyExtreme(pileNumber, extremeNumber));
            new StrategyApply().getHistDataAndStrategyApply(new StrategyExtreme(pileNumber, extremeNumber));
            // 查表 symbols_list_tbl，请求历史数据，put 到 QueueWithHistDataBean
            // 从 QueueWithHistDataBean 队列 take HistDataBean 创表或更新数据，并计算
        } else if (SymbolsSourceHandler.needUpdate(uri) == 0){
            new Thread(() -> new ProduceHistDataBeanToQueue(new StrategyExtreme(pileNumber, extremeNumber))).start();
            new StrategyApply().getHistDataAndStrategyApply(new StrategyExtreme(pileNumber, extremeNumber));
        } else {
            ColorSOP.e("You seem to be using expired symbol source data, please update it to " + uri + ".");
        }

    }
}
