package christmas.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InputViewTest {
    private static InputView inputView;
    private static InputReader inputReader;

    @BeforeAll
    static void setup() {
        inputReader = Mockito.mock(ConsoleInputReader.class);
        inputView = new InputView(inputReader);
    }

    @DisplayName("정수가 아닌 경우 IllegalArgumentException을 발생시킨다.")
    @ValueSource(strings = {"a", "25a", "a25"})
    @ParameterizedTest
    void readInvalidDayInput(String input) {
        //given
        when(inputReader.read())
                .thenReturn(input);

        //when
        //then
        assertThatThrownBy(inputView::readDayOfVisit)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정수 형태로 입력해주세요.");
    }
}