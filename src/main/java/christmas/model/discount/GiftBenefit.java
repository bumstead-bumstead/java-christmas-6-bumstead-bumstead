package christmas.model.discount;

import christmas.model.Gift;

public class GiftBenefit extends Benefit {
    protected GiftBenefit(int amount) {
        super(amount, "증정 이벤트");
    }

    public static GiftBenefit of(Gift gift) {
        return new GiftBenefit(gift.calculateTotalPrice());
    }

    public static Benefit empty() {
        return new GiftBenefit(0);
    }

}
