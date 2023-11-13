package christmas.view;

import christmas.config.EventConfig;
import christmas.dto.MenuDto;
import christmas.model.*;

import java.text.DecimalFormat;
import java.util.List;

public class OutputView {
    private static final String START_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String DAY_OF_VISIT_INPUT_MESSAGE =
            "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_INPUT_MESSAGE =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    public static final String ERROR_PREFIX = "[ERROR] ";
    public static final String BENEFIT_INTRODUCE_FORMAT = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    public static final String ORDERED_MENU_FORMAT = "%s %d개";
    private static final String AMOUNT_PATTERN = "#,###";
    public static final String NOTHING_MESSAGE = "없음";
    public static final String GIFT_HEADER = "<증정 메뉴>";
    public static final String TOTAL_PRICE_HEADER = "<할인 전 총주문 금액>";
    public static final String ORDERD_MENU_HEADER = "<주문 메뉴>";
    public static final String BENEFIT_HEADER = "<혜택 내역>";
    public static final String TOTAL_BENEFIT_PRICE_HEADER = "<총혜택 금액>";
    public static final String TOTAL_PAYMENT_PRICE_HEADER = "<할인 후 예상 결제 금액>";
    public static final String EVENT_BADGE_HEADER = "<12월 이벤트 배지>";
    public static final String PRICE_FORMAT = "%s원";
    public static final String D_DAY_DISCOUNT_FORMAT = "크리스마스 디데이 할인: -%s원";
    public static final String WEEKDAY_DISCOUNT_FORMAT = "평일 할인: -%s원";
    public static final String WEEKEND_DISCOUNT_FORMAT = "주말 할인: -%s원";
    public static final String SPECIAL_DISCOUNT_FORMAT = "특별 할인: -%s원";
    public static final String GIFT_DISCOUNT_FORMAT = "증정 이벤트: -%s원";

    public void printStartMessage() {
        System.out.println(START_MESSAGE);
    }

    public void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public void printDayOfVisitInputMessage() {
        System.out.println(DAY_OF_VISIT_INPUT_MESSAGE);
    }

    public void printMenuInputMessage() {
        System.out.println(MENU_INPUT_MESSAGE);
    }

    public void printBenefit(Benefit benefit) {
        printTotalPrice(benefit.getTotalPrice());
        printGift(benefit.getGift());
        printBenefits(benefit);
        printTotalBenefitPrice(benefit.getTotalBenefit());
        printAmountOfPayment(benefit.getTotalDiscount(), benefit.getTotalPrice());
        printEventBadge(benefit.getBadge());
    }

    private void printEventBadge(Badge badge) {
        System.out.println(EVENT_BADGE_HEADER);
        if (badge.equals(Badge.NO_BADGE)) {
            System.out.println(NOTHING_MESSAGE);
            return;
        }
        System.out.println(badge.getLabel());
    }

    private void printAmountOfPayment(int totalBenefit, int totalPrice) {
        System.out.println(TOTAL_PAYMENT_PRICE_HEADER);
        System.out.println(String.format(PRICE_FORMAT, formatIntegerToAmount(totalPrice - totalBenefit)));
        printNewLine();
    }

    private void printTotalBenefitPrice(int totalBenefit) {
        System.out.println(TOTAL_BENEFIT_PRICE_HEADER);
        if (totalBenefit == 0) {
            System.out.println(String.format(PRICE_FORMAT, formatIntegerToAmount(totalBenefit)));
            printNewLine();
            return;
        }
        System.out.println(String.format("-" + PRICE_FORMAT, formatIntegerToAmount(totalBenefit)));
        printNewLine();
    }

    private void printBenefits(Benefit benefit) {
        System.out.println(BENEFIT_HEADER);
        printDDayDiscount(benefit.getdDayDiscount());
        printWeekdayDiscount(benefit.getWeekdayDiscount());
        printWeekendDiscount(benefit.getWeekendDiscount());
        printSpecialDiscount(benefit.getSpecialDiscount());
        printGiftDiscount(benefit.getGift());
        if (!benefit.hasBenefit()) {
            System.out.println(NOTHING_MESSAGE);
        }
        printNewLine();
    }

    private void printDDayDiscount(int dDayDiscount) {
        if (dDayDiscount != 0) {
            System.out.println(String.format(
                    D_DAY_DISCOUNT_FORMAT,
                    formatIntegerToAmount(dDayDiscount)
            ));
        }
    }

    private void printWeekdayDiscount(int weekdayDiscount) {
        if (weekdayDiscount != 0) {
            System.out.println(String.format(
                    WEEKDAY_DISCOUNT_FORMAT,
                    formatIntegerToAmount(weekdayDiscount)
            ));
        }
    }

    private void printWeekendDiscount(int weekendDiscount) {
        if (weekendDiscount != 0) {
            System.out.println(String.format(
                    WEEKEND_DISCOUNT_FORMAT,
                    formatIntegerToAmount(weekendDiscount)
            ));
        }
    }

    private void printSpecialDiscount(int specialDiscount) {
        if (specialDiscount != 0) {
            System.out.println(String.format(
                    SPECIAL_DISCOUNT_FORMAT,
                    formatIntegerToAmount(specialDiscount)
            ));
        }
    }

    private void printGiftDiscount(Menu gift) {
        if (!gift.isKindOf(FoodCategory.NO_CATEGORY)) {
            System.out.println(String.format(
                    GIFT_DISCOUNT_FORMAT,
                    formatIntegerToAmount(gift.getPrice())
            ));
        }
    }

    private void printGift(Menu gift) {
        System.out.println(GIFT_HEADER);
        if (gift.equals(Menu.NO_FOOD)) {
            System.out.println(NOTHING_MESSAGE);
            printNewLine();
            return;
        }
        System.out.println(String.format(
                ORDERED_MENU_FORMAT,
                gift.getLabel(),
                1
        ));
        printNewLine();
    }

    private void printTotalPrice(int totalPrice) {
        System.out.println(TOTAL_PRICE_HEADER);
        System.out.println(String.format(PRICE_FORMAT, formatIntegerToAmount(totalPrice)));
        printNewLine();
    }

    private String formatIntegerToAmount(int amount) {
        DecimalFormat amountFormat = new DecimalFormat(AMOUNT_PATTERN);
        return amountFormat.format(amount);
    }

    public void printBenefitIntroduceMessage(Day dayOfVisit) {
        System.out.println(String.format(
                BENEFIT_INTRODUCE_FORMAT,
                EventConfig.EVENT_MONTH,
                dayOfVisit.getDay()
        ));
        printNewLine();
    }

    private void printNewLine() {
        System.out.println();
    }

    public void printOrderedMenus(List<MenuDto> menuDtos) {
        System.out.println(ORDERD_MENU_HEADER);
        menuDtos.forEach(menuDto -> System.out.println(String.format(
                ORDERED_MENU_FORMAT,
                menuDto.getMenuName(),
                menuDto.getCount())
        ));
        printNewLine();
    }
}
