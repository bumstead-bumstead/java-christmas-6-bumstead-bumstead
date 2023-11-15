package christmas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GiftTest {
    @DisplayName("총 구매금액이 일정 금액을 넘으면 샴페인을 증정한다.")
    @Test
    void giftBenefitInitialization() {
        //when
        Gift gift = Gift.fromTotalPrice(120000);
        //then
        assertThat(gift.calculateTotalPrice()).isEqualTo(25000);
    }

    @DisplayName("총 구매금액이 일정 금액을 넘지 않으면, 증정하지 않는다.")
    @Test
    void emptyGiftBenefitInitialization() {
        //when
        Gift gift = Gift.fromTotalPrice(9999);
        //then
        assertThat(gift.calculateTotalPrice()).isEqualTo(0);
    }
}