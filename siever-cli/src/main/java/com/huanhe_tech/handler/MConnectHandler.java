package com.huanhe_tech.handler;

import com.ib.client.Types;
import com.ib.controller.ApiConnection;
import com.ib.controller.ApiController;

import java.util.List;

public enum MConnectHandler implements ApiController.IConnectionHandler {
    INSTANCE;
    private ApiController m_controller;
    private final CliInstance.Logger m_inLogger = new CliInstance.Logger();
    private final CliInstance.Logger m_outLogger = new CliInstance.Logger();

    public void connect() {
        controller().connect("127.0.0.1", 7496, 0, "");
    }

    public void disconnect() {
        controller().disconnect();
    }

    private static class Logger implements ApiConnection.ILogger {

        // 这里显示的信息需要定制，否则一堆无用的信息会出现
        @Override
        public void log(String s) {
//            System.out.println("$$$$");
        }
    }

    public ApiController controller() {
        if (m_controller == null) {
            m_controller = new ApiController(this, m_inLogger, m_outLogger);
        }
        return m_controller;
    }

    @Override
    public void connected() {
       show("Connected!");
//       controller().reqHistoricalData(
//               ContractSet.USStockWithPrimaryExch("AAPL"),
//               "20210302 18:00:00",
//               1,
//               Types.DurationUnit.DAY,
//               Types.BarSize._1_day,
//               Types.WhatToShow.BID,
//               true,
//               false,
//               new MHistoricalDataHandler("AAPL")
//       );

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
        System.out.println(e);
    }

    @Override
    public void message(int i, int i1, String s) {

    }

    @Override
    public void show(String s) {
        System.out.println(s);
    }
}
