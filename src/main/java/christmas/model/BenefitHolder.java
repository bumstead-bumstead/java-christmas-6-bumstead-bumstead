package christmas.model;

import christmas.model.discount.*;
import java.util.List;

import static christmas.config.EventConfig.*;

public class BenefitHolder {
    private final List<Benefit> benefits;
    private final Gift gift;

    private BenefitHolder(List<Benefit> benefits, Gift gift) {
        this.benefits = benefits;
        this.gift = gift;
    }

    public static BenefitHolder of(Day day, MenuSheet menuSheet) {
        if (menuSheet.calculateTotalPrice() < MINIMUM_ORDER_AMOUNT) {
            return new BenefitHolder(collectEmptyDiscounts(), Gift.fromTotalPrice(0));
        }

        Gift gift = Gift.fromTotalPrice(menuSheet.calculateTotalPrice());
        List<Benefit> benefits = collectDiscounts(menuSheet, day, gift);
        return new BenefitHolder(benefits, gift);
    }

    public Gift getGift() {
        return gift;
    }

    public List<Benefit> getDiscounts() {
        return benefits;
    }

    public int getTotalBenefit() {
        return sumOfDiscounts(benefits);
    }

    public int getTotalDiscount() {
        return sumOfDiscounts(benefits) - gift.calculateTotalPrice();
    }

    public Badge getBadge() {
        return Badge.fromTotalBenefit(getTotalBenefit());
    }

    public boolean hasBenefit() {
        return getTotalBenefit() > 0;
    }

    private static int sumOfDiscounts(List<Benefit> benefits) {
        return benefits.stream()
                .map(Benefit::getAmount)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static List<Benefit> collectDiscounts(MenuSheet menuSheet, Day day, Gift gift) {
        return List.of(
                DDayDiscount.of(day),
                SpecialDiscount.of(day),
                WeekendDiscount.of(day, menuSheet),
                WeekdayDiscount.of(day, menuSheet),
                GiftBenefit.of(gift)
        );
    }

    private static List<Benefit> collectEmptyDiscounts() {
        return List.of(
                DDayDiscount.empty(),
                SpecialDiscount.empty(),
                WeekendDiscount.empty(),
                WeekdayDiscount.empty(),
                GiftBenefit.empty()
        );
    }
}
