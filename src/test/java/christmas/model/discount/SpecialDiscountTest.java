package christmas.model.discount;

import christmas.model.Day;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SpecialDiscountTest {
    @DisplayName("날짜에 대한 특별 할인을 갖는다.")
    @MethodSource("provideDayAndExpectedSpecialDiscount")
    @ParameterizedTest(name = "Day = {0}, amount = {1}")
    void dDayInitialization(Day day, int expectedAmount) {
        //when
        Benefit discount = SpecialDiscount.of(day);
        int actualAmount = discount.getAmount();
        //then
        assertThat(actualAmount).isEqualTo(expectedAmount);
    }

    private static Stream<Arguments> provideDayAndExpectedSpecialDiscount() {
        return Stream.of(
                Arguments.of(Day.of(3), 1000),
                Arguments.of(Day.of(10), 1000),
                Arguments.of(Day.of(17), 1000),
                Arguments.of(Day.of(24), 1000),
                Arguments.of(Day.of(25), 1000),
                Arguments.of(Day.of(2), 0)
        );
    }
}