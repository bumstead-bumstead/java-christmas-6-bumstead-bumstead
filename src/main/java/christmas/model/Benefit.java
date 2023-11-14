package christmas.model;

import christmas.model.discount.*;
import java.util.List;

import static christmas.config.EventConfig.*;

public class Benefit {
    private final List<Discount> discounts;
    private final Gift gift;

    private Benefit(List<Discount> discounts, Gift gift) {
        this.discounts = discounts;
        this.gift = gift;
    }

    public static Benefit of(Day day, MenuSheet menuSheet) {
        if (menuSheet.calculateTotalPrice() < MINIMUM_ORDER_AMOUNT) {
            return new Benefit(collectEmptyDiscounts(), Gift.fromTotalPrice(0));
        }

        Gift gift = Gift.fromTotalPrice(menuSheet.calculateTotalPrice());
        List<Discount> discounts = collectDiscounts(menuSheet, day, gift);
        return new Benefit(discounts, gift);
    }

    public Gift getGift() {
        return gift;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public int getTotalBenefit() {
        return sumOfDiscounts(discounts);
    }

    public int getTotalDiscount() {
        return sumOfDiscounts(discounts) - gift.calculateTotalPrice();
    }

    public Badge getBadge() {
        return Badge.fromTotalBenefit(getTotalBenefit());
    }

    public boolean hasBenefit() {
        return getTotalBenefit() > 0;
    }

    private static int sumOfDiscounts(List<Discount> discounts) {
        return discounts.stream()
                .map(Discount::getAmount)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static List<Discount> collectDiscounts(MenuSheet menuSheet, Day day, Gift gift) {
        return List.of(
                DDayDiscount.of(day),
                SpecialDiscount.of(day),
                WeekendDiscount.of(day, menuSheet),
                WeekdayDiscount.of(day, menuSheet),
                GiftDiscount.of(gift)
        );
    }

    private static List<Discount> collectEmptyDiscounts() {
        return List.of(
                DDayDiscount.empty(),
                SpecialDiscount.empty(),
                WeekendDiscount.empty(),
                WeekdayDiscount.empty(),
                GiftDiscount.empty()
        );
    }
}
