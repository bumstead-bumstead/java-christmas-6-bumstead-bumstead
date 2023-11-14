package christmas.view;

import christmas.config.EventConfig;
import christmas.dto.MenuDto;
import christmas.model.*;

import java.text.DecimalFormat;
import java.util.List;

public class ConsoleOutputFormatter {
    private static final String BENEFIT_INTRODUCE_FORMAT = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ORDERED_MENU_FORMAT = "%s %d개";
    private static final String AMOUNT_PATTERN = "#,###";
    private static final String NOTHING_MESSAGE = "없음";
    private static final String GIFT_HEADER = "<증정 메뉴>";
    private static final String TOTAL_PRICE_HEADER = "<할인 전 총주문 금액>";
    private static final String ORDERD_MENU_HEADER = "<주문 메뉴>";
    private static final String BENEFIT_HEADER = "<혜택 내역>";
    private static final String TOTAL_BENEFIT_PRICE_HEADER = "<총혜택 금액>";
    private static final String TOTAL_PAYMENT_PRICE_HEADER = "<할인 후 예상 결제 금액>";
    private static final String EVENT_BADGE_HEADER = "<12월 이벤트 배지>";
    private static final String PRICE_FORMAT = "%s원";
    private static final String D_DAY_DISCOUNT_FORMAT = "크리스마스 디데이 할인: -%s원";
    private static final String WEEKDAY_DISCOUNT_FORMAT = "평일 할인: -%s원";
    private static final String WEEKEND_DISCOUNT_FORMAT = "주말 할인: -%s원";
    private static final String SPECIAL_DISCOUNT_FORMAT = "특별 할인: -%s원";
    private static final String GIFT_DISCOUNT_FORMAT = "증정 이벤트: -%s원";
    private static final String NEW_LINE = "\n";

    public String formatError(String message) {
        return ERROR_PREFIX + message;
    }

    public String formatBenefit(Benefit benefit) {
        return formatTotalPrice(benefit.getTotalPrice())
                + formatGift(benefit.getGift())
                + formatBenefits(benefit)
                + formatTotalBenefitPrice(benefit.getTotalBenefit())
                + formatAmountOfPayment(benefit.getTotalDiscount(), benefit.getTotalPrice())
                + formatEventBadge(benefit.getBadge());
    }

    public String formatBenefitIntroduceMessage(Day dayOfVisit) {
        return String.format(BENEFIT_INTRODUCE_FORMAT, EventConfig.EVENT_MONTH, dayOfVisit.getDay())
                + NEW_LINE;
    }

    public String formatOrderedMenus(List<MenuDto> menuDtos) {
        StringBuilder stringBuilder = getHeaderStringBuilder(ORDERD_MENU_HEADER);
        menuDtos.forEach(menuDto -> stringBuilder.append(String.format(
                        ORDERED_MENU_FORMAT,
                        menuDto.getMenuName(),
                        menuDto.getCount())
                )
                .append(NEW_LINE));

        return stringBuilder.toString();
    }

    private String formatEventBadge(Badge badge) {
        StringBuilder stringBuilder = getHeaderStringBuilder(EVENT_BADGE_HEADER);
        if (badge.equals(Badge.NO_BADGE)) {
            return stringBuilder
                    .append(NOTHING_MESSAGE)
                    .toString();
        }
        return stringBuilder.append(badge.getLabel())
                .toString();
    }

    private String formatAmountOfPayment(int totalBenefit, int totalPrice) {
        int amountOfPay = totalPrice - totalBenefit;
        return getHeaderStringBuilder(TOTAL_PAYMENT_PRICE_HEADER)
                .append(String.format(PRICE_FORMAT, formatIntegerToAmount(amountOfPay)))
                .append(NEW_LINE + NEW_LINE)
                .toString();
    }

    private String formatTotalBenefitPrice(int totalBenefit) {
        StringBuilder stringBuilder = getHeaderStringBuilder(TOTAL_BENEFIT_PRICE_HEADER);
        if (totalBenefit == 0) {
            return stringBuilder.append(String.format(PRICE_FORMAT, formatIntegerToAmount(totalBenefit)))
                    .append(NEW_LINE + NEW_LINE)
                    .toString();
        }
        return stringBuilder.append(String.format("-" + PRICE_FORMAT, formatIntegerToAmount(totalBenefit)))
                .append(NEW_LINE + NEW_LINE)
                .toString();
    }

    private String formatBenefits(Benefit benefit) {
        StringBuilder stringBuilder = getHeaderStringBuilder(BENEFIT_HEADER);
        if (!benefit.hasBenefit()) {
            return stringBuilder.append(NOTHING_MESSAGE)
                    .append(NEW_LINE + NEW_LINE)
                    .toString();
        }

        return stringBuilder
                .append(formatOneDiscount(D_DAY_DISCOUNT_FORMAT, benefit.getDDayDiscount()))
                .append(formatOneDiscount(WEEKDAY_DISCOUNT_FORMAT, benefit.getWeekdayDiscount()))
                .append(formatOneDiscount(WEEKEND_DISCOUNT_FORMAT, benefit.getWeekendDiscount()))
                .append(formatOneDiscount(SPECIAL_DISCOUNT_FORMAT, benefit.getSpecialDiscount()))
                .append(formatOneDiscount(GIFT_DISCOUNT_FORMAT, benefit.getGift().getPrice()))
                .append(NEW_LINE)
                .toString();
    }

    private String formatOneDiscount(String promptFormat, int discount) {
        StringBuilder stringBuilder = new StringBuilder();
        if (discount != 0) {
            stringBuilder.append(String.format(promptFormat, formatIntegerToAmount(discount)))
                    .append(NEW_LINE);
        }
        return stringBuilder.toString();
    }

    private String formatGift(Menu gift) {
        StringBuilder stringBuilder = getHeaderStringBuilder(GIFT_HEADER);
        if (gift.equals(Menu.NO_FOOD)) {
            return stringBuilder.append(NOTHING_MESSAGE)
                    .append(NEW_LINE + NEW_LINE)
                    .toString();
        }
        return stringBuilder.append(String.format(ORDERED_MENU_FORMAT, gift.getLabel(), 1))
                .append(NEW_LINE + NEW_LINE)
                .toString();
    }

    private String formatTotalPrice(int totalPrice) {
        return getHeaderStringBuilder(TOTAL_PRICE_HEADER)
                .append(String.format(PRICE_FORMAT, formatIntegerToAmount(totalPrice)))
                .append(NEW_LINE + NEW_LINE)
                .toString();
    }

    private String formatIntegerToAmount(int amount) {
        DecimalFormat amountFormat = new DecimalFormat(AMOUNT_PATTERN);
        return amountFormat.format(amount);
    }

    private static StringBuilder getHeaderStringBuilder(String header) {
        return new StringBuilder()
                .append(header)
                .append(NEW_LINE);
    }
}
