package christmas.model.benefit;

import christmas.model.Day;

import static christmas.config.EventConfig.*;

public class SpecialDiscount extends Benefit {

    private SpecialDiscount(int amount) {
        super(amount, SPECIAL_DISCOUNT_LABEL);
    }

    public static SpecialDiscount of(Day day) {
        if (day.isComponentOf(STARRED_DAYS)) {
            return new SpecialDiscount(SPECIAL_DISCOUNT);
        }
        return new SpecialDiscount(0);
    }

    public static Benefit empty() {
        return new SpecialDiscount(0);
    }

}
