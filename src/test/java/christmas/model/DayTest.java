package christmas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DayTest {

    @DisplayName("Day 객체를 생성할 때,")
    @Nested
    class InstanceCreation {
        @DisplayName("정해진 범위를 벗어날 경우 IllegalArguementException을 발생시킨다.")
        @ValueSource(ints = {-1, 0, 32})
        @ParameterizedTest
        void invalidRange(int dayInput) {
            //when
            //then
            assertThatThrownBy(() -> Day.of(dayInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("1에서 31일 사이의 값을 입력해주세요.");
        }

        @DisplayName("같은 값으로 반복적으로 생성할 경우 같은 객체를 반환한다.")
        @Test
        void SingletonTest() {
            //given
            int dayInput = 3;
            //when
            Day day1 = Day.of(dayInput);
            Day day2 = Day.of(dayInput);
            //then
            assertThat(day1).isSameAs(day2);
        }
    }
}