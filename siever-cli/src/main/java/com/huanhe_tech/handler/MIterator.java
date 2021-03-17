package com.huanhe_tech.handler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MIterator {
    MHistoricalDataHandler my_mec;
    private final MGlobalSettings mGlobalSettings = MGlobalSettings.INSTANCE;
    Path path = Paths.get("/Users/gaofeng/shortstock/usa.txt");
    Charset charset = StandardCharsets.UTF_8;
    private int count = 0;

    List<String> symbolList = new ArrayList<>();

    public MHistoricalDataHandler myMecController() {
        if (my_mec == null) {
            my_mec = new MHistoricalDataHandler();
        }
        return my_mec;
    }

    public void getSymbolFromFile() {
        synchronized (mGlobalSettings) {
            try {
                Stream<String> lines = Files.lines(path, charset);
                System.out.printf("%10s %10s %10s %22s", "SYMBOL", "HIGH", "LOW", "TIME\n");
                lines.forEach(
                        line -> {
                            if (line.matches("^(?!.*?#).*$") && count < 50) {
                                line = line.substring(0, line.indexOf("|"));
                                new MReqContractDetailsController(line).reqContractDetails();

                                try {
                                    // 等待 MContractDetailsHandler 改 MGlobalSettings 的 hasNN
                                    mGlobalSettings.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if(mGlobalSettings.hasNN()) {
                                    symbolList.add(line);
                                    count ++;
                                    System.out.println(count + ": " + line);
                                }

//                            MReqHistoricalDataController mec = new MReqHistoricalDataController(line);
//                            mec.reqAndHandlerHistoricalData();
                            }
                        }
                );
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void echoHistoricalData() {
        do {
            mGlobalSettings.setReqHistoricalComplete(false);
            MReqHistoricalDataController mec = new MReqHistoricalDataController(symbolList.get(mGlobalSettings.getSymbolListIndex()));
            mec.reqAndHandlerHistoricalData();

            System.out.println(mGlobalSettings.isReqHistoricalComplete());
            System.out.println(mGlobalSettings.getSymbolListIndex());
        } while (mGlobalSettings.isReqHistoricalComplete() && mGlobalSettings.getSymbolListIndex() < 20);
    }
}
