package christmas.model;

import christmas.config.EventConfig;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static christmas.config.EventConfig.*;
import static java.time.DayOfWeek.*;

public class Day {
    private final int day;

    public Day(int day) {
        validate(day);
        this.day = day;
    }

    public boolean isWeekend() {
        LocalDate localDate = LocalDate.of(EVENT_YEAR, EVENT_MONTH, day);
        DayOfWeek DayOfWeek = localDate.getDayOfWeek();

        return DayOfWeek.equals(SATURDAY) || DayOfWeek.equals(SUNDAY);
    }

    private void validate(int day) {
        if (isInvalidDay(day)) {
            throw new IllegalArgumentException("1에서 31일 사이의 값을 입력해주세요.");
        }
    }

    private static boolean isInvalidDay(int day) {
        return day < 1 || day > 31;
    }
}
