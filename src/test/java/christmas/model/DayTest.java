package christmas.model;

import christmas.config.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.config.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DayTest {

    @DisplayName("Day 객체를 생성할 때,")
    @Nested
    class InstanceCreation {
        @DisplayName("정해진 범위를 벗어날 경우 IllegalArguementException을 발생시킨다.")
        @ValueSource(ints = {-1, 0, 32})
        @ParameterizedTest
        void invalidRange(int dayInput) {
            //when
            //then
            assertThatThrownBy(() -> Day.of(dayInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(DAY_INPUT_ERROR_MESSAGE);
        }

        @DisplayName("같은 값으로 반복적으로 생성할 경우 같은 객체를 반환한다.")
        @Test
        void SingletonTest() {
            //given
            int dayInput = 3;
            //when
            Day day1 = Day.of(dayInput);
            Day day2 = Day.of(dayInput);
            //then
            assertThat(day1).isSameAs(day2);
        }
    }

    @DisplayName("isWeekend()를 실행했을 때, 토요일, 일요일을 제외하고 false를 반환한다.")
    @MethodSource("provideDayAndBoolean")
    @ParameterizedTest
    void isWeekend(int dayInt, boolean expectedResult) {
        //given
        Day day = Day.of(dayInt);
        //when
        boolean actualResult = day.isWeekend();

        //then
        assertThat(actualResult)
                .isEqualTo(expectedResult);
    }

    @DisplayName("isComponentOf()를 실행했을 때, 해당 리스트에 포함되는 경우 true를 반환한다.")
    @MethodSource("provideDayAndBoolean")
    @ParameterizedTest
    void isComponentOf(int dayInt, boolean expectedResult) {
        //given
        List<Integer> list = List.of(2, 3);
        Day day = Day.of(dayInt);
        //when
        boolean actualResult = day.isComponentOf(list);

        //then
        assertThat(actualResult)
                .isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideDayAndBoolean() {
        return Stream.of(
                Arguments.of(1, false),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, false),
                Arguments.of(5, false)
        );
    }
}