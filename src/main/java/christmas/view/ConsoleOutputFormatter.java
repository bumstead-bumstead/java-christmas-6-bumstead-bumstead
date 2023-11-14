package christmas.view;

import christmas.config.EventConfig;
import christmas.dto.MenuDto;
import christmas.model.*;
import christmas.model.discount.Discount;

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
    private static final String DISCOUNT_FORMAT = "%s: -%s원";
    private static final String NEW_LINE = "\n";

    public String formatError(String message) {
        return ERROR_PREFIX + message;
    }

    public String formatTotalPrice(int totalPrice) {
        return getHeaderStringBuilder(TOTAL_PRICE_HEADER)
                .append(String.format(PRICE_FORMAT, formatIntegerToAmount(totalPrice)))
                .append(NEW_LINE)
                .toString();
    }

    public String formatBenefitIntroduceMessage(Day dayOfVisit) {
        return String.format(BENEFIT_INTRODUCE_FORMAT, EventConfig.EVENT_MONTH, dayOfVisit.getDay())
                + NEW_LINE;
    }

    public String formatOrderedMenus(List<MenuDto> menuDtos) {
        StringBuilder stringBuilder = getHeaderStringBuilder(ORDERD_MENU_HEADER);
        menuDtos.forEach(menuDto -> stringBuilder.append(formatMenu(menuDto))
                .append(NEW_LINE));

        return stringBuilder.toString();
    }

    private static String formatMenu(MenuDto menuDto) {
        return String.format(
                ORDERED_MENU_FORMAT,
                menuDto.getMenuName(),
                menuDto.getCount());
    }

    public String formatEventBadge(Badge badge) {
        StringBuilder stringBuilder = getHeaderStringBuilder(EVENT_BADGE_HEADER);
        if (badge.equals(Badge.NO_BADGE)) {
            return stringBuilder
                    .append(NOTHING_MESSAGE)
                    .toString();
        }
        return stringBuilder.append(badge.getLabel())
                .toString();
    }

    public String formatAmountOfPayment(int totalDiscount, int totalPrice) {
        int amountOfPay = totalPrice - totalDiscount;
        return getHeaderStringBuilder(TOTAL_PAYMENT_PRICE_HEADER)
                .append(String.format(PRICE_FORMAT, formatIntegerToAmount(amountOfPay)))
                .append(NEW_LINE)
                .toString();
    }

    public String formatTotalBenefitPrice(int totalBenefit) {
        StringBuilder stringBuilder = getHeaderStringBuilder(TOTAL_BENEFIT_PRICE_HEADER);
        if (totalBenefit == 0) {
            return stringBuilder.append(String.format(PRICE_FORMAT, formatIntegerToAmount(totalBenefit)))
                    .append(NEW_LINE)
                    .toString();
        }
        return stringBuilder.append(String.format("-" + PRICE_FORMAT, formatIntegerToAmount(totalBenefit)))
                .append(NEW_LINE)
                .toString();
    }

    public String formatBenefits(Benefit benefit) {
        StringBuilder stringBuilder = getHeaderStringBuilder(BENEFIT_HEADER);
        List<Discount> discounts = benefit.getDiscounts();

        if (!benefit.hasBenefit()) {
            return stringBuilder.append(NOTHING_MESSAGE)
                    .append(NEW_LINE)
                    .toString();
        }

        discounts.stream()
                .filter(Discount::hasDiscount)
                .forEach(discount -> stringBuilder.append(formatOneDiscount(discount)));

        return stringBuilder.toString();
    }

    private String formatOneDiscount(Discount discount) {
        return String.format(
                DISCOUNT_FORMAT,
                discount.getLabel(),
                formatIntegerToAmount(discount.getAmount())
        ) + NEW_LINE;
    }

    public String formatGift(List<MenuDto> menuDtos) {
        StringBuilder stringBuilder = getHeaderStringBuilder(GIFT_HEADER);
        if (menuDtos.isEmpty()) {
            return stringBuilder.append(NOTHING_MESSAGE)
                    .append(NEW_LINE)
                    .toString();
        }
        menuDtos.forEach(menuDto -> stringBuilder.append(formatMenu(menuDto))
                .append(NEW_LINE));

        return stringBuilder.toString();
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
