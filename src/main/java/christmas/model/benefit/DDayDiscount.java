package christmas.model.benefit;

import christmas.model.Day;

import static christmas.config.EventConfig.*;

public class DDayDiscount extends Benefit {

    private DDayDiscount(int amount) {
        super(amount, D_DAY_DISCOUNT_LABEL);
    }

    public static DDayDiscount of(Day day) {
        if (day.getDay() > END_DAY_OF_D_DAY_EVENT) {
            return new DDayDiscount(0);
        }
        int amount = D_DAY_DISCOUNT_LOWER_BOUND + (day.getDay() - 1) * D_DAY_DISCOUNT_UNIT;

        return new DDayDiscount(amount);
    }

    public static Benefit empty() {
        return new DDayDiscount(0);
    }
}
