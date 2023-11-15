package christmas.model.benefit;

import christmas.model.Day;
import christmas.model.FoodCategory;
import christmas.model.MenuSheet;

import static christmas.config.EventConfig.EVENT_YEAR;
import static christmas.config.EventConfig.WEEKDAY_DISCOUNT_LABEL;

public class WeekdayDiscount extends Benefit {

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
