package com.huanhe_tech.siever;

import com.huanhe_tech.cli.*;

import com.huanhe_tech.cli.strategies.StrategyApply;
import com.huanhe_tech.cli.strategies.StrategyExtreme;
import com.huanhe_tech.siever.utils.SymbolsSourceHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        String nowDateTime = LocalDateTime.now(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss"));
        System.out.println("Current Time in New York: " + nowDateTime);

        if (SymbolsSourceHandler.isUpdate("resources/usa.txt")) {
            // 从本地文件读取 symbol，生成数据对象，put 到队列
            new Thread(() -> new ProduceAllSymbols("usa.txt", InstancePool.getAllSymbolsQueue()).putFlowingSymbolsToQueue(), "Get all symbol thread").start();
            // 从前序队列取出对象数据, 并过滤出 NASDQ 和 NYSE 的标的，put 到 FiltrateBySymbolTypeQueue 队列
            new Thread(() -> new ConsumeAllSymbols(InstancePool.getAllSymbolsQueue()).takeFlowingSymbolFormQueue(), "Filtrate type thread").start();
            // 从 FiltrateBySymbolTypeQueue 队列取出对象，写入数据库中的 symbols_list_tbl 表
            new Thread(() -> {
                new ConsumeFilteredByTypeSymbolObj().takeAndPersistence();
                new StrategyApply().getHistDataAndStrategyApply(new StrategyExtreme());
            }).start();
        } else {
            new StrategyApply().getHistDataAndStrategyApply(new StrategyExtreme());
        }

    }

}
