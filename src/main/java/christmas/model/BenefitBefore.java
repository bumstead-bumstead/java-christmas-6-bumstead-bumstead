package christmas.model;

import static christmas.config.EventConfig.*;

//todo : 필드 너무 많음. totalPrice는 불필요한 필드. Discount 일반화, 컬렉션으로 관리
// -> 구조를 아예 수정할 필요가...
// 매개변수 final 붙이기 고려
// displayname에 메소드이름 쓰지 말기 수정 소요
public class BenefitBefore {
    private final Menu gift;
    private final int totalPrice;
    private final int specialDiscount;
    private final int weekendDiscount;
    private final int weekdayDiscount;
    private final int dDayDiscount;

    private BenefitBefore(Menu gift,
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

    public static BenefitBefore of(Day day, MenuSheet menuSheet) {
        int totalPrice = menuSheet.calculateTotalPrice();
        if (totalPrice < MINIMUM_ORDER_AMOUNT) {
            return empty(totalPrice);
        }

        return new BenefitBefore(
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
            return menuSheet.getNumberOfMenu(FoodCategory.DESSERT) * EVENT_YEAR;
        }
        return 0;
    }

    private static int calculateWeekendDiscount(Day day, MenuSheet menuSheet) {
        if (day.isWeekend()) {
            return menuSheet.getNumberOfMenu(FoodCategory.MAIN) * EVENT_YEAR;
        }
        return 0;
    }


    private static int calculateSpecialDiscount(Day day) {
        if (day.isComponentOf(STARRED_DAYS)) {
            return SPECIAL_DISCOUNT;
        }
        return 0;
    }

    private static BenefitBefore empty(int totalPrice) {
        return new BenefitBefore(
                Menu.NO_FOOD,
                totalPrice,
                0,
                0,
                0,
                0
        );
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

    public int getDDayDiscount() {
        return dDayDiscount;
    }

    public Badge getBadge() {
        return Badge.fromTotalBenefit(getTotalBenefit());
    }

    public int getTotalBenefit() {
        return getTotalDiscount() + gift.getPrice();
    }

    public int getTotalDiscount() {
        return dDayDiscount +
                weekendDiscount +
                weekdayDiscount +
                specialDiscount;
    }

    public boolean hasBenefit() {
        return getTotalBenefit() > 0;
    }
}
