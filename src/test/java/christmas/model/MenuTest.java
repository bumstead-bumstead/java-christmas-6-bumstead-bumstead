package christmas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static christmas.config.ErrorMessage.MENU_INPUT_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    @DisplayName("존재하지 않는 이름의 메뉴를 요청하면, IllegalArgumentException을 발생시킨다.")
    @ValueSource(strings = {"송이버섯스프", "123", "바베큐립"})
    @ParameterizedTest
    void invalidName(String menuName) {
        //when
        //then
        assertThatThrownBy(() -> Menu.of(menuName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MENU_INPUT_ERROR_MESSAGE);
    }

    @DisplayName("존재하는 이름의 메뉴를 요청하면 알맞은 Menu 객체를 반환한다.")
    @MethodSource("provideMenuNamesAndMenuInstances")
    @ParameterizedTest
    void validName(String menuName, Menu menu){
        //when
        Menu actualMenu = Menu.of(menuName);
        //then
        assertThat(actualMenu)
                .isEqualTo(menu);
    }

    private static Stream<Arguments> provideMenuNamesAndMenuInstances() {
        return Stream.of(
                Arguments.of("양송이수프", Menu.MUSHROOM_SOUP),
                Arguments.of("타파스", Menu.TAPAS),
                Arguments.of("시저샐러드", Menu.CAESAR_SALAD),
                Arguments.of("바비큐립", Menu.BARBECUE_LIP),
                Arguments.of("초코케이크", Menu.CHOCOLATE_CAKE)
        );
    }
}