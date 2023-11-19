package christmas.view;

import christmas.dto.MenuDto;
import christmas.model.Badge;
import christmas.model.Day;
import christmas.model.Menu;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleOutputFormatterTest {
    private final ConsoleOutputFormatter consoleOutputFormatter = new ConsoleOutputFormatter();
    @DisplayName("총 주문 금액을 출력 형식에 맞게 포매팅한다.")
    @Test
    void formatTotalPrice() {
        //given
        int totalPrice = 39230;
        String expectedString = "<할인 전 총주문 금액>\n" +
                "39,230원\n";

        //when
        String actualString = consoleOutputFormatter.formatTotalPrice(totalPrice);

        //then
        assertThat(actualString).isEqualTo(expectedString);
    }

    @DisplayName("이벤트 혜택 안내 메세지를 형식에 맞게 포매팅한다.")
    @Test
    void formatBenefitIntroduceMessage() {
        //given
        Day day = Day.of(6);
        String expectedString = "12월 6일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";
        //when
        String actualString = consoleOutputFormatter.formatBenefitIntroduceMessage(day);
        //then
        assertThat(actualString).isEqualTo(expectedString);
    }

    @DisplayName("주문 메뉴를 형식에 맞게 포매팅한다.")
    @Test
    void formatOrderedMenus() {
        //given
        List<MenuDto> menuDtos = List.of(
                MenuDto.fromMenuEntry(MapEntry.entry(Menu.CHAMPAGNE, 2)),
                MenuDto.fromMenuEntry(MapEntry.entry(Menu.TAPAS, 2)),
                MenuDto.fromMenuEntry(MapEntry.entry(Menu.ZERO_COKE, 1)),
                MenuDto.fromMenuEntry(MapEntry.entry(Menu.T_BONE_STEAK, 3))
        );
        List<String> expectedStringList = List.of("샴페인 2개", "타파스 2개", "제로콜라 1개", "티본스테이크 3개");
        //when
        String actualString = consoleOutputFormatter.formatOrderedMenus(menuDtos);
        //then
        assertThat(actualString)
                .contains(expectedStringList);
    }

    @DisplayName("배지를 형식에 맞게 포매팅한다.")
    @Test
    void formatEventBadge() {
        //given
        Badge badge = Badge.STAR;
        String expectedString = "<12월 이벤트 배지>\n" +
                "별";
        //when
        String actualString = consoleOutputFormatter.formatEventBadge(badge);
        //then
        assertThat(actualString)
                .contains(expectedString);
    }

    @DisplayName("예상 결제 금액을 형식에 맞게 포매팅한다.")
    @Test
    void formatAmountOfPayment() {
        //given
        int totalDiscount = 300;
        int totalPrice = 5301;
        String expectedString = "<할인 후 예상 결제 금액>\n" +
                "5,001원\n";
        //when
        String actualString = consoleOutputFormatter.formatAmountOfPayment(totalDiscount, totalPrice);
        //then
        assertThat(actualString)
                .contains(expectedString);
    }

    @DisplayName("총 혜택 금액을 형식에 맞게 포매팅한다.")
    @Test
    void formatTotalBenefitPrice() {
        //given
        int totalBenefit = 5301;
        String expectedString = "<총혜택 금액>\n" +
                "-5,301원\n";
        //when
        String actualString = consoleOutputFormatter.formatTotalBenefitPrice(totalBenefit);
        //then
        assertThat(actualString)
                .contains(expectedString);
    }
}