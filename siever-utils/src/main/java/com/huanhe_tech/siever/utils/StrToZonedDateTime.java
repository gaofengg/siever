package com.huanhe_tech.siever.utils;

import java.time.ZonedDateTime;

public class StrToZonedDateTime {
    private static final String NEW_YORK = "-04:00[GMT-04:00]";

    /**
     *
     * @param dateTime yy-MM-dd hh:mm:ss
     * @return ZoneDateTime
     */
    public static ZonedDateTime newYork(String dateTime) {
        String rDateTime = dateTime.replace(" ", "T");
        String zDateTime = rDateTime + NEW_YORK;
        return ZonedDateTime.parse(zDateTime);
    }
}
