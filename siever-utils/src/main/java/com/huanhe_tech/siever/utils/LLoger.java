package com.huanhe_tech.siever.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLoger {
    public static Logger logger = LoggerFactory.getLogger(new Throwable().getStackTrace()[1].getClassName());
}
