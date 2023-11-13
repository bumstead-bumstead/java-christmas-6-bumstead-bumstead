package christmas.model;

import static christmas.config.EventConfig.*;

public class Benefit {
    private final Menu gift;
    private final int totalPrice;
    private final int specialDiscount;
    private final int weekendDiscount;
    private final int weekdayDiscount;
    private final int dDayDiscount;

    /*
     * Order 객체를 입력받아서 바로 생성하느냐... 아님 필드를 입력받아서 생성하느냐...
     * 혜택 계산 관련 로직은 여기에 있는 게 맞다.
     * */

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
        return null;
    }

    private static int calculateDDayDiscount(Day day) {
        return D_DAY_DISCOUNT_LOWER_BOUND + day.getDay() * D_DAY_DISCOUNT_UNIT;
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
                null,
                totalPrice,
                0,
                0,
                0,
                0
        );
    }
}
