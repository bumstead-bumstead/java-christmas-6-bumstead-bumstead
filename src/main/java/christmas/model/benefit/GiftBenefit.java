package christmas.model.benefit;

import christmas.model.Gift;

import static christmas.config.EventConfig.GIFT_BENEFIT_LABEL;

public class GiftBenefit extends Benefit {

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
