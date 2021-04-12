package com.huanhe_tech.siever.test;

import com.huanhe_tech.cli.*;
import com.huanhe_tech.cli.queue.AllSymbolsQueue;
import com.ib.client.Types;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MyTest {

    @Test
    public void exportSymbol() {
    }

    @Test
    public void bilibili() {
        Path path = Paths.get("/Users/gaofeng/Downloads/bilibili/bilibili.txt");
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 57 ; i++) {
            list.add("https://www.bilibili.com/video/BV1eJ411c7rf?p=" + i);
        }
        try {
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pc() {
        AllSymbolsQueue allSymbolsQueue2 = new AllSymbolsQueue(5);
        ProduceAllSymbols produceAllSymbols = new ProduceAllSymbols("test.txt", allSymbolsQueue2);
        ConsumeAllSymbols consumeAllSymbols = new ConsumeAllSymbols(allSymbolsQueue2);

        new Thread(produceAllSymbols::putFlowingSymbolsToQueue).start();


        for (int i = 0; i < 2; i++) {
            new Thread(consumeAllSymbols::takeFlowingSymbolFormQueue).start();
        }
    }

    @Test
    public void testObjectPool() {
//        System.out.println(ObjectPool.getAllSymbolsQueue().hashCode());
//        System.out.println(ObjectPool.getAllSymbolsQueue().hashCode());
//        System.out.println(ObjectPool.getAllSymbolsQueue().hashCode());

//        System.out.println(InstancePool.getProduceAllSymbols("test.txt").hashCode());
//        System.out.println(InstancePool.getProduceAllSymbols("test.txt").hashCode());
//        System.out.println(InstancePool.getProduceAllSymbols("test.txt").hashCode());
//        System.out.println(InstancePool.getProduceAllSymbols("test.txt").hashCode());
    }

    @Test
    public void testApi() {
//        System.out.println(GlobalFlags.ReqTypeFlag.STATE);
        System.out.println("aaa");
    }

    @Test
    public void aab() {
        System.out.println("aab");
    }

}
