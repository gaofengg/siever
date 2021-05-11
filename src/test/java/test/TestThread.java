package test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TestThread {
    @Test
    public void test01() {
        System.out.println("aa");
    }

    @Test
    public void threadStream() {

    }

    @Test
    public void test03() {
        String nowDate = LocalDate.now(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String dateTime = nowDate + " 18:00:00";
        System.out.println(dateTime);
    }
}
