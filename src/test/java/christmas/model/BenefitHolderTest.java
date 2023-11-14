package christmas.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static christmas.config.EventConfig.*;
import static christmas.model.discount.DDayDiscount.*;
import static christmas.model.discount.SpecialDiscount.SPECIAL_DISCOUNT_LABEL;
import static christmas.model.discount.WeekdayDiscount.*;
import static christmas.model.discount.WeekendDiscount.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BenefitHolderTest {
    @DisplayName("Benefit 객체를 생성할 때,")
    @Nested
    class instanceCreation {
        private MenuSheet menuSheet;
        private Day day;
        private BenefitHolder benefitHolder;

        @BeforeEach
        void setup() {
            menuSheet = Mockito.mock(MenuSheet.class);
            when(menuSheet.calculateTotalPrice())
                    .thenReturn(10_000);

            day = Mockito.mock(Day.class);
        }

        @DisplayName("총 가격이 120_000을 넘으면, 증정품이 주어진다.")
        @Test
        void gift() {
            //given
            when(menuSheet.calculateTotalPrice())
                    .thenReturn(120_001);
            benefitHolder = BenefitHolder.of(day, menuSheet);
            //when
            Gift gift = benefitHolder.getGift();
            //then
            assertThat(gift.calculateTotalPrice())
                    .isGreaterThan(0);
        }

        @DisplayName("Day가 주말인 경우 weekendDiscount는 (메인 메뉴의 개수 * 2023)이다.")
        @Test
        void weekendDiscount() {
            //given
            int mainMenuCount = 2;
            when(day.isWeekend())
                    .thenReturn(true);
            when(menuSheet.getNumberOfMenu(FoodCategory.MAIN))
                    .thenReturn(mainMenuCount);
            benefitHolder = BenefitHolder.of(day, menuSheet);
            //when
            findDiscountAmountByLabel(benefitHolder, "주말 할인");
            int weekendDiscount = findDiscountAmountByLabel(benefitHolder, WEEKEND_DISCOUNT_LABEL);
            //then
            assertThat(weekendDiscount)
                    .isEqualTo(mainMenuCount * EVENT_YEAR);
        }

        private int findDiscountAmountByLabel(BenefitHolder benefitHolder, String label) {
            return benefitHolder.getDiscounts()
                    .stream()
                    .filter(discount -> discount.getLabel().equals(label))
                    .findFirst()
                    .get()
                    .getAmount();
        }

        @DisplayName("Day가 평일인 경우 weekdayDiscount는 (디저트 메뉴의 개수 * 2023)이다.")
        @Test
        void weekdayDiscount() {
            //given
            int dessertMenuCount = 3;
            when(day.isWeekend())
                    .thenReturn(false);
            when(menuSheet.getNumberOfMenu(FoodCategory.DESSERT))
                    .thenReturn(dessertMenuCount);
            benefitHolder = BenefitHolder.of(day, menuSheet);
            //when
            int weekdayDiscount = findDiscountAmountByLabel(benefitHolder, WEEKDAY_DISCOUNT_LABEL);
            //then
            assertThat(weekdayDiscount)
                    .isEqualTo(dessertMenuCount * EVENT_YEAR);
        }

        @DisplayName("Day가 달력에 별이 있는 날인 경우 specialDiscount는 1000이다.")
        @Test
        void specialDiscount() {
            //given
            when(day.isComponentOf(STARRED_DAYS))
                    .thenReturn(true);
            benefitHolder = BenefitHolder.of(day, menuSheet);
            //when
            int specialDiscount = findDiscountAmountByLabel(benefitHolder, SPECIAL_DISCOUNT_LABEL);
            //then
            assertThat(specialDiscount)
                    .isEqualTo(SPECIAL_DISCOUNT);
        }

        @DisplayName("총 주문금액이 10000 미만인 경우 gift가 주어지지 않고, 모든 할인은 0이다.")
        @Test
        void invalidTotalPayment() {
            //given
            when(menuSheet.calculateTotalPrice())
                    .thenReturn(9999);
            benefitHolder = BenefitHolder.of(day, menuSheet);
            //then
            assertThat(benefitHolder.getTotalDiscount())
                    .isEqualTo(0);
            assertThat(benefitHolder.getGift().calculateTotalPrice())
                    .isEqualTo(0);
        }

        @DisplayName("디데이 할인은 1일부터 25일까지 적용되며, 1000 + 100*(day-1)이다.")
        @MethodSource("provideDayAndDDayDiscount")
        @ParameterizedTest
        void dDayDiscount(int dayValue, int expectedDDayDiscount) {
            //given
            when(day.getDay())
                    .thenReturn(dayValue);
            benefitHolder = BenefitHolder.of(day, menuSheet);
            //when
            int dDayDiscount = findDiscountAmountByLabel(benefitHolder, D_DAY_DISCOUNT_LABEL);
            //then
            assertThat(dDayDiscount)
                    .isEqualTo(expectedDDayDiscount);
        }

        private static Stream<Arguments> provideDayAndDDayDiscount() {
            return Stream.of(
                    Arguments.of(1, 1000),
                    Arguments.of(2, 1100),
                    Arguments.of(15, 2400),
                    Arguments.of(25, 3400),
                    Arguments.of(26, 0),
                    Arguments.of(31, 0)
            );
        }
    }
}
