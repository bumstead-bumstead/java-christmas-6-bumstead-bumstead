package christmas.view;

import christmas.config.ErrorMessage;
import christmas.dto.MenuDto;

import java.util.Arrays;
import java.util.List;

import static christmas.config.ErrorMessage.*;
import static christmas.config.EventConfig.*;

public class InputView {
    private final InputReader inputReader;

    public InputView(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public int readDayOfVisit() {
        String dayInput = inputReader.read().trim();
        validateIntegerInput(dayInput);

        return Integer.parseInt(dayInput);
    }

    private void validateIntegerInput(String dayInput) {
        try {
            Integer.parseInt(dayInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(DAY_INPUT_ERROR_MESSAGE);
        }
    }

    public List<MenuDto> readMenu() {
        String menuInput = inputReader.read().trim();
        validateMenuInput(menuInput);

        return Arrays.stream(menuInput.split(MENU_SEPARATOR))
                .map(MenuDto::fromConsoleInputFormat)
                .toList();
    }

    private void validateMenuInput(String menuInput) {
        String[] menus = menuInput.split(MENU_SEPARATOR);

        Arrays.stream(menus)
                .forEach(this::validateMenuInputFormat);
    }

    private void validateMenuInputFormat(String menuInput) {
        if (!menuInput.matches(MENU_INPUT_REGEX)) {
            throw new IllegalArgumentException(MENU_INPUT_ERROR_MESSAGE);
        }
    }
}
