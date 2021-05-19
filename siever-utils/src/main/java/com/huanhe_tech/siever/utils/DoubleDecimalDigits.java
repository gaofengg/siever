package com.huanhe_tech.siever.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleDecimalDigits {
    public static double transition (int digits, double number)  {
        number = new BigDecimal(number).setScale(digits, RoundingMode.HALF_DOWN).doubleValue();
        return number;
    }
}
