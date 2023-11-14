package christmas.model.discount;

import christmas.model.Day;

import static christmas.config.EventConfig.*;

public class DDayDiscount extends Discount {
    private DDayDiscount(int amount) {
        super(amount, "크리스마스 디데이 할인");
    }

    public static Discount of(Day day) {
        if (day.getDay() > END_DAY_OF_D_DAY_EVENT) {
            return new DDayDiscount(0);
        }
        int amount = D_DAY_DISCOUNT_LOWER_BOUND + (day.getDay() - 1) * D_DAY_DISCOUNT_UNIT;

        return new DDayDiscount(amount);
    }

    public static Discount empty() {
        return new DDayDiscount(0);
    }
}