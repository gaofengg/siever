package com.huanhe_tech.cli;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ProduceAllSymbols {

    private final Path symbolListFilePath;
    private final Charset charset = StandardCharsets.UTF_8;
    private final AllSymbolsQueue allSymbolsQueue;
    private final FlowingSymbol flowingSymbol = FlowingSymbol.INSTANCE;

    private int count; // 用于测试的，不要一次获取这么多symbol

    /**
     * @param fileName        TWS 官方提供的 symbol 列表文件名
     * @param allSymbolsQueue 存放 symbols 完整列表的队列容器
     */
    public ProduceAllSymbols(String fileName, AllSymbolsQueue allSymbolsQueue, FlowingSymbol flowingSymbol) {
        this.allSymbolsQueue = allSymbolsQueue;

        String projectDir = System.getProperty("user.dir");
        String assetsDir = projectDir + "/assets";
        symbolListFilePath = Paths.get(assetsDir + "/" + fileName);

    }

    public void putFlowingSymbolsToQueue() {
        synchronized (flowingSymbol) {
            try {
                Stream<String> lines = Files.lines(symbolListFilePath, charset);
                lines.forEach(
                        line -> {
                            if (line.matches("^(?!.*?#).*$") && count <= 5) {
                                line = line.substring(0, line.indexOf("|"));
                                flowingSymbol.setId(flowingSymbol.getId());
                                flowingSymbol.setSymbol(line);
                                // 将 line 加入到一个有限队列中，提供给 SymbolFilter(String NN) 消费
                                allSymbolsQueue.putSymbol(flowingSymbol);
                                System.out.println("生产者生产了一个 symbol：{" + flowingSymbol.getId() + ": " + flowingSymbol.getSymbol() + "}");
                                flowingSymbol.setIdIncrement();
                                count++;
                            }
                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
