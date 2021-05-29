package com.huanhe_tech.cli.DAO;

import com.huanhe_tech.cli.GlobalFlags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest2 {
    public static void main(String[] args) {
        run();
        Thread t1 = new Thread(() -> {
            System.out.println("aaaa");
        });
        t1.setDaemon(true);
        t1.start();
    }

    public static void run() {
        List<Integer> lists = new ArrayList<>(Arrays.asList(1, 8, 0, 3, 5, 2));
        Iterator<Integer> iterator = lists.iterator();
        new Thread(() -> {
            synchronized (GlobalFlags.UpdateHistDone.STATE) {
                while (GlobalFlags.UpdateHistDone.STATE.getB()) {
                    GlobalFlags.UpdateHistDone.STATE.setB(false);
                    if (iterator.hasNext()) {
                        int list = iterator.next();
                        if (list == 0) {
                            list = 99;
                        }
                        int finalList = list;
                        new Thread(() -> {
                            synchronized (GlobalFlags.UpdateHistDone.STATE) {
                                if (!GlobalFlags.UpdateHistDone.STATE.getB()) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(finalList);
                                    GlobalFlags.UpdateHistDone.STATE.setB(true);
                                    GlobalFlags.UpdateHistDone.STATE.notifyAll();
                                }
                            }
                        }).start();
                    } else {
                        break;
                    }
                    try {
                        GlobalFlags.UpdateHistDone.STATE.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
