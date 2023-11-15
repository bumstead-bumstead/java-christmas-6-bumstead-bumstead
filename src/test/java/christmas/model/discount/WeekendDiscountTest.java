package christmas.model.discount;

import christmas.model.Day;
import christmas.model.FoodCategory;
import christmas.model.MenuSheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static christmas.config.EventConfig.EVENT_YEAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WeekendDiscountTest {
    private final Day day = Mockito.mock(Day.class);
    private final MenuSheet menuSheet = Mockito.mock(MenuSheet.class);
    @DisplayName("평일인 경우, 디저트 메뉴의 개수에 대한 할인을 갖는다.")
    @Test
    void weekdayDiscountInitialization() {
        //given
        int mainMenuCount = 3;
        when(day.isWeekend())
                .thenReturn(true);
        when(menuSheet.getNumberOfMenu(FoodCategory.MAIN))
                .thenReturn(mainMenuCount);
        WeekendDiscount weekendDiscount = WeekendDiscount.of(day, menuSheet);
        //when
        int amount = weekendDiscount.getAmount();
        //then
        assertThat(amount)
                .isEqualTo(mainMenuCount * EVENT_YEAR);
    }

}