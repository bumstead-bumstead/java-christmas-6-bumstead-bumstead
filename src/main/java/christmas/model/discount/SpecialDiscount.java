package christmas.model.discount;

import christmas.model.Day;

import static christmas.config.EventConfig.SPECIAL_DISCOUNT;
import static christmas.config.EventConfig.STARRED_DAYS;

public class SpecialDiscount extends Benefit {
    public static final String SPECIAL_DISCOUNT_LABEL = "특별 할인";

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
