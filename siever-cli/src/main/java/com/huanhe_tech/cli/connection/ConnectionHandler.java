package com.huanhe_tech.cli.connection;

import com.huanhe_tech.cli.GlobalFlags;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class ConnectionHandler implements ApiController.IConnectionHandler {
    private final Log log = LogFactory.getLog(getClass());
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
        if (i != -1) {
            log.error(i + "\n" + i1 + "\n" + s);
            synchronized (GlobalFlags.UpdateHistDone.STATE) {
                if (!GlobalFlags.UpdateHistDone.STATE.getB() && s != null) {
                    GlobalFlags.UpdateHistDone.STATE.setB(true);
                    GlobalFlags.UpdateHistDone.STATE.notifyAll();
                }
            }
        }
    }

    @Override
    public void show(String s) {
        System.out.println(s);
    }

}
