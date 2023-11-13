package christmas.model;

import christmas.dto.MenuDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.config.ErrorMessage.*;
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
            List<MenuDto> menuDtos = List.of(
                    MenuDto.fromConsoleInputFormat("양송이수프-3"),
                    MenuDto.fromConsoleInputFormat("제로콜라-3"),
                    MenuDto.fromConsoleInputFormat("티본스테이크-3")
            );
            MenuSheet.fromMenuDtoList(menuDtos);
        }

        @DisplayName("메뉴 입력이 중복된 경우 IllegalArgumentException을 발생시킨다.")
        @Test
        void duplication() {
            //given
            List<MenuDto> menuDtos = List.of(
                    MenuDto.fromConsoleInputFormat("양송이수프-3"),
                    MenuDto.fromConsoleInputFormat("제로콜라-3"),
                    MenuDto.fromConsoleInputFormat("양송이수프-2")
            );

            //when
            //then
            assertThatThrownBy(() -> MenuSheet.fromMenuDtoList(menuDtos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(MENU_INPUT_ERROR_MESSAGE);
        }

        @DisplayName("메뉴 개수가 정해진 개수를 초과할 경우 IllegalArgumentException을 발생시킨다.")
        @Test
        void maximumMenuNumber() {
            //given
            List<MenuDto> menuDtos = List.of(
                    MenuDto.fromConsoleInputFormat("양송이수프-15"),
                    MenuDto.fromConsoleInputFormat("제로콜라-3"),
                    MenuDto.fromConsoleInputFormat("타파스-3")
            );
            //when
            //then
            assertThatThrownBy(() -> MenuSheet.fromMenuDtoList(menuDtos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(MENU_NUMBER_ERROR_MESSAGE);
        }

        @DisplayName("음료 외의 메뉴가 없을 경우 IllegalArgumentException을 발생시킨다.")
        @Test
        void onlyBeverage() {
            //given
            List<MenuDto> menuDtos = List.of(
                    MenuDto.fromConsoleInputFormat("레드와인-3"),
                    MenuDto.fromConsoleInputFormat("제로콜라-3"),
                    MenuDto.fromConsoleInputFormat("샴페인-3")
            );
            //when
            //then
            assertThatThrownBy(() -> MenuSheet.fromMenuDtoList(menuDtos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(MENU_COMBINATION_ERROR_MESSAGE);
        }
    }

    @DisplayName("양송이수프-2,티본스테이크-2,초코케이크-3,샴페인-1,타파스-3,아이스크림-5를 포함하는 MenuSheet 객체에 대해서,")
    @Nested
    class calculation {
        private static MenuSheet menuSheet;

        @BeforeAll
        static void setup() {
            List<MenuDto> menuDtos = List.of(
                    MenuDto.fromConsoleInputFormat("양송이수프-2"),
                    MenuDto.fromConsoleInputFormat("티본스테이크-2"),
                    MenuDto.fromConsoleInputFormat("초코케이크-3"),
                    MenuDto.fromConsoleInputFormat("샴페인-1"),
                    MenuDto.fromConsoleInputFormat("타파스-3"),
                    MenuDto.fromConsoleInputFormat("아이스크림-5")
            );
            menuSheet = MenuSheet.fromMenuDtoList(menuDtos);
        }

        @DisplayName("각 카테고리의 음식 개수를 반환한다.")
        @MethodSource("provideFoodCategoriesAndPrices")
        @ParameterizedTest
        void numberOfMenuCategory(FoodCategory foodCategory, int expectedNumber) {
            //when
            int actualNumber = menuSheet.getNumberOfMenuCategory(foodCategory); //208_500 -> 233500

            //then
            assertThat(actualNumber)
                    .isEqualTo(expectedNumber);
        }

        @DisplayName("총 가격의 합을 반환한다.")
        @Test
        void calculateTotalPrice() {
            //given
            int expectedTotalPrice = 233_500;

            //when
            int actualTotalPrice = menuSheet.calculateTotalPrice();

            //then
            assertThat(actualTotalPrice)
                    .isEqualTo(expectedTotalPrice);
        }

        private static Stream<Arguments> provideFoodCategoriesAndPrices() {
            return Stream.of(
                    Arguments.of(FoodCategory.MAIN, 2),
                    Arguments.of(FoodCategory.DESSERT, 8),
                    Arguments.of(FoodCategory.APPETIZER, 5),
                    Arguments.of(FoodCategory.BEVERAGE, 1)
            );
        }
    }
}