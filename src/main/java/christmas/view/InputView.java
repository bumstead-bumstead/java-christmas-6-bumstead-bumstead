package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public int readDayOfVisit() {
        String dayInput = Console.readLine();
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
}
