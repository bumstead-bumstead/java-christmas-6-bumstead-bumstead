package christmas.model;

import christmas.config.EventConfig;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static christmas.config.EventConfig.*;
import static java.time.DayOfWeek.*;

public class Day {
    private static final Map<Integer, Day> dayCache = new HashMap<>();
    static {
        IntStream.range(1, 32)
                .forEach(dayNumber -> dayCache.put(dayNumber, new Day(dayNumber)));
    }
    private final int day;

    private Day(int day) {
        validate(day);
        this.day = day;
    }

    public static Day of(int day) {
        return dayCache.get(day);
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
