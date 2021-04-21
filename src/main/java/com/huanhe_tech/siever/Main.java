package com.huanhe_tech.siever;

import com.huanhe_tech.cli.*;
import com.huanhe_tech.cli.controller.MainController;
import com.huanhe_tech.cli.strategies.StrategyOfExtremum;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        if (new MainController().needUpdate()) {
//            System.out.println("直接计算");
//        } else {
//            ProduceAllSymbols produceAllSymbols = InstancePool.getProduceAllSymbols("usa.txt");
//            ConsumeAllSymbols consumeAllSymbols = InstancePool.getConsumeAllSymbols();
//            ConsumeFilteredByTypeSymbolObj consumeFilteredByTypeSymbolObj = InstancePool.getConsumeFilteredByTypeSymbolObj();
//
//            // 从本地文件读取 symbol，生成数据对象，put 到队列
//            new Thread(produceAllSymbols::putFlowingSymbolsToQueue, "Get all symbol thread").start();
//            // 从前序队列取出对象数据, 并将处理后的数据 put到新的队列
//            new Thread(consumeAllSymbols::takeFlowingSymbolFormQueue, "Filtrate type thread").start();
//            // 从前序队列取出对象数据
//            new Thread(consumeFilteredByTypeSymbolObj::takeFilteredByTypeSymbolObj, "Req historical date thread").start();
//            new Thread(() -> {
//                new ConsumeHistData().getHistDataFromQueue();
//            }).start();
//
////            System.out.println("数据库更新完毕，开始计算。");
//        }

        new StrategyOfExtremum().getHistData();
    }
}
