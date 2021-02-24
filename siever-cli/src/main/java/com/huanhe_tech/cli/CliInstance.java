package com.huanhe_tech.cli;

import com.ib.client.Contract;
import com.ib.client.Types;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController;
import com.ib.controller.ApiController.IConnectionHandler;

import java.util.List;

import static java.lang.Thread.sleep;

public class CliInstance implements IConnectionHandler {
    public static CliInstance INSTANCE;
    private ApiController m_controller;
    private Contract m_contract = new Contract();
    private final Logger m_inLogger = new Logger();
    private final Logger m_outLogger = new Logger();

    private static class Logger implements ILogger {

        // 这里显示的信息需要定制，否则一堆无用的信息会出现
        @Override
        public void log(String s) {
//            System.out.println("$$$$");

        }

    }

    public static void start(CliInstance cliInstance) {
        INSTANCE = cliInstance;
        INSTANCE.run();
    }

    private void run() {
        controller().connect("127.0.0.1", 7496, 0, "");

        // 模糊查找带有 "IBM" 的symbol
//        controller().reqMatchingSymbols("IBM", new SymbolSearchlHandler());
//        try {
//            sleep(1000);
//            m_controller.reqHistoricalData(
//                    ContractSet.USStockWithPrimaryExch(),
//                    "20210222 09:09:09",
//                    1,
//                    Types.DurationUnit.DAY,
//                    Types.BarSize._1_day,
//                    Types.WhatToShow.BID,
//                    true,
//                    false,
//                    new HistoricalDataHandler());
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    public ApiController controller() {
        if (m_controller == null) {
            m_controller = new ApiController(this, m_inLogger, m_outLogger);
        }
        return m_controller;
    }


    @Override
    public void connected() {
        show("Connected! \n");

        controller().reqHistoricalData(
                ContractSet.USStockWithPrimaryExch(),
                "20210220 00:00:00",
                1,
                Types.DurationUnit.DAY,
                Types.BarSize._1_day,
                Types.WhatToShow.BID,
                true,
                false,
                new HistoricalDataHandler(ContractSet.USStockWithPrimaryExch().symbol()));
    }

    @Override
    public void disconnected() {
        show("Disconnected!");
    }

    @Override
    public void accountList(List<String> list) {
        System.out.print("Acount List: ");
        list.forEach(System.out::println);
    }

    @Override
    public void error(Exception e) {
        show(e.toString());
    }

    // 市场数据链接状态日志
    @Override
    public void message(int i, int i1, String s) {
//        show(i + " " + i1 + " " + s);
    }

    @Override
    public void show(String s) {
        System.out.println(s);
    }


}
