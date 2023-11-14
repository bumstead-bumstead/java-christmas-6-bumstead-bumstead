package christmas.model.discount;

import christmas.model.Day;
import christmas.model.FoodCategory;
import christmas.model.MenuSheet;

import static christmas.config.EventConfig.EVENT_YEAR;

public class WeekdayDiscount extends Benefit {
    public static final String WEEKDAY_DISCOUNT_LABEL = "평일 할인";

    protected WeekdayDiscount(int amount) {
        super(amount, WEEKDAY_DISCOUNT_LABEL);
    }

    public static WeekdayDiscount of(Day day, MenuSheet menuSheet) {
        if (!day.isWeekend()) {
            int amount = menuSheet.getNumberOfMenu(FoodCategory.DESSERT) * EVENT_YEAR;
            return new WeekdayDiscount(amount);
        }
        return new WeekdayDiscount(0);
    }

    public static Benefit empty() {
        return new WeekdayDiscount(0);
    }

}
