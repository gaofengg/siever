package com.huanhe_tech.cli.connection;

import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController;

import java.util.List;

public class ConnectionHandler implements ApiController.IConnectionHandler {
    public ILogger m_inLogger = new Logger("Start Connecting ...");
    public ILogger m_outLogger = new Logger("");

    static class Logger implements ILogger {

        public Logger(String s) {
            System.out.println(s);
        }

        @Override
        public void log(String s) {
//            System.out.println(s);
        }
    }

    @Override
    public void connected() {
        System.out.println("Connected!");
    }

    @Override
    public void disconnected() {
        System.out.println("Disconnected!");
    }

    @Override
    public void accountList(List<String> list) {
        System.out.print("Acount List: ");
        list.forEach(System.out::println);
    }

    @Override
    public void error(Exception e) {
        System.out.println(e.toString());
    }

    @Override
    public void message(int i, int i1, String s) {

    }

    @Override
    public void show(String s) {
        System.out.println(s);
    }

}
