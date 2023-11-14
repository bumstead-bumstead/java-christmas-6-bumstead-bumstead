package christmas.model.discount;

import christmas.model.Gift;

public class GiftBenefit extends Benefit {
    public static final String GIFT_BENEFIT_LABEL = "증정 이벤트";

    protected GiftBenefit(int amount) {
        super(amount, GIFT_BENEFIT_LABEL);
    }

    public static GiftBenefit of(Gift gift) {
        return new GiftBenefit(gift.calculateTotalPrice());
    }

    public static Benefit empty() {
        return new GiftBenefit(0);
    }

}
