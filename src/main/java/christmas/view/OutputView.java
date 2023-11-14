package christmas.view;

import christmas.dto.MenuDto;
import christmas.model.*;

import java.util.List;

public class OutputView {
    private static final String START_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String DAY_OF_VISIT_INPUT_MESSAGE =
            "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_INPUT_MESSAGE =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private final ConsoleOutputFormatter consoleOutputFormatter;

    public OutputView(ConsoleOutputFormatter consoleOutputFormatter) {
        this.consoleOutputFormatter = consoleOutputFormatter;
    }

    public void printStartMessage() {
        System.out.println(START_MESSAGE);
    }

    public void printError(String message) {
        System.out.println(consoleOutputFormatter.formatError(message));
    }

    public void printDayOfVisitInputMessage() {
        System.out.println(DAY_OF_VISIT_INPUT_MESSAGE);
    }

    public void printMenuInputMessage() {
        System.out.println(MENU_INPUT_MESSAGE);
    }

    public void printBenefit(BenefitHolder benefitHolder, MenuSheet menuSheet) {
        System.out.println(consoleOutputFormatter.formatGift(benefitHolder.getGift().toMenuDtoList()));
        System.out.println(consoleOutputFormatter.formatBenefits(benefitHolder));
        System.out.println(consoleOutputFormatter.formatTotalBenefitPrice(benefitHolder.getTotalBenefit()));
        System.out.println(consoleOutputFormatter.formatAmountOfPayment(benefitHolder.getTotalDiscount(), menuSheet.calculateTotalPrice()));
        System.out.println(consoleOutputFormatter.formatEventBadge(benefitHolder.getBadge()));
    }

    public void printOrderedMenus(List<MenuDto> menuDtos) {
        System.out.println(consoleOutputFormatter.formatOrderedMenus(menuDtos));
    }

    public void printBenefitIntroduceMessage(Day dayOfVisit) {
        System.out.println(consoleOutputFormatter.formatBenefitIntroduceMessage(dayOfVisit));
    }

    public void printTotalPrice(int totalPrice) {
        System.out.println(consoleOutputFormatter.formatTotalPrice(totalPrice));
    }
}
