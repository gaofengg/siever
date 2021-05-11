package com.huanhe_tech.siever.utils;

public class ColorSOP {
    public static final String ANSI_RESET ="\u001B[0m";
//    public static final String ANSI_BLACK ="\u001B[30m";
    public static final String ANSI_RED ="\u001B[31m";
    public static final String ANSI_GREEN ="\u001B[32m";
    public static final String ANSI_YELLOW ="\u001B[33m";
    public static final String ANSI_BLUE ="\u001B[34m";
//    public static final String ANSI_PURPLE ="\u001B[35m";
//    public static final String ANSI_CYAN ="\u001B[36m";
//    public static final String ANSI_WHITE ="\u001B[37m";
    public static final String ANSI_GRAY = "\u001B[38;5;244m";

    //info
    public static void i(String infoMessage) {
        System.out.println(ANSI_GRAY+ infoMessage + ANSI_RESET);
    }

    //error
    public static void e(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }

    //debug
    public static void d(String debugMessage) {
        System.out.println(ANSI_BLUE + debugMessage + ANSI_RESET);
    }

    //warning
    public static void w(String warningMessage) {
        System.out.println(ANSI_YELLOW + warningMessage + ANSI_RESET);
    }

    // yes
    public static void y(String yesMessage) {
        System.out.println(ANSI_GREEN + yesMessage + ANSI_RESET);
    }
}
