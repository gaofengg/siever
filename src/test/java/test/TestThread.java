package test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestThread {
    @Test
    public void test01() {
        System.out.println("aa");
    }

    @Test
    public void test03() {
        String nowDate = LocalDate.now(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String dateTime = nowDate + " 18:00:00";
        System.out.println(dateTime);
    }

    @Test
    void arrayTest() {
        List<Double> list = Arrays.asList(12.5, 33.0, 9.4, 55.0, 32.0, 90.0, 34.0, 89.0);
        List<List<Double>> doubleElement = new ArrayList<>();
        for (int i = 0; i <= list.size() / 2 + 1; i = i + 2) {
            List<Double> tempList = list.subList(i, i + 2);
            doubleElement.add(tempList);
        }
        System.out.println(doubleElement);
    }

}
