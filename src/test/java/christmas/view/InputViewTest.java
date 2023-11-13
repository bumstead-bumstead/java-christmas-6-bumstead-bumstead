package christmas.view;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("방문 일자를 입력받을 때,")
    @Nested
    class ReadDayOfVisit {
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

        @DisplayName("유효한 입력의 경우 int를 반환한다.")
        @Test
        void validDayInput() {
            //given
            when(inputReader.read())
                    .thenReturn("3");

            //when
            int result = inputView.readDayOfVisit();

            //then
            assertThat(result).isEqualTo(3);
        }
    }

    @DisplayName("메뉴를 입력받을 때,")
    @Nested
    class ReadMenuInput {
        @DisplayName("유효한 입력의 경우 List<String>을 반환한다.")
        @Test
        void validDayInput() {
            //given
            when(inputReader.read())
                    .thenReturn("해산물파스타-3,어쩌구저쩌구-1,타파스-53");

            //when
            List<String> result = inputView.readMenu();

            //then
            assertThat(result)
                    .contains("해산물파스타-3")
                    .contains("어쩌구저쩌구-1")
                    .contains("타파스-53")
                    .hasSize(3);
        }

        @DisplayName("\'${메뉴 이름}-${개수}\' 형태가 아닌 경우 IllegalArguementException을 발생시킨다.")
        @ValueSource(strings = {"해산물파스타-a", "해산물파스타3", "해산물파스타-", "해산물파스타-3a1", "-3", "-"})
        @ParameterizedTest
        void inValidInputFormat(String menuInput) {
            //given
            when(inputReader.read())
                    .thenReturn(menuInput);

            //when
            //then
            assertThatThrownBy(() -> inputView.readMenu())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴와 개수는 \'${메뉴 이름}-${개수}\' 의 형태로 입력해주세요.");
        }
    }
}