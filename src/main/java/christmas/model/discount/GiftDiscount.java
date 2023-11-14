package christmas.model.discount;

import christmas.model.Day;
import christmas.model.Gift;

import static christmas.config.EventConfig.SPECIAL_DISCOUNT;
import static christmas.config.EventConfig.STARRED_DAYS;

public class GiftDiscount extends Discount {
    protected GiftDiscount(int amount) {
        super(amount, "증정 이벤트");
    }

    public static GiftDiscount of(Gift gift) {
        return new GiftDiscount(gift.calculateTotalPrice());
    }

    public static Discount empty() {
        return new GiftDiscount(0);
    }

}
