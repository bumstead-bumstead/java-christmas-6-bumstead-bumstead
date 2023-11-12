package christmas.view;

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
}
