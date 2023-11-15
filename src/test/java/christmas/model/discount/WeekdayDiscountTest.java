package christmas.model.discount;

import christmas.model.BenefitHolder;
import christmas.model.Day;
import christmas.model.FoodCategory;
import christmas.model.MenuSheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static christmas.config.EventConfig.EVENT_YEAR;
import static christmas.model.discount.WeekdayDiscount.WEEKDAY_DISCOUNT_LABEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WeekdayDiscountTest {
    private final Day day = Mockito.mock(Day.class);
    private final MenuSheet menuSheet = Mockito.mock(MenuSheet.class);
    @DisplayName("평일인 경우, 디저트 메뉴의 개수에 대한 할인을 갖는다.")
    @Test
    void weekdayDiscountInitialization() {
        //given
        int dessertMenuCount = 3;
        when(day.isWeekend())
                .thenReturn(false);
        when(menuSheet.getNumberOfMenu(FoodCategory.DESSERT))
                .thenReturn(dessertMenuCount);
        WeekdayDiscount weekdayDiscount = WeekdayDiscount.of(day, menuSheet);
        //when
        int amount = weekdayDiscount.getAmount();
        //then
        assertThat(amount)
                .isEqualTo(dessertMenuCount * EVENT_YEAR);
    }

}