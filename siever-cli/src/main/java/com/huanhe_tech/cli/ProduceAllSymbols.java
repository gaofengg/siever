package com.huanhe_tech.cli;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ProduceAllSymbols {
    private static int id = 0;
    private final Path symbolListFilePath;
    private final Charset charset = StandardCharsets.UTF_8;
    private final AllSymbolsQueue2 allSymbolsQueue2;

    /**
     * @param fileName        TWS 官方提供的 symbol 列表文件名
     * @param allSymbolsQueue2 存放 symbols 完整列表的队列容器
     */
    public ProduceAllSymbols(String fileName, AllSymbolsQueue2 allSymbolsQueue2) {
        this.allSymbolsQueue2 = allSymbolsQueue2;

        String projectDir = System.getProperty("user.dir");
        String assetsDir = projectDir + "/assets";
        symbolListFilePath = Paths.get(assetsDir + "/" + fileName);
    }

    public synchronized void putFlowingSymbolsToQueue() {
            try {
                Stream<String> lines = Files.lines(symbolListFilePath, charset);
                lines.forEach(
                        line -> {
                            if (line.matches("^(?!.*?#).*$")) {
                                line = line.substring(0, line.indexOf("|"));
                                FlowingSymbol flowingSymbol = new FlowingSymbol(nextId(), line);
                                allSymbolsQueue2.put(flowingSymbol);
                            }
                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    private static synchronized int nextId() {
        return id++;
    }
}
