package christmas.view;

import java.util.Arrays;
import java.util.List;

import static christmas.config.EventConfig.*;

public class InputView {
    private final InputReader inputReader;

    public InputView(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public int readDayOfVisit() {
        String dayInput = inputReader.read();
        validateIntegerInput(dayInput);

        return Integer.parseInt(dayInput);
    }

    private void validateIntegerInput(String dayInput) {
        try {
            Integer.parseInt(dayInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수 형태로 입력해주세요.");
        }
    }

    public List<String> readMenu() {
        String menuInput = inputReader.read();
        validateMenuInput(menuInput);

        return Arrays.stream(menuInput.split(MENU_SEPARATOR)).toList();
    }

    private void validateMenuInput(String menuInput) {
        String[] menus = menuInput.split(MENU_SEPARATOR);

        Arrays.stream(menus)
                .forEach(this::validateMenuInputFormat);
    }

    private void validateMenuInputFormat(String menuInput) {
        if (!menuInput.matches(MENU_INPUT_REGEX)) {
            throw new IllegalArgumentException("메뉴와 개수는 \'${메뉴 이름}-${개수}\' 의 형태로 입력해주세요.");
        }
    }
}
