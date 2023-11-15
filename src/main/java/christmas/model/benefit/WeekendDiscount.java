package christmas.model.benefit;

import christmas.model.Day;
import christmas.model.FoodCategory;
import christmas.model.MenuSheet;

import static christmas.config.EventConfig.EVENT_YEAR;
import static christmas.config.EventConfig.WEEKEND_DISCOUNT_LABEL;

public class WeekendDiscount extends Benefit {
    protected WeekendDiscount(int amount) {
        super(amount, WEEKEND_DISCOUNT_LABEL);
    }

    public static WeekendDiscount of(Day day, MenuSheet menuSheet) {
        if (day.isWeekend()) {
            int amount = menuSheet.getNumberOfMenu(FoodCategory.MAIN) * EVENT_YEAR;
            return new WeekendDiscount(amount);
        }
        return new WeekendDiscount(0);
    }

    public static Benefit empty() {
        return new WeekendDiscount(0);
    }
}
