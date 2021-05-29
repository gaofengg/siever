package com.huanhe_tech.cli.connection;

import com.huanhe_tech.cli.GlobalFlags;
import com.huanhe_tech.siever.utils.LLoger;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController;

import java.util.List;

public class ConnectionHandler implements ApiController.IConnectionHandler {
    public ILogger m_inLogger = new CLogger("Start Connecting ...");
    public ILogger m_outLogger = new CLogger("");

    static class CLogger implements ILogger {

        public CLogger(String s) {
            System.out.println(s);
        }

        @Override
        public void log(String s) {
//            System.out.println(s);
        }
    }

    @Override
    public void connected() {
        LLoger.logger.trace("Connected!");
    }

    @Override
    public void disconnected() {
        LLoger.logger.warn("Disconnected!");
    }

    @Override
    public void accountList(List<String> list) {
        LLoger.logger.info("Acount List: ");
        list.forEach(LLoger.logger::info);
    }

    @Override
    public void error(Exception e) {
        LLoger.logger.error(e.toString());
    }

    @Override
    public void message(int i, int i1, String s) {
        if (i != -1) {
            LLoger.logger.error("{}\t{}\n{}", i, i1, s);
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
        LLoger.logger.trace(s);
    }
}
