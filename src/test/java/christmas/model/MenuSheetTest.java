package christmas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static christmas.model.Menu.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuSheetTest {

    @DisplayName("fromConsoleInputFormat()을 이용해 객체를 생성할 때,")
    @Nested
    class fromConsoleInputFormatValidation {
        @DisplayName("유효한 경우 예외를 발생시키지 않는다.")
        @Test
        void validInput() {
            //given
            List<String> inputEntries = List.of("양송이수프-3", "제로콜라-3", "티본스테이크-3");
            MenuSheet.fromConsoleInputFormat(inputEntries);
        }

        @DisplayName("메뉴 입력이 중복된 경우 IllegalArgumentException을 발생시킨다.")
        @Test
        void duplication() {
            //given
            List<String> inputEntries = List.of("양송이수프-3", "제로콜라-3", "양송이수프-2");

            //when
            //then
            assertThatThrownBy(() -> MenuSheet.fromConsoleInputFormat(inputEntries))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴 이름이 중복되었습니다.");
        }

        @DisplayName("메뉴 개수가 정해진 개수를 초과할 경우 IllegalArgumentException을 발생시킨다.")
        @Test
        void maximumMenuNumber() {
            //given
            List<String> inputEntries = List.of("양송이수프-15", "제로콜라-3", "타파스-3");

            //when
            //then
            assertThatThrownBy(() -> MenuSheet.fromConsoleInputFormat(inputEntries))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴는 최대 20개까지 주문할 수 있습니다.");
        }

        @DisplayName("음료 외의 메뉴가 없을 경우 IllegalArgumentException을 발생시킨다.")
        @Test
        void onlyBeverage() {
            //given
            List<String> inputEntries = List.of("레드와인-3", "제로콜라-3", "샴페인-3");

            //when
            //then
            assertThatThrownBy(() -> MenuSheet.fromConsoleInputFormat(inputEntries))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("음료가 아닌 메뉴를 최소 한 개 이상 주문해주세요.");
        }

        @DisplayName("유효한 입력일 경우, 메뉴의 개수를 유효하게 저장한다.")
        @Test
        void instance() {
            //given
            List<String> inputEntries = List.of("양송이수프-3", "제로콜라-3", "티본스테이크-3");

            //when
            MenuSheet menuSheet = MenuSheet.fromConsoleInputFormat(inputEntries);

            //then
            assertThat(menuSheet.getNumberOfMenu(T_BONE_STEAK))
                    .isEqualTo(3);
            assertThat(menuSheet.getNumberOfMenu(MUSHROOM_SOUP))
                    .isEqualTo(3);
            assertThat(menuSheet.getNumberOfMenu(ZERO_COKE))
                    .isEqualTo(3);
            assertThat(menuSheet.getNumberOfMenu(ICE_CREAM))
                    .isEqualTo(0);

        }
    }
}