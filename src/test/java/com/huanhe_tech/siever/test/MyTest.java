package com.huanhe_tech.siever.test;

import com.huanhe_tech.cli.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MyTest {

    @Test
    public void exportSymbol() {
    }
    
    @Test
    public void bilibili() {
        Path path = Paths.get("/Users/gaofeng/Downloads/bibi/bilibili.txt");
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 282; i++) {
            list.add("https://www.bilibili.com/video/BV16J411h7Rd?p=" + i);
        }
        try {
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pc() {
        AllSymbolsQueue2 allSymbolsQueue2 = new AllSymbolsQueue2(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                new ProduceAllSymbols("test.txt", allSymbolsQueue2, FlowingSymbol.INSTANCE).putFlowingSymbolsToQueue();
            }, Thread.currentThread().getName()).start();
        }

        new ConsumeAllSymbols(allSymbolsQueue2).takeFlowingSymbolFormQueue();

    }
}
