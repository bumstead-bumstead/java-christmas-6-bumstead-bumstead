package christmas;

import christmas.dto.MenuDto;
import christmas.model.Benefit;
import christmas.model.Day;
import christmas.model.MenuSheet;
import christmas.view.ConsoleInputReader;
import christmas.view.ConsoleOutputFormatter;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class ChristmasEventController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChristmasEventController() {
        this.inputView = new InputView(new ConsoleInputReader());
        this.outputView = new OutputView(new ConsoleOutputFormatter());
    }

    public void run() {
        outputView.printStartMessage();
        Day dayOfVisit = inputDayOfVisit();
        MenuSheet menuSheet = inputMenus();
        Benefit benefit = Benefit.of(dayOfVisit, menuSheet);

        showResult(dayOfVisit, menuSheet, benefit);
    }

    private MenuSheet inputMenus() {
        outputView.printMenuInputMessage();
        MenuSheet menuSheet = requestRepeatedly(this::getMenuSheet);
        return menuSheet;
    }

    private Day inputDayOfVisit() {
        outputView.printDayOfVisitInputMessage();
        Day dayOfVisit = requestRepeatedly(this::getDayOfVisit);
        return dayOfVisit;
    }

    private void showResult(Day dayOfVisit, MenuSheet menuSheet, Benefit benefit) {
        outputView.printBenefitIntroduceMessage(dayOfVisit);
        outputView.printOrderedMenus(menuSheet.toMenuDtoList());
        outputView.printTotalPrice(menuSheet.calculateTotalPrice());
        outputView.printBenefit(benefit, menuSheet);
    }

    private MenuSheet getMenuSheet() {
        List<MenuDto> menuInput = inputView.readMenu();
        return MenuSheet.fromMenuDtoList(menuInput);
    }

    private Day getDayOfVisit() {
        int dayInput = inputView.readDayOfVisit();
        return Day.of(dayInput);
    }

    private <T> T requestRepeatedly(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
