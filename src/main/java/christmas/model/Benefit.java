package christmas.model;

import static christmas.config.EventConfig.*;

public class Benefit {
    private final Menu gift;
    private final int totalPrice;
    private final int specialDiscount;
    private final int weekendDiscount;
    private final int weekdayDiscount;
    private final int dDayDiscount;

    private Benefit(Menu gift,
                    int totalPrice,
                    int specialDiscount,
                    int weekendDiscount,
                    int weekdayDiscount,
                    int dDayDiscount) {
        this.gift = gift;
        this.totalPrice = totalPrice;
        this.specialDiscount = specialDiscount;
        this.weekendDiscount = weekendDiscount;
        this.weekdayDiscount = weekdayDiscount;
        this.dDayDiscount = dDayDiscount;
    }

    public Menu getGift() {
        return gift;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getSpecialDiscount() {
        return specialDiscount;
    }

    public int getWeekendDiscount() {
        return weekendDiscount;
    }

    public int getWeekdayDiscount() {
        return weekdayDiscount;
    }

    public int getdDayDiscount() {
        return dDayDiscount;
    }

    public Badge getBadge() {
        return Badge.fromTotalBenefit(getTotalBenefit());
    }

    public int getTotalBenefit() {
        return dDayDiscount +
                weekendDiscount +
                weekdayDiscount +
                specialDiscount +
                gift.getPrice();
    }

    public int getTotalDiscount() {
        return dDayDiscount +
                weekendDiscount +
                weekdayDiscount +
                specialDiscount;
    }

    public boolean hasBenefit() {
        return dDayDiscount > 0 ||
                weekdayDiscount > 0 ||
                weekendDiscount > 0 ||
                specialDiscount > 0 ||
                !gift.equals(Menu.NO_FOOD);
    }

    public static Benefit of(Day day, MenuSheet menuSheet) {
        int totalPrice = menuSheet.calculateTotalPrice();
        if (totalPrice == 0) {
            return empty(totalPrice);
        }

        return new Benefit(
                calculateGift(totalPrice),
                totalPrice,
                calculateSpecialDiscount(day),
                calculateWeekendDiscount(day, menuSheet),
                calculateWeekdayDiscount(day, menuSheet),
                calculateDDayDiscount(day)
        );
    }

    private static Menu calculateGift(int totalPrice) {
        if (totalPrice > PURCHASE_AMOUNT_BASELINE) {
            return DEFAULT_GIFT;
        }
        return Menu.NO_FOOD;
    }

    private static int calculateDDayDiscount(Day day) {
        if (day.getDay() > END_DAY_OF_D_DAY_EVENT) {
            return 0;
        }
        return D_DAY_DISCOUNT_LOWER_BOUND + (day.getDay() - 1) * D_DAY_DISCOUNT_UNIT;
    }

    private static int calculateWeekdayDiscount(Day day, MenuSheet menuSheet) {
        if (!day.isWeekend()) {
            return menuSheet.getNumberOfMenuCategory(FoodCategory.DESSERT) * EVENT_YEAR;
        }
        return 0;
    }

    private static int calculateWeekendDiscount(Day day, MenuSheet menuSheet) {
        if (day.isWeekend()) {
            return menuSheet.getNumberOfMenuCategory(FoodCategory.MAIN) * EVENT_YEAR;
        }
        return 0;
    }


    private static int calculateSpecialDiscount(Day day) {
        if (day.isComponentOf(STARRED_DAYS)) {
            return SPECIAL_DISCOUNT;
        }
        return 0;
    }

    private static Benefit empty(int totalPrice) {
        return new Benefit(
                Menu.NO_FOOD,
                totalPrice,
                0,
                0,
                0,
                0
        );
    }
}
