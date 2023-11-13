package christmas.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static christmas.config.EventConfig.*;
import static java.time.DayOfWeek.*;

public class Day {
    private static final Map<Integer, Day> dayCache = new HashMap<>();

    static {
        IntStream.range(DAY_START, DAY_END + 1)
                .forEach(dayNumber -> dayCache.put(dayNumber, new Day(dayNumber)));
    }

    private final int day;

    private Day(int day) {
        this.day = day;
    }

    public static Day of(int day) {
        validateRange(day);
        return dayCache.get(day);
    }

    public int getDay() {
        return day;
    }

    public boolean isComponentOf(List<Integer> days) {
        return days.contains(day);
    }

    public boolean isWeekend() {
        LocalDate localDate = LocalDate.of(EVENT_YEAR, EVENT_MONTH, day);
        DayOfWeek DayOfWeek = localDate.getDayOfWeek();

        return DayOfWeek.equals(SATURDAY) || DayOfWeek.equals(SUNDAY);
    }

    private static void validateRange(int day) {
        if (isInvalidDay(day)) {
            throw new IllegalArgumentException("1에서 31일 사이의 값을 입력해주세요.");
        }
    }

    private static boolean isInvalidDay(int day) {
        return day < DAY_START || day > DAY_END;
    }
}
