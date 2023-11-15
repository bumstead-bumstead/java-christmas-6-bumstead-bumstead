package christmas.model.discount;

import christmas.model.Day;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DDayDiscountTest {
    @DisplayName("날짜에 대한 디데이 할인 금액을 갖는다.")
    @MethodSource("provideDayAndExpectedDDayDiscount")
    @ParameterizedTest(name = "Day = {0}, amount = {1}")
    void dDayInitialization(Day day, int expectedAmount) {
        //when
        Benefit discount = DDayDiscount.of(day);
        int actualAmount = discount.getAmount();
        //then
        assertThat(actualAmount).isEqualTo(expectedAmount);
    }

    private static Stream<Arguments> provideDayAndExpectedDDayDiscount() {
        return Stream.of(
                Arguments.of(Day.of(5), 1400),
                Arguments.of(Day.of(10), 1900),
                Arguments.of(Day.of(15), 2400),
                Arguments.of(Day.of(26), 0),
                Arguments.of(Day.of(31), 0)
        );
    }
}