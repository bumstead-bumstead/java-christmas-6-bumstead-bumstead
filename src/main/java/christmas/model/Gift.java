package christmas.model;

import christmas.dto.MenuDto;

import java.util.List;
import java.util.Map;

import static christmas.config.EventConfig.*;

public class Gift {
    private final Map<Menu, Integer> gift;

    public Gift(Map<Menu, Integer> gift) {
        this.gift = gift;
    }

    public static Gift fromTotalPrice(int totalPrice) {
        if (totalPrice < PURCHASE_AMOUNT_BASELINE) {
            return new Gift(Map.of());
        }
        return new Gift(Map.of(DEFAULT_GIFT, NUMBER_OF_DEFAULT_GIFT));
    }

    public List<MenuDto> toMenuDtoList() {
        return gift.entrySet()
                .stream()
                .map(MenuDto::fromMenuEntry)
                .toList();
    }

    public int calculateTotalPrice() {
        return gift.keySet()
                .stream()
                .map(menu -> gift.get(menu) * menu.getPrice())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
