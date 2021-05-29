package com.huanhe_tech.cli.crawler;

public class SymbolRename {
    private final String str;

    public SymbolRename(String str) {
        this.str = str;
    }

    public String translate() {
        String str1 = str.replace(" ", "-");
        String str11 = str1.substring(0, str1.length() - 2);
        String str12 = str1.substring(str1.length() - 1);
        return str11.concat(str12);
    }
}
