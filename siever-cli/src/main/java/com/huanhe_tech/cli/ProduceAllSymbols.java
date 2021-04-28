package com.huanhe_tech.cli;

import com.huanhe_tech.cli.queue.AllFlowingSymbol;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.huanhe_tech.siever.utils.LoadReSrc;

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
    private final AllSymbolsQueue allSymbolsQueue;

    /**
     * @param fileName        TWS 官方提供的 symbol 列表文件名
     * @param allSymbolsQueue 存放 symbols 完整列表的队列容器
     */
    public ProduceAllSymbols(String fileName, AllSymbolsQueue allSymbolsQueue) {
        this.allSymbolsQueue = allSymbolsQueue;

        symbolListFilePath = Paths.get(new LoadReSrc("resources", fileName).getUri());
    }

    public void putFlowingSymbolsToQueue() {
            try {
                Stream<String> lines = Files.lines(symbolListFilePath, charset);
                lines.forEach(
                        line -> {
                                if (line.matches("^(?!.*?#B|#S).*$")) {
                                    line = line.substring(0, line.indexOf("|"));
                                    AllFlowingSymbol flowingSymbol = new AllFlowingSymbol(nextId(), line);
                                    allSymbolsQueue.putSymbol(flowingSymbol);
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
