package com.huanhe_tech.cli.strategies;

public interface Strategy<T extends Object> {
    void run(T t);
}
