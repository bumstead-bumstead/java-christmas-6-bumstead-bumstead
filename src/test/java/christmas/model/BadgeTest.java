package christmas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BadgeTest {
    @DisplayName("fromTotalBenefit()을 호출할 때, 혜택 금액에 맞는 객체를 반환한다.")
    @MethodSource("provideBenefitsAndBadges")
    @ParameterizedTest(name = "{index} benefit = {0}, badge = {1}")
    void fromTotalBenefit(int totalBenefit, Badge expectedBadge){
        //when
        Badge actualBadge = Badge.fromTotalBenefit(totalBenefit);

        //then
        assertThat(actualBadge)
                .isEqualTo(expectedBadge);
    }

    private static Stream<Arguments> provideBenefitsAndBadges() {
        return Stream.of(
                Arguments.of(3000, Badge.NO_BADGE),
                Arguments.of(7000, Badge.STAR),
                Arguments.of(15000, Badge.TREE),
                Arguments.of(20000, Badge.SANTA)
        );
    }
}