package com.huanhe_tech.cli;

import java.util.LinkedList;

public class AllSymbolsQueue2 {
    private final LinkedList<FlowingSymbol> list = new LinkedList<>();
    private final int capacity;

    public AllSymbolsQueue2(int capacity) {
        this.capacity = capacity;
    }

    // 向队列中尾部添加数据
    public void put(FlowingSymbol flowingSymbol) {
        synchronized (list) {
            while (list.size() == capacity) {
                try {
                    System.out.println("队列已满");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(flowingSymbol);
            System.out.println("已向队列尾部加入：" + flowingSymbol);
            list.notifyAll();
            System.out.println("唤醒take");
        }

    }

    // 从队列头中取出并删除数据
    public FlowingSymbol take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    System.out.println("队列为空");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            FlowingSymbol flowingSymbol = list.removeFirst();
            System.out.println("已从队列头部获取并删除：" + flowingSymbol);
            list.notifyAll();
            return flowingSymbol;
        }
    }
}
